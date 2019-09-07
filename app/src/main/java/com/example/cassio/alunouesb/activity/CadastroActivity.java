package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.weiwangcn.betterspinner.library.BetterSpinner;

public class CadastroActivity extends AppCompatActivity {
    private TextView usuarioNome;
    private TextView usuarioEmail;
    private TextView usuarioSenha;
    private Button mCadastrar;
    private Button mCancelar;
    private TextView usuarioSemestre;
    private BetterSpinner usuarioCurso;
    private Usuario usuario;
    public boolean cadastroRealizado = false; // variavel de controle


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
                    salvarUsuario();
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
        // veriricar se o nome de Usuario está ok
        // verificar se o email está ok
        // verificar se a senha está ok
        // verificar se o semestre digitado está ok

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
                if(verificar()) this.salvarUsuario(); // se vericar retornar true...
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void salvarUsuario() {
        // dar um DROP no banco de dados local para nao haver choque de usuarios


        String nome = usuarioNome.getText().toString();
        String curso = usuarioCurso.getText().toString();
        String semestre = usuarioSemestre.getText().toString();
        String email = usuarioEmail.getText().toString();
        String senha = usuarioSenha.getText().toString();

        usuario = new Usuario(nome, email, senha, curso);

        // instancia o semestre
        Semestre semestreTemp = new Semestre(semestre);

        usuario.addSemestre(semestreTemp);


        //animaçao de carregamento
        // faz cadastro do usuario no firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        String uid = FirebaseAuth.getInstance().getUid();// id do usuario no firebase
                        usuario.setUid(uid);


                        FirebaseFirestore.getInstance().collection("/users").document(uid).set(usuario); // adiciona usuario ao banco de dados do firebase
                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();

                        //FIM ANICACAO DE CARREGAMENTO - SUCESSO

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        //FIM ANICACAO DE CARREGAMENTO - SUCESSO
                        Toast.makeText(CadastroActivity.this, e.getCause().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //login
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha) // pode-se colocar alguma tela indicamento o LOADING
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent telaPrincipal = new Intent(CadastroActivity.this, PrincipalActivity.class);
                        telaPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //limpa a pilha de Activities
                        startActivity(telaPrincipal);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(CadastroActivity.this, "Falha ao fazer login", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
