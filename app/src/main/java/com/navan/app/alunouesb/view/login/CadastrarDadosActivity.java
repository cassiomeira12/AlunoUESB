package com.navan.app.alunouesb.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.navan.app.alunouesb.R;

public class CadastrarDadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_dados);
    }

    public void avancar(View view) {
        //startActivity(new Intent(getApplication(), CadastrarFinalizadoActivity.class));
        //finish();
    }

    public void termosUso(View view) {
        Intent intent = new Intent(getApplication(), TermosAppActivity.class);
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.TERMO_USO);
        startActivity(intent);
    }

    public void politicaPrivacidade(View view) {
        Intent intent = new Intent(getApplication(), TermosAppActivity.class);
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.POLITICA_PRIVACIDADE);
        startActivity(intent);
    }

    public void politicaDados(View view) {
        Intent intent = new Intent(getApplication(), TermosAppActivity.class);
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.POLITICA_DADOS);
        startActivity(intent);
    }
}