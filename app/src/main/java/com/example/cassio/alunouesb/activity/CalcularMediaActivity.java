package com.example.cassio.alunouesb.activity;

import java.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.DisciplinaDAO;
import com.example.cassio.alunouesb.model.Disciplina;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class CalcularMediaActivity extends AppCompatActivity {

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

    private List<Disciplina> disciplinasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_media);
        setTitle("Calcular Média");

        spinnerDisciplina = (MaterialBetterSpinner) findViewById(R.id.spinner_disciplina);
        unidade1 = (EditText) findViewById(R.id.text_unidade_1);
        unidade2 = (EditText) findViewById(R.id.text_unidade_2);
        unidade3 = (EditText) findViewById(R.id.text_unidade_3);
        layoutProvaFinal = (LinearLayout) findViewById(R.id.layout_prova_final);
        notaFinal = (EditText) findViewById(R.id.text_final);
        media = (TextView) findViewById(R.id.text_media);
        finalNecessaria = (TextView) findViewById(R.id.text_final_necessaria);
        imageResultado = (ImageView) findViewById(R.id.image_resultado);
        buttonCalcularMedia = (Button) findViewById(R.id.button_calcular_media);

        layoutProvaFinal.setVisibility(View.GONE);
        media.setVisibility(View.INVISIBLE);
        finalNecessaria.setVisibility(View.GONE);
        imageResultado.setVisibility(View.INVISIBLE);

        configurarSpinner();

        spinnerDisciplina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                disciplina = disciplinasList.get(i);

                if (disciplina.getUnidade1() != 0) {
                    unidade1.setText(String.valueOf(disciplina.getUnidade1()));
                }

                if (disciplina.getUnidade2() != 0) {
                    unidade2.setText(String.valueOf(disciplina.getUnidade2()));

                }

                if (disciplina.getUnidade3() != 0) {
                    unidade3.setText(String.valueOf(disciplina.getUnidade3()));

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (disciplina != null) {

            getMenuInflater().inflate(R.menu.menu_adicionar_lembrete, menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void calcularMedia(View view) {
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

            float notaFinal = Float.parseFloat(this.notaFinal.getText().toString());

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

    private float mediaResultado(float notaFinal) {
        float unidade1 = Float.parseFloat(this.unidade1.getText().toString());
        float unidade2 = Float.parseFloat(this.unidade2.getText().toString());
        float unidade3 = Float.parseFloat(this.unidade3.getText().toString());
        float resultado;

        if (notaFinal != 0) {
            resultado = (unidade1 + unidade2 + unidade3 + notaFinal)/4;
        } else {
            resultado = (unidade1 + unidade2 + unidade3)/3;
        }

        return resultado;
    }

    private float finalNecessaria(float media) {
        Float teste = 2.80f;
        if (media == teste) {
            return 10;
        }

        return (50 - (7*media))/3;
    }

    private void configurarSpinner() {
        disciplinasList = DisciplinaDAO.getInstance(this).buscarTodos(PrincipalActivity.USUARIO.getIdSemestre());

        ArrayAdapter<Disciplina> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, disciplinasList);

        spinnerDisciplina.setAdapter(adapter);
    }

    private void salvarNotas() {
        float unidade1 = Float.parseFloat(this.unidade1.getText().toString());
        float unidade2 = Float.parseFloat(this.unidade2.getText().toString());
        float unidade3 = Float.parseFloat(this.unidade3.getText().toString());

        if (!notaFinal.getText().toString().isEmpty()) {//Verificando se nao estar vazio
            float notaFinal = Float.parseFloat(this.notaFinal.getText().toString());
            disciplina.setNotaFinal(notaFinal);
        }

        disciplina.setUnidade1(unidade1);
        disciplina.setUnidade2(unidade2);
        disciplina.setUnidade3(unidade3);
        disciplina.setMedia(mediaGeral);

        DisciplinaDAO.getInstance(this).alterarRegistros(disciplina);

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
