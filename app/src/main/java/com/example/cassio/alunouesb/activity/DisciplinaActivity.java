package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.DisciplinaDAO;
import com.example.cassio.alunouesb.database.dao.HorarioDAO;
import com.example.cassio.alunouesb.database.dao.ProfessorDAO;
import com.example.cassio.alunouesb.database.dao.SemestreDAO;
import com.example.cassio.alunouesb.dialog.DialogAdicionarHorario;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Horario;
import com.example.cassio.alunouesb.model.Professor;

import java.util.List;

public class DisciplinaActivity extends AppCompatActivity implements DialogAdicionarHorario.OnClickDialog {

    private Disciplina disciplina;
    private List<Horario> horarioList;
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
    private TextView idSemestre;
    private ListView listHorarios;

    private Switch switchMenu;

    private boolean permitirEdicao = true;

    private int REQUEST_NOVO_HORARIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);
        setTitle("Disciplina");

        nome = (EditText) findViewById(R.id.text_disciplina_nome);
        abreviatura = (EditText) findViewById(R.id.text_disciplina_abreviatura);
        nomeProfessor = (EditText) findViewById(R.id.text_disciplina_professor);
        emailProfessor = (EditText) findViewById(R.id.text_email_professor);
        unidade1 = (EditText) findViewById(R.id.text_unidade_1);
        unidade2 = (EditText) findViewById(R.id.text_unidade_2);
        unidade3 = (EditText) findViewById(R.id.text_unidade_3);
        notaMedia = (EditText) findViewById(R.id.text_media);
        notaFinal = (EditText) findViewById(R.id.text_final);
        idSemestre = (TextView) findViewById(R.id.text_disciplina_semestre);
        listHorarios = (ListView) findViewById(R.id.list_horarios);

        disciplina = (Disciplina) getIntent().getSerializableExtra("disciplina");

        horarioList = HorarioDAO.getInstance(this).buscarTodos(disciplina.getId());
        professor = ProfessorDAO.getInstance(this).carregaDadoById(disciplina.getIdProfessor());

        adapter = new ArrayAdapter<Horario>(this, android.R.layout.simple_list_item_1,horarioList);
        listHorarios.setAdapter(adapter);


        nome.setText(disciplina.getNome());
        abreviatura.setText(disciplina.getAbreviacao());
        nomeProfessor.setText(professor.getNome());
        emailProfessor.setText(professor.getEmail());

        if (disciplina.getUnidade1() != 0) {
            unidade1.setText(String.valueOf(disciplina.getUnidade1()));
        }
        if (disciplina.getUnidade2() != 0) {
            unidade2.setText(String.valueOf(disciplina.getUnidade2()));
        }
        if (disciplina.getUnidade3() != 0) {
            unidade3.setText(String.valueOf(disciplina.getUnidade3()));
        }
        if (disciplina.getMedia() != 0) {
            notaMedia.setText(String.valueOf(disciplina.getMedia()));
        }
        if (disciplina.getNotaFinal() != 0) {
            notaFinal.setText(String.valueOf(disciplina.getNotaFinal()));
        }

        idSemestre.setText("Semestre "+SemestreDAO.getInstance(this).carregaDadoById(disciplina.getIdSemestre()).getSemestre());

        habilitarEdicao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                salvar();
                break;

            case R.id.action_editar:
                habilitarEdicao();
                invalidateOptionsMenu();
                break;

            case R.id.action_cancelar:
                finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Horario horario;

        //Caso adicionou um novo Item
        if (requestCode == REQUEST_NOVO_HORARIO && data != null) {
            horario = (Horario) data.getSerializableExtra("horario");
            adapter.add(horario);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void salvar() {
        alterarDados();

        DisciplinaDAO.getInstance(this).alterarRegistros(disciplina);
        ProfessorDAO.getInstance(this).alterarRegistro(professor);

        Intent intent = new Intent();
        intent.putExtra("disciplina", disciplina);
        setResult(1, intent);
        finish();
    }

    private void alterarDados() {
        disciplina.setNome(nome.getText().toString());
        disciplina.setAbreviacao(abreviatura.getText().toString());

        professor.setNome(nomeProfessor.getText().toString());
        professor.setEmail(emailProfessor.getText().toString());

        if (!unidade1.getText().toString().isEmpty()) {
            disciplina.setUnidade1(Float.valueOf(unidade1.getText().toString()));
        }

        if (!unidade2.getText().toString().isEmpty()) {
            disciplina.setUnidade2(Float.valueOf(unidade2.getText().toString()));
        }

        if (!unidade3.getText().toString().isEmpty()) {
            disciplina.setUnidade3(Float.valueOf(unidade3.getText().toString()));
        }

        if (!notaMedia.getText().toString().isEmpty()) {
            disciplina.setMedia(Float.valueOf(notaMedia.getText().toString()));
        }

        if (!notaFinal.getText().toString().isEmpty()) {
            disciplina.setNotaFinal(Float.valueOf(notaFinal.getText().toString()));
        }
    }

    public void chamarTelaAdicionarHorario(View view) {

        DialogAdicionarHorario add = new DialogAdicionarHorario();
        add.setOnClickDialog(this);
        add.show(getSupportFragmentManager(), "");
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
        //this.notaMedia.setEnabled(permitirEdicao);
        //this.notaFinal.setEnabled(permitirEdicao);
    }

    @Override
    public void onClickDialog(DialogAdicionarHorario.ViewHolder view) {

        int dia = view.spinnerDia.getSelectedItemPosition();
        int horario = view.spinnerHorario.getSelectedItemPosition();

        //int dia = dias.indexOf((String) spinnerDia.getSelectedItem());
        //int horario = horarios.indexOf((String) spinnerHorario.getSelectedItem());

        Horario horarioTemp = new Horario(null, dia, horario, disciplina.getId());

        long id = HorarioDAO.getInstance(this).inserirDados(horarioTemp);

        if (id < 0) {
            Toast.makeText(getApplicationContext(), "Erro, ao inserir", Toast.LENGTH_LONG).show();
        } else {
            horarioTemp.setId(id);
            //Intent intent = new Intent();
            //intent.putExtra("horario", horarioTemp);
            //setResult(1, intent);
            //finish();
        }

    }
}
