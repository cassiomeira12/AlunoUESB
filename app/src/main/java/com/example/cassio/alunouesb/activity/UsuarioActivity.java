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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.SemestreDAO;
import com.example.cassio.alunouesb.database.dao.UsuarioDAO;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

public class UsuarioActivity extends AppCompatActivity {

    private EditText usuarioNome;
    private MaterialBetterSpinner spinnerCurso;
    private EditText matricula;
    private Spinner spinnerSemestre;
    private EditText novoSemestre;

    public static List<Semestre> semestreList;
    private ArrayAdapter<Semestre> arrayAdapter;
    private Usuario usuario = PrincipalActivity.USUARIO;

    private boolean permitirEdicao = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        setTitle("Meus Dados");

        usuarioNome = (EditText) findViewById(R.id.text_usuario_nome);
        spinnerCurso = (MaterialBetterSpinner) findViewById(R.id.spinner_usuario_curso);
        matricula = (EditText) findViewById(R.id.text_matricula);
        novoSemestre = (EditText) findViewById(R.id.text_novo_semestre);


        String[] cursos = {"Administração", "Agronomia", "Biologia", "Cinema", "Ciências Socias", "Ciência da Computação",
                            "Contabilidade", "Direito", "Economia", "Educação Física", "Engenharia Florestal", "Filosofia",
                            "Física", "Geografia", "História", "Matemática", "Medicina", "Pedagogia", "Psicologia"};

        ArrayAdapter<String> adapterCurso = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cursos);
        spinnerCurso.setAdapter(adapterCurso);


        spinnerSemestre = (Spinner) findViewById(R.id.spinner_semestre);
        semestreList = SemestreDAO.getInstance(this).buscarTodos();
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, semestreList);
        spinnerSemestre.setAdapter(arrayAdapter);

        usuarioNome.setText(usuario.getNome());
        spinnerCurso.setText(usuario.getCurso());
        matricula.setText(String.valueOf(usuario.getMatricula()));
        spinnerSemestre.setSelection(arrayAdapter.getPosition(PrincipalActivity.SEMESTRE));

        /*
        spinnerSemestre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Semestre semestre = semestreList.get(i);

                Semestre semestre = (Semestre) adapterView.getItemAtPosition(i);

                PrincipalActivity.SEMESTRE = semestre;
                PrincipalActivity.USUARIO.setIdSemestre(semestre.getId());
                //UsuarioActivity.crud.alterarRegistro(PrincipalActivity.USUARIO);
                //Toast.makeText(getApplicationContext(), String.valueOf(PrincipalActivity.USUARIO.getIdSemestre()), Toast.LENGTH_LONG).show();
            }
        });
        */
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
                salvarModificacoes();
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



    public void adicionarSemestre(View view) {

        String semestre = novoSemestre.getText().toString();

        Semestre semestreTemp = new Semestre(null, semestre, PrincipalActivity.USUARIO.getId());

        long id = SemestreDAO.getInstance(this).inserirDados(semestreTemp);

        if (id < 0) {
            Toast.makeText(getApplicationContext(), "Erro, ao inserir", Toast.LENGTH_LONG).show();
        } else {
            semestreTemp.setId(id);
            arrayAdapter.add(semestreTemp);
        }

        novoSemestre.setText("");
    }

    public void salvarModificacoes() {

        String nome = usuarioNome.getText().toString();
        String curso = spinnerCurso.getText().toString();
        int matricula = Integer.parseInt(this.matricula.getText().toString());
        Semestre semestre = ((Semestre) spinnerSemestre.getSelectedItem());

        usuario.setNome(nome);
        usuario.setCurso(curso);
        usuario.setMatricula(matricula);
        usuario.setIdSemestre(semestre.getId());

        PrincipalActivity.SEMESTRE = semestre;
        PrincipalActivity.USUARIO = usuario;

        UsuarioDAO.getInstance(this).alterarRegistro(usuario);


        Intent intent = new Intent();
        intent.putExtra("usuario", usuario);
        setResult(1, intent);
        finish();

    }


    private void habilitarEdicao() {

        this.permitirEdicao = (!this.permitirEdicao);

        usuarioNome.setEnabled(permitirEdicao);
        matricula.setEnabled(permitirEdicao);
        spinnerCurso.setEnabled(permitirEdicao);

    }
}
