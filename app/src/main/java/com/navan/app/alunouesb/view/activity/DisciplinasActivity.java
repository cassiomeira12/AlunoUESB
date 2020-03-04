package com.navan.app.alunouesb.view.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.navan.app.alunouesb.contract.IDisciplinaContract;
import com.navan.app.alunouesb.data.db.References;
import com.navan.app.alunouesb.presenter.disciplina.DisciplinaPresenter;
import com.navan.app.alunouesb.view.adapter.Adapter2;
import com.navan.app.alunouesb.view.dialog.DialogExcluir;
import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.view.adapter.AdapterDisciplina;
import com.navan.app.alunouesb.data.model.Disciplina;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DisciplinasActivity extends AppCompatActivity implements DialogExcluir.OnExcluir, IDisciplinaContract.View, Adapter2.Actions {

    private RecyclerView recyclerView;
    private AdapterDisciplina adapter;

    private ProgressBar progressBar;

    private ArrayList<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();
    public List<Disciplina> listaExclusao = new ArrayList<>();

    private IDisciplinaContract.Presenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        setTitle("Minhas Disciplinas");

        progressBar = findViewById(R.id.progressbar);

        iPresenter = new DisciplinaPresenter(this);

        configAdapter();
    }

    private void configAdapter() {
        recyclerView = findViewById(R.id.recycler_view_disciplinas);

        adapter = new AdapterDisciplina(listaDisciplinas, getApplicationContext(), this);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, recyclerView.VERTICAL, false);
        DividerItemDecoration divider = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
    }

    public void chamarTelaAdicionarDisciplina(View view){
        adapter.clearItensSelected();
        invalidateOptionsMenu();

        Intent intent = new Intent(this, AdicionarDisciplinaActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lembretes, menu);
        MenuItem excluir = menu.findItem(R.id.action_excluir);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (adapter.isItensSelectedEmpty()) {
            excluir.setVisible(false);
        } else {
            excluir.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_excluir) {

            DialogExcluir dialog = new DialogExcluir();
            dialog.setOnExcluir(this);
            dialog.show(getSupportFragmentManager(), "Deseja excluir a disciplina?");

        }else if(listaExclusao.isEmpty()){// volta para a tela anterior
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.itensList.clear();
        iPresenter.list();
    }

    @Override
    public void onBackPressed() {
        if (adapter.itensSelected.isEmpty()) {
            finish();
        } else {
            adapter.clearItensSelected();
            invalidateOptionsMenu();
        }
    }


    @Override
    public void onExcluir() {
        for (Disciplina disciplina : adapter.itensSelected) {
            iPresenter.remove(disciplina);
        }

    }

    @Override
    public void showProgress() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListSuccess(@NotNull List<? extends Disciplina> list) {
        adapter.itensList.addAll(list);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onCreatedSuccess(@NotNull Disciplina item) {
        adapter.add(item);
    }

    @Override
    public void onUpdateSuccess(@NotNull Disciplina item) {
        adapter.update(item);
    }

    @Override
    public void onRemovedSuccess(@NotNull Disciplina item) {
        Toast.makeText(this, "Disciplina removida", Toast.LENGTH_SHORT).show();
        adapter.itensSelected.remove(item);
        adapter.remove(item);
        invalidateOptionsMenu();
    }

    @Override
    public void onFailure(@NotNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickItem(View view) {
        if(adapter.itensSelected.isEmpty() && adapter.objectSelected != null){
            Intent intent = new Intent(getApplicationContext(), DisciplinaActivity.class);
            intent.putExtra("disciplina", adapter.objectSelected);
            startActivity(intent);
        }
    }

    @Override
    public void onLongClickItem(View view) {
        invalidateOptionsMenu();

    }
}
