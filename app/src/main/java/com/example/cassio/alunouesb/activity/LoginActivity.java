package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView mEmail;
    private TextView mSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonEntrar = findViewById(R.id.button_entrar_login);
        mEmail = findViewById(R.id.text_email_login);
        mSenha = findViewById(R.id.text_senha_login);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){
                    //fazer login
                    fazerLogin();
                }else{ // campo vazio
                    Toast.makeText(LoginActivity.this, "Campos inválidos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean verificarCampos() {
        if(!mEmail.getText().toString().isEmpty() && !mSenha.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    public void cadastro (View v){
        Intent activityCadastro = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(activityCadastro);
    }


    public void fazerLogin(){
        String email = mEmail.getText().toString();
        String senha = mSenha.getText().toString();

        //inicia tela de carregamento


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha) // pode-se colocar alguma tela indicamento o LOADING
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        limparCampos();
                        // fecha tela de carregamento SUCESSO


                        Intent telaPrincipal = new Intent(LoginActivity.this, PrincipalActivity.class);
                        telaPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //limpa a pilha de Activities
                        startActivity(telaPrincipal);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fecha tela de carregamento FALHA


                        Toast.makeText(LoginActivity.this, "Falha ao fazer login", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void limparCampos() {
        mEmail.setText("");
        mSenha.setText("");
    }
}
