package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.SemestreDAO;
import com.example.cassio.alunouesb.database.dao.UsuarioDAO;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.weiwangcn.betterspinner.library.BetterSpinner;

public class CadastroActivity extends AppCompatActivity {
    private TextView usuarioNome;
    private TextView usuarioEmail;
    private TextView usuarioSenha;
    private Button mCadastrar;
    private Button mCancelar;
    private TextView usuarioSemestre;
    private BetterSpinner usuarioCurso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        usuarioNome = findViewById(R.id.text_nome);
        usuarioEmail = findViewById(R.id.text_email);
        usuarioSenha = findViewById(R.id.text_senha);
        mCadastrar = findViewById(R.id.button_cadastrar);
        mCancelar = findViewById(R.id.button_cancelar);
        usuarioCurso = findViewById(R.id.spinner_curso);
        usuarioSemestre = findViewById(R.id.text_semestre);

        String[] cursos = {"Administração", "Agronomia", "Biologia", "Cinema", "Ciências Socias", "Ciência da Computação",
                "Contabilidade", "Direito", "Economia", "Educação Física", "Engenharia Florestal", "Filosofia",
                "Física", "Geografia", "História", "Matemática", "Medicina", "Pedagogia", "Psicologia"};

        ArrayAdapter<String> adapterCurso = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cursos);
        usuarioCurso.setAdapter(adapterCurso);


        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verificar se os campos estão correntamente preenchidos
                if(verificar()){
                    // fazer cadastro

                }else{
                    // enviar uma mensagem de erro
                }

            }
        });

        mCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private boolean verificar() {

        // se o nome de usuario, sobrenome, email e senha for diferente de nulo
        if(!usuarioNome.getText().toString().isEmpty() &&
                !usuarioEmail.toString().isEmpty() && !usuarioSenha.getText().toString().isEmpty()){

            return true;
        }
        return false;
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

        if (!nome.isEmpty() && !curso.isEmpty() && !semestre.isEmpty()) {

            Usuario usuario = new Usuario(null, nome, curso);
            long idUsuario = UsuarioDAO.getInstance(this).inserirDados(usuario);
            usuario.setId(idUsuario);

            Semestre semestreTemp = new Semestre(null, semestre, idUsuario);
            long idSemestre = SemestreDAO.getInstance(this).inserirDados(semestreTemp);
            semestreTemp.setId(idSemestre);

            usuario.setIdSemestre(idSemestre);

            UsuarioDAO.getInstance(this).alterarRegistro(usuario);

            Intent telaPrincipal = new Intent(this, PrincipalActivity.class);
            telaPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(telaPrincipal);



        } else {
            Toast toast = Toast.makeText(this, "Dados insuficentes", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
