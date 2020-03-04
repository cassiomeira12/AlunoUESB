package com.navan.app.alunouesb.view.activity;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.contract.IDisciplinaContract;
import com.navan.app.alunouesb.data.db.References;
import com.navan.app.alunouesb.data.model.Disciplina;
import com.navan.app.alunouesb.data.model.Professor;
import com.navan.app.alunouesb.presenter.disciplina.DisciplinaPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdicionarDisciplinaActivity extends AppCompatActivity implements IDisciplinaContract.View {

    private ProgressBar progressBar;

    private EditText nome;
    private EditText abreviatura;
    private EditText nomeProfessor;
    private EditText emailProfessor;
    private IDisciplinaContract.Presenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_disciplina);

        progressBar = findViewById(R.id.progressbar);

        nome = findViewById(R.id.text_disciplina_nome);
        abreviatura = findViewById(R.id.text_disciplina_abreviatura);
        nomeProfessor = findViewById(R.id.text_disciplina_professor);
        emailProfessor = findViewById(R.id.text_disciplina_email_professor);

        iPresenter = new DisciplinaPresenter(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_adicionar_lembrete, menu);
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
                this.salvarDisciplina();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvarDisciplina() {

        String nome = this.nome.getText().toString();
        String abreviatura = this.abreviatura.getText().toString();
        String nomeProfessor = this.nomeProfessor.getText().toString();
        String emailProfessor = this.emailProfessor.getText().toString();

        if (!nome.isEmpty() && !abreviatura.isEmpty() && !nomeProfessor.isEmpty()) {

            Professor professorTemp = new Professor(nomeProfessor, emailProfessor);
            Disciplina disciplina = new Disciplina(nome, abreviatura, professorTemp);

            iPresenter.add(disciplina);

        }
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
    public void onListSuccess(@NotNull List<? extends Disciplina> list) {

    }

    @Override
    public void onCreatedSuccess(@NotNull Disciplina item) {
        finish();

    }

    @Override
    public void onUpdateSuccess(@NotNull Disciplina item) {

    }

    @Override
    public void onRemovedSuccess(@NotNull Disciplina item) {

    }

    @Override
    public void onFailure(@NotNull String message) {

    }
}
