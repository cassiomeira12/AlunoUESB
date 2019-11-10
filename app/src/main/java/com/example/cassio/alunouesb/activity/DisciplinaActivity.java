package com.example.cassio.alunouesb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.db.References;
import com.example.cassio.alunouesb.dialog.DialogAdicionarHorario;
import com.example.cassio.alunouesb.dialog.DialogAdicionarHorario.ViewHolder;
import com.example.cassio.alunouesb.dialog.DialogExcluir;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Horario;
import com.example.cassio.alunouesb.model.Professor;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import static java.util.Date.parse;

public class DisciplinaActivity extends AppCompatActivity implements DialogExcluir.OnExcluir, DialogAdicionarHorario.OnClickDialog {

    private Semestre semestre =  PrincipalActivity.semestre;

    // lista com todas as disciplinas do usuario
    ArrayList<Disciplina> disciplinas = (ArrayList<Disciplina>) semestre.getDisciplinaList();

    private Disciplina disciplina;
    private ArrayList<Horario> horarioList;
    private Professor professor;
    private ArrayAdapter<Horario> adapter;

    private EditText nome;
    private EditText abreviatura;
    private EditText nomeProfessor;
    private EditText emailProfessor;
    private EditText unidade1;
    private EditText unidade2;
    private EditText unidade3;
    private EditText notaMedia;
    private EditText notaFinal;
    private ImageView emotion;
    private ListView listHorarios;
    private Menu menu;

    private String excluir; // essa variavel diz qual elemento ira ser excluido, o horario da lista de horarios ou a disciplina
    //pois ambos tem o mesm DialogExcluir

    private int indiceExcluir;

    private TextView valueNumberPicker;

    private boolean permitirEdicao = true;

    DecimalFormat formatter = new DecimalFormat("#.##"); // formato para decimal com duas casas decimais


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);
        setTitle("Disciplina");

        //link das Views
        nome = findViewById(R.id.text_disciplina_nome);
        abreviatura = findViewById(R.id.text_disciplina_abreviatura);
        nomeProfessor = findViewById(R.id.text_disciplina_professor);
        emailProfessor =  findViewById(R.id.text_email_professor);
        unidade1 =  findViewById(R.id.text_unidade_1);
        unidade2 =  findViewById(R.id.text_unidade_2);
        unidade3 =  findViewById(R.id.text_unidade_3);
        notaMedia =  findViewById(R.id.text_media);
        notaFinal = findViewById(R.id.text_final);
        listHorarios = findViewById(R.id.list_horarios);
        valueNumberPicker = findViewById(R.id.number_picker_value);
        //Number Picker
        Button negativoNumberPicker = findViewById(R.id.number_picker_negative);
        Button positivoNumberPicker = findViewById(R.id.number_picker_positive);
        emotion = findViewById(R.id.emotion);

        //pegar disciplina
        int idDisciplina = (int) getIntent().getSerializableExtra("idDisciplina");
        disciplina = semestre.getDisciplinaList().get(idDisciplina);

        // cria uma lista com as disciplinas do usuario
        disciplinas = (ArrayList<Disciplina>) semestre.getDisciplinaList();

        for (Disciplina disc : disciplinas) {
            if(disc.getNome().equals(disciplina.getNome())){// verifica qual disciplina na lista do usuario possui o mesmo nome da disciplina que foi pega na intent
                this.disciplina = disc; // coloca a disciplina na variavel disciplina local para facilitar a edicao
            }
        }

        professor = disciplina.getProfessor();
        horarioList = disciplina.getHorarioList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,horarioList);
        listHorarios.setAdapter(adapter);


        nome.setText(disciplina.getNome());
        abreviatura.setText(disciplina.getAbreviacao());
        nomeProfessor.setText(professor.getNome());
        emailProfessor.setText(professor.getEmail());


        // colocar a quantidade de faltas no TextView
        valueNumberPicker.setText(String.valueOf(disciplina.getFaltas()));

        // ao clicar no "negativo" = decrementa o numero de faltas
        negativoNumberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFalta(-1);
            }
        });

        // ao pressionar no "negativo" = decrementa o numero de faltas
        negativoNumberPicker.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setFalta(-5);
                return false;
            }
        });

        // ao clicar no "positivo" = incrementa o numero de faltas
        positivoNumberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFalta(1);
            }
        });

        // ao pressionar no "positivo" = incrementa o numero de faltas
        positivoNumberPicker.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setFalta(5);
                return false;
            }
        });

        if (disciplina.getUnidade1() != 0) {
            unidade1.setText(formatter.format(disciplina.getUnidade1()));
        }
        if (disciplina.getUnidade2() != 0) {
            unidade2.setText(formatter.format(disciplina.getUnidade2()));
        }
        if (disciplina.getUnidade3() != 0) {
            unidade3.setText(formatter.format(disciplina.getUnidade3()));
        }
        if (disciplina.getMedia() != 0) {
            notaMedia.setText(formatter.format(disciplina.getMedia()));
        }
        if (disciplina.getNotaFinal() != 0) {
            notaFinal.setText(formatter.format(disciplina.getNotaFinal()));
        }

        listHorarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //indice do horario a ser excluido
                indiceExcluir = i;
                chamarTelaConfimarDeleteHorario();

                return false;
            }
        });

        habilitarEdicao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lembrete, menu);
        menu.findItem(R.id.action_salvar).setVisible(permitirEdicao);
        menu.findItem(R.id.action_editar).setVisible(!permitirEdicao);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_salvar:
                menu.findItem(R.id.action_salvar).setVisible(!permitirEdicao);
                menu.findItem(R.id.action_editar).setVisible(permitirEdicao);
                invalidateOptionsMenu();
                salvar();
                habilitarEdicao();
                break;

            case R.id.action_editar:
                habilitarEdicao();
                invalidateOptionsMenu();
                break;

            case R.id.excluir_lembrete:
                excluirDisciplina();
        }


        return super.onOptionsItemSelected(item);
    }

    private void excluirDisciplina() {
        excluir = "disciplina";
        DialogExcluir excluir = new DialogExcluir();
        excluir.setOnExcluir(this);
        excluir.show(getSupportFragmentManager(), "Deseja excluir esta disciplina?");
    }

    private void salvar() {
        // recebe os valores para o nome da disciplina
        disciplina.setNome(nome.getText().toString());

        // recebe os valores para abreviacao do nome da disciplina
        disciplina.setAbreviacao(abreviatura.getText().toString());

        professor.setNome(nomeProfessor.getText().toString());
        professor.setEmail(emailProfessor.getText().toString());


        //notas
        // se nota da primeira unidade for diferente de vazio
        if (!unidade1.getText().toString().isEmpty()) {
            disciplina.setUnidade1(Float.valueOf(unidade1.getText().toString()));// salva o valor no Objeto que sera enviado ao bd
        }else{
            unidade1.setText("");
            disciplina.setUnidade1(0);
        }

        if (!unidade2.getText().toString().isEmpty()) {
            disciplina.setUnidade2(Float.valueOf(unidade2.getText().toString()));
        }else{
            unidade2.setText("");
            disciplina.setUnidade2(0);
        }

        if (!unidade3.getText().toString().isEmpty()) {
            disciplina.setUnidade3(Float.valueOf(unidade3.getText().toString()));
        }else{
            unidade3.setText("");
            disciplina.setUnidade3(0);
        }

        float media =  (disciplina.getUnidade1() + disciplina.getUnidade2() + disciplina.getUnidade3()) / 3;

        Log.e("Media", "" + media);

        if(media >= 7){

            NumberFormat formatter = new DecimalFormat("0.00"); // formato para decimal com duas casas decimais
            media = Float.parseFloat(formatter.format(media));

            disciplina.setMedia(media);
            notaMedia.setText(formatter.format(disciplina.getMedia()));

            notaFinal.setText("");
            disciplina.setNotaFinal(0);

        }else{
            if(media > 0){
                float mNotaFinal;

                if(notaFinal.getText().toString().isEmpty()){ // se vazio, nota final recebe zero
                    mNotaFinal = 0;
                }else{ // se nao vazio, nota final recebe o valor que esta no TextEdit
                    mNotaFinal = Float.valueOf(notaFinal.getText().toString());
                }

                double aux1 = media * 0.7;
                double aux2 = mNotaFinal * 0.3;

                media = (float )(aux1 + aux2);

                disciplina.setMedia(media);
                notaMedia.setText(formatter.format(media));
            }
        }

        if (!notaFinal.getText().toString().isEmpty()) {
            disciplina.setNotaFinal(Float.valueOf(notaFinal.getText().toString()));
        }

        //mudar Icon de emocao
        if(disciplina.getNotaFinal() < 5){
            emotion.setImageResource(R.drawable.triste);
        }else{
            emotion.setImageResource(R.drawable.feliz);
        }

        //alterar o valor das faltas no banco de dados
        salvarBancoDeDados();

    }

    public void chamarTelaAdicionarHorario(View view) {

        DialogAdicionarHorario add = new DialogAdicionarHorario();
        add.setOnClickDialog(this);
        add.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClickDialog(ViewHolder view) {

        String dia = (String) view.spinnerDia.getSelectedItem();
        String horario = (String) view.spinnerHorario.getSelectedItem();
        int idTurno = view.radioGroup.getCheckedRadioButtonId();

        int turno = getTurno(idTurno);

        int intDia = convertDia(dia);
        int intHorario = convertHorario(horario);

        disciplina.adicionarHorario(turno, intDia, intHorario);
        carregaHorarios();

        salvarBancoDeDados();

    }

    private int getTurno(int idTurno) {
        switch (idTurno){
            case R.id.radio_button_manha:
                return 0;
            case R.id.radio_button_tarde:
                return 1;
            case R.id.radio_button_noite:
                return 2;
        }
        return -1;
    }

    public void chamarTelaConfimarDeleteHorario(){
        excluir = "horario";
        DialogExcluir remove = new DialogExcluir();
        remove.setOnExcluir(this);
        remove.show(getSupportFragmentManager(), "Deseja excluir este horário?");
    }

    @Override
    public void onExcluir() {

        switch (excluir){
            case "disciplina":
                disciplinas.remove(disciplina);
                finish();
                break;
            case "horario":
                adapter.remove(adapter.getItem(indiceExcluir));
                break;
        }

        salvarBancoDeDados();
    }

    private void habilitarEdicao() {

        this.permitirEdicao = (!this.permitirEdicao);

        this.nome.setEnabled(permitirEdicao);
        this.abreviatura.setEnabled(permitirEdicao);
        this.nomeProfessor.setEnabled(permitirEdicao);
        this.emailProfessor.setEnabled(permitirEdicao);
        this.unidade1.setEnabled(permitirEdicao);
        this.unidade2.setEnabled(permitirEdicao);
        this.unidade3.setEnabled(permitirEdicao);
//        this.notaMedia.setEnabled(permitirEdicao); //media eh um calculo das 3 unidades. Nao deve ser editada diretamente
        this.notaFinal.setEnabled(permitirEdicao);
    }

    // metodo responsavel por fazer a alteracao das faltas (incremento e decremento)
    private void setFalta(int valor){

        int faltas = disciplina.getFaltas();

        if(faltas + valor < 0 || faltas + valor > 100){
            return;
        }

        faltas += valor;

        valueNumberPicker.setText(String.valueOf(faltas));

        disciplina.setFaltas(faltas);

        //alterar o valor das faltas no banco de dados
        salvarBancoDeDados();

    }

    private void carregaHorarios() {
        horarioList = disciplina.getHorarioList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,horarioList);
        listHorarios.setAdapter(adapter);
    }

    private void salvarBancoDeDados() {
        //alterar o valor das faltas no banco de dados

        String semestreSelecionado =  PrincipalActivity.semestre.getSemestre();
        References.db.collection("/semestres").document(semestreSelecionado).set(PrincipalActivity.semestre);
    }

    private int convertDia(String dia) {
        int resultado = 0;

        switch (dia) {
            case "Segunda":
                resultado = 0;
                break;

            case "Terça":
                resultado = 1;
                break;

            case "Quarta":
                resultado = 2;
                break;

            case "Quinta":
                resultado = 3;
                break;

            case "Sexta":
                resultado = 4;
                break;

            case "Sábado":
                resultado = 5;
                break;
        }
        return resultado;
    }

    public int convertHorario(String horario){
        int resultado = 0;

        switch (horario){
            case "1º Horário":
                resultado = 0;
                break;
            case "2º Horário":
                resultado = 1;
                break;
            case "3º Horário":
                resultado = 2;
                break;
        }
        return resultado;
    }

}
