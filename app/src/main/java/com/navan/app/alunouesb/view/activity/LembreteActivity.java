package com.navan.app.alunouesb.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.app.presenter.login.LembretePresenter;
import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.contract.ILembreteContract;
import com.navan.app.alunouesb.data.model.Lembrete;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LembreteActivity extends AppCompatActivity implements ILembreteContract.View {

    private Lembrete lembrete;

    private ProgressBar progressBar;
    private EditText titulo;
    private EditText mensagem;

    private ILembreteContract.Presenter iPresenter;

    private boolean permitirEdicao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrete);
        setTitle("Lembrete");

        iPresenter = new LembretePresenter(this);

        progressBar = findViewById(R.id.progressBar);
        titulo = findViewById(R.id.text_titulo_lembrete);
        mensagem = findViewById(R.id.text_mensagem_lembrete);

        lembrete = (Lembrete) getIntent().getSerializableExtra("lembrete");

        showData();
    }

    private void showData() {
        titulo.setText(lembrete.getTitulo());
        mensagem.setText(lembrete.getMensagem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lembrete, menu);
        menu.findItem(R.id.action_salvar).setVisible(permitirEdicao);
        menu.findItem(R.id.action_editar).setVisible(!permitirEdicao);
        menu.findItem(R.id.excluir_lembrete).setVisible(!permitirEdicao);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.excluir_lembrete:
                iPresenter.remove(lembrete);
                break;

            case R.id.action_salvar:
                lembrete.setTitulo(titulo.getText().toString());
                lembrete.setMensagem(mensagem.getText().toString());
                iPresenter.update(lembrete);
                break;

            case R.id.action_editar:
                habilitarEdicao();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void habilitarEdicao() {
        this.permitirEdicao = (!permitirEdicao);
        titulo.setEnabled(permitirEdicao);
        mensagem.setEnabled(permitirEdicao);
        invalidateOptionsMenu();
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
    public void onListSuccess(@NotNull List<? extends Lembrete> list) { }

    @Override
    public void onCreatedSuccess(@NotNull Lembrete item) { }

    @Override
    public void onUpdateSuccess(@NotNull Lembrete item) {
        Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        habilitarEdicao();
    }

    @Override
    public void onRemovedSuccess(@NotNull Lembrete item) {
        Toast.makeText(this, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailure(@NotNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
