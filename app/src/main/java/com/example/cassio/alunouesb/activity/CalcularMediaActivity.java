package com.example.cassio.alunouesb.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Usuario;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalcularMediaActivity extends AppCompatActivity {

    private Usuario usuario = PrincipalActivity.usuario;
    private Disciplina disciplina = null;
    private float mediaGeral;

    private MaterialBetterSpinner spinnerDisciplina;
    private EditText unidade1;
    private EditText unidade2;
    private EditText unidade3;
    private LinearLayout layoutProvaFinal;
    private EditText notaFinal;
    private TextView media;
    private TextView finalNecessaria;
    private ImageView imageResultado;
    private Button buttonCalcularMedia;
    private MenuItem item;

    private ArrayList<Disciplina> disciplinasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_media);
        setTitle("Calcular Média");

        spinnerDisciplina = findViewById(R.id.spinner_disciplina);
        unidade1 = findViewById(R.id.text_unidade_1);
        unidade2 = findViewById(R.id.text_unidade_2);
        unidade3 =  findViewById(R.id.text_unidade_3);
        layoutProvaFinal = findViewById(R.id.layout_prova_final);
        notaFinal = findViewById(R.id.text_final);
        media = findViewById(R.id.text_media);
        finalNecessaria = findViewById(R.id.text_final_necessaria);
        imageResultado = findViewById(R.id.image_resultado);
        buttonCalcularMedia = findViewById(R.id.button_calcular_media);

        layoutProvaFinal.setVisibility(View.GONE);
        media.setVisibility(View.INVISIBLE);
        finalNecessaria.setVisibility(View.GONE);
        imageResultado.setVisibility(View.INVISIBLE);

        carregarDados();

    }


    public void carregarDados(){
        if(PrincipalActivity.semestre != null){
            disciplinasList = (ArrayList<Disciplina>) PrincipalActivity.semestre.getDisciplinaList();
//        disciplinasList = new ArrayList<Disciplina>();
            configurarSpinner();

            spinnerDisciplina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    disciplina = disciplinasList.get(i);

                    if (disciplina.getUnidade1() != 0) {
                        unidade1.setText(String.valueOf(disciplina.getUnidade1()));
                    }else{
                        unidade1.setText("");
                    }

                    if (disciplina.getUnidade2() != 0) {
                        unidade2.setText(String.valueOf(disciplina.getUnidade2()));

                    }else{
                        unidade2.setText("");
                    }

                    if (disciplina.getUnidade3() != 0) {
                        unidade3.setText(String.valueOf(disciplina.getUnidade3()));

                    }else{
                        unidade3.setText("");
                    }
                }
            });
        }else{
            Toast.makeText(this, "Falha na conexão com o servidor", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_lembrete, menu);
        item = menu.findItem(R.id.action_salvar);

        if(disciplina != null){
            item.setVisible(true);

        }else{
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_salvar:
                salvarNotas();
                limparEntradas();
                spinnerDisciplina.setText("");
                invalidateOptionsMenu();
                disciplina = null;
                break;

            case R.id.action_cancelar:
                limparEntradas();
                invalidateOptionsMenu();
                spinnerDisciplina.setText("");
                disciplina = null;
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void calcularMedia(View view) {

        if(disciplinasList.isEmpty()){
            Toast.makeText(this, "Lista de disciplinas vazia", Toast.LENGTH_LONG).show();
        }else{
            mediaGeral = mediaResultado(0);

            media.setVisibility(View.VISIBLE);
            media.setText("Média Geral: " + new DecimalFormat("0.00").format(mediaGeral));


            if (buttonCalcularMedia.getText().toString().equals("Calcular Média")) {

                if (mediaGeral >= 7) {//Aprovado sem final
                    imageResultado.setVisibility(View.VISIBLE);
                    finalNecessaria.setVisibility(View.VISIBLE);
                    finalNecessaria.setText("Você foi Aprovado");
                    buttonCalcularMedia.setText("Limpar");
                    imageResultado.setVisibility(View.VISIBLE);
                    imageResultado.setImageResource(R.drawable.feliz);
                    invalidateOptionsMenu();

                } else if (mediaGeral <= 2.7) {//Reprovado sem final

                    finalNecessaria.setVisibility(View.VISIBLE);
                    finalNecessaria.setText("Você foi Reprovado");
                    buttonCalcularMedia.setText("Limpar");
                    imageResultado.setVisibility(View.VISIBLE);
                    imageResultado.setImageResource(R.drawable.triste);
                    invalidateOptionsMenu();

                } else {//Notas menores que 7
                    layoutProvaFinal.setVisibility(View.VISIBLE);
                    imageResultado.setVisibility(View.INVISIBLE);
                    finalNecessaria.setVisibility(View.VISIBLE);
                    finalNecessaria.setText("Você precisará tirar "+
                            new DecimalFormat("0.00").format(finalNecessaria(mediaGeral)) +" na final!");
                    buttonCalcularMedia.setText("Adicionar Final");
                    invalidateOptionsMenu();
                }

            } else if (buttonCalcularMedia.getText().toString().equals("Limpar")) {

                limparEntradas();
                invalidateOptionsMenu();
                spinnerDisciplina.setText("");
                disciplina = null;

            } else {
                float notaFinal = 0;
                if(!this.notaFinal.getText().toString().isEmpty()){ // se nao estiver vazio
                    notaFinal = Float.parseFloat(this.notaFinal.getText().toString());
                }


                finalNecessaria.setVisibility(View.VISIBLE);
                mediaGeral = mediaResultado(notaFinal);

                if (notaFinal >= finalNecessaria(mediaGeral)) {
                    finalNecessaria.setText("Você foi Aprovado");
                    imageResultado.setVisibility(View.VISIBLE);
                    imageResultado.setImageResource(R.drawable.feliz);
                } else {
                    finalNecessaria.setText("Você foi Reprovado");
                    imageResultado.setVisibility(View.VISIBLE);
                    imageResultado.setImageResource(R.drawable.triste);
                }

                //finalNecessaria.setVisibility(View.GONE);
                media.setText("Média Geral: " + new DecimalFormat("0.00").format(mediaGeral));
                buttonCalcularMedia.setText("Limpar");
                invalidateOptionsMenu();

            }
        }
    }

    private float mediaResultado(float notaFinal) {
        float unidade1 = 0;
        float unidade2 = 0;
        float unidade3 = 0;

        if(!this.unidade1.getText().toString().isEmpty()){
            unidade1 = Float.parseFloat(this.unidade1.getText().toString());
        }if(!this.unidade2.getText().toString().isEmpty()){
            unidade2 = Float.parseFloat(this.unidade2.getText().toString());
        }
        if(!this.unidade3.getText().toString().isEmpty()){
            unidade3 = Float.parseFloat(this.unidade3.getText().toString());
        }

        float resultado;

        if (notaFinal != 0) {
            resultado = (unidade1 + unidade2 + unidade3 + notaFinal)/4;
        } else {
            resultado = (unidade1 + unidade2 + unidade3)/3;
        }

        return resultado;
    }

    private float finalNecessaria(float media) {
        float teste = 2.80f;
        if (media == teste) {
            return 10;
        }

        return (50 - (7*media))/3;
    }

    private void configurarSpinner() {

        ArrayAdapter<Disciplina> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, disciplinasList);

        spinnerDisciplina.setAdapter(adapter);
    }

    private void salvarNotas() {
        float unidade1 = 0;
        float unidade2 = 0;
        float unidade3 = 0;

        if(!this.unidade1.getText().toString().isEmpty()){
            unidade1 = Float.parseFloat(this.unidade1.getText().toString());
        }
        if(!this.unidade2.getText().toString().isEmpty()){
            unidade2 = Float.parseFloat(this.unidade2.getText().toString());
        }
        if(!this.unidade3.getText().toString().isEmpty()){
            unidade3 = Float.parseFloat(this.unidade3.getText().toString());
        }

        if (!notaFinal.getText().toString().isEmpty()) {//Verificando se nao estar vazio
            float notaFinal = Float.parseFloat(this.notaFinal.getText().toString());
            disciplina.setNotaFinal(notaFinal);
        }

        disciplina.setUnidade1(unidade1);
        disciplina.setUnidade2(unidade2);
        disciplina.setUnidade3(unidade3);
        disciplina.setMedia(mediaGeral);


    }

    private void limparEntradas() {
        buttonCalcularMedia.setText("Calcular Média");
        layoutProvaFinal.setVisibility(View.GONE);
        media.setVisibility(View.INVISIBLE);
        finalNecessaria.setVisibility(View.GONE);
        imageResultado.setVisibility(View.INVISIBLE);
        unidade1.setText("");
        unidade2.setText("");
        unidade3.setText("");
        notaFinal.setText("");
    }
}
