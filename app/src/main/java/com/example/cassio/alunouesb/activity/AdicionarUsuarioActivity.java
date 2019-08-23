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
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.SemestreDAO;
import com.example.cassio.alunouesb.database.dao.UsuarioDAO;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AdicionarUsuarioActivity extends AppCompatActivity {

    private EditText usuarioNome;
    private MaterialBetterSpinner usuarioCurso;
    private EditText usuarioSemestre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_usuario);
        setTitle("Novo Usuário");

        usuarioNome = (EditText) findViewById(R.id.text_nome);
        usuarioCurso = (MaterialBetterSpinner) findViewById(R.id.spinner_curso);
        usuarioSemestre = (EditText) findViewById(R.id.text_semestre);

        String[] cursos = {"Administração", "Agronomia", "Biologia", "Cinema", "Ciências Socias", "Ciência da Computação",
                "Contabilidade", "Direito", "Economia", "Educação Física", "Engenharia Florestal", "Filosofia",
                "Física", "Geografia", "História", "Matemática", "Medicina", "Pedagogia", "Psicologia"};

        ArrayAdapter<String> adapterCurso = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cursos);
        usuarioCurso.setAdapter(adapterCurso);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_adicionar_lembrete, menu);
        menu.findItem(R.id.action_cancelar).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.action_salvar:
                this.salvarUsuario();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void salvarUsuario() {

        String nome = usuarioNome.getText().toString();
        String curso = usuarioCurso.getText().toString();
        String semestre = usuarioSemestre.getText().toString();

//        if (!nome.isEmpty() && !curso.isEmpty() && !semestre.isEmpty()) {
//
//            Usuario usuario = new Usuario();
//            usuario.setNome(nome);
//            usuario.setCurso(curso);
//
//            long idUsuario = UsuarioDAO.getInstance(this).inserirDados(usuario);
//            usuario.setId(idUsuario);
//
//            Semestre semestreTemp = new Semestre(null, semestre, idUsuario);
//            long idSemestre = SemestreDAO.getInstance(this).inserirDados(semestreTemp);
//            semestreTemp.setId(idSemestre);
//
//            usuario.setIdSemestre(idSemestre);
//
//            UsuarioDAO.getInstance(this).alterarRegistro(usuario);
//
//            Intent intent = new Intent();
//            intent.putExtra("usuario", usuario);
//            setResult(1, intent);
//
//            finish();
//
//        } else {
//            Toast toast = Toast.makeText(this, "Dados insuficentes", Toast.LENGTH_LONG);
//            toast.show();
//        }
    }
}
