package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.LembreteDAO;
import com.example.cassio.alunouesb.model.Lembrete;

public class LembreteActivity extends AppCompatActivity {

    private Lembrete lembrete;

    private EditText titulo;
    private EditText mensagem;

    private boolean permitirEdicao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrete);
        setTitle("Lembrete");

        titulo = (EditText) findViewById(R.id.text_titulo_lembrete);
        mensagem = (EditText) findViewById(R.id.text_mensagem_lembrete);

        lembrete = (Lembrete) getIntent().getSerializableExtra("lembrete");

        titulo.setText(lembrete.getTitulo());
        mensagem.setText(lembrete.getMensagem());

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
            case R.id.action_cancelar:
                finish();
                break;

            case R.id.action_salvar:
                this.salvar();
                break;

            case R.id.action_editar:
                habilitarEdicao();
                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {
        alterarDados();

        LembreteDAO.getInstance(this).alteraRegistro(lembrete);

        Intent intent = new Intent();
        intent.putExtra("lembrete", lembrete);
        setResult(1, intent);
        finish();
    }

    private void alterarDados() {
        lembrete.setTitulo(titulo.getText().toString());
        lembrete.setMensagem(mensagem.getText().toString());
        lembrete.setData(System.currentTimeMillis());
    }

    private void habilitarEdicao() {
        this.permitirEdicao = (!permitirEdicao);
        titulo.setEnabled(permitirEdicao);
        mensagem.setEnabled(permitirEdicao);
    }
}
