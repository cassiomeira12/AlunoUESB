package com.navan.app.alunouesb.view.login;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.navan.app.alunouesb.R;

public class TermosAppActivity extends AppCompatActivity {
    public static String TERMO = "TERMO";
    public static int TERMO_USO = 0, POLITICA_PRIVACIDADE = 1, POLITICA_DADOS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_uso);

        TextView termosApp = findViewById(R.id.txt_termos_app);

        int value = getIntent().getIntExtra(TERMO, 0);
        switch (value) {
            case 0:
                setTitle(getResources().getString(R.string.termos_de_uso));
                termosApp.setText(R.string.termos_uso);
                break;
            case 1:
                setTitle(getResources().getString(R.string.politica_de_privacidade));
                termosApp.setText(R.string.politica_privacidade);
                break;
            case 2:
                setTitle(getResources().getString(R.string.politica_de_dados));
                termosApp.setText(R.string.politica_dados);
                break;
        }

    }

}
