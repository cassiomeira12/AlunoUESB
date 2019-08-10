package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;

public class LoginActivity extends AppCompatActivity {
    private Button buttonEntrar;
    private TextView email;
    private TextView senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonEntrar = findViewById(R.id.button_entrar_login);
        email = findViewById(R.id.text_email_login);
        senha = findViewById(R.id.text_senha_login);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){
                    //fazer login

                    return;
                }else{ // campo vazio
                    Toast.makeText(LoginActivity.this, "Campos inválidos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean verificarCampos() {
        if(!email.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    public void cadastro (View v){
        Intent activityCadastro = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(activityCadastro);
    }
}
