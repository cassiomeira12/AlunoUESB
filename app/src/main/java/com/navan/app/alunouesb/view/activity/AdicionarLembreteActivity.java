package com.navan.app.alunouesb.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.app.presenter.login.LembretePresenter;
import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.contract.ILembreteContract;
import com.navan.app.alunouesb.data.model.Lembrete;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdicionarLembreteActivity extends AppCompatActivity implements ILembreteContract.View {

    private EditText titulo;
    private EditText mensagem;

    private ILembreteContract.Presenter iPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lembrete);
        setTitle("Adicionar Lembrete");

        iPresenter = new LembretePresenter(this);

        progressBar = findViewById(R.id.progressBar);
        titulo = findViewById(R.id.text_titulo_adicionar_lembrete);
        mensagem = findViewById(R.id.text_mensagem_adicionar_lembrete);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_adicionar_lembrete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancelar:
                finish();
                break;

            case R.id.action_salvar:
                salvarLembrete();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarLembrete() {
        String titulo = this.titulo.getText().toString();
        String mensagem = this.mensagem.getText().toString();
        Lembrete lembrete = new Lembrete(titulo, mensagem);
        iPresenter.add(lembrete);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListSuccess(@NotNull List<? extends Lembrete> list) {

    }

    @Override
    public void onCreatedSuccess(@NotNull Lembrete item) {
        finish();
    }

    @Override
    public void onUpdateSuccess(@NotNull Lembrete item) {

    }

    @Override
    public void onRemovedSuccess(@NotNull Lembrete item) {

    }

    @Override
    public void onFailure(@NotNull String message) {

    }
}
