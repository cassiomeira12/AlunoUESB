package com.navan.app.alunouesb.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.navan.app.alunouesb.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView mEmail;
    private TextView mSenha;
    private Button buttonEntrar;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //buttonEntrar = findViewById(R.id.button_entrar_login);
        //mEmail = findViewById(R.id.text_email_login);
        //mSenha = findViewById(R.id.text_senha_login);

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Carregando");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);


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

        mDialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha) // pode-se colocar alguma tela indicamento o LOADING
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Intent telaPrincipal = new Intent(LoginActivity.this, PrincipalActivity.class);
                        telaPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //limpa a pilha de Activities


                        limparCampos();
                        // fecha tela de carregamento SUCESSO
                        mDialog.dismiss();

                        startActivity(telaPrincipal);

                        //buttonEntrar.setEnabled(true); // habilita o botao

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //fecha tela de carregamento FALHA

                        mDialog.dismiss();

                        Toast.makeText(LoginActivity.this, "Falha ao fazer login", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void limparCampos() {
        mEmail.setText("");
        mSenha.setText("");
    }
}
