package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cassio.alunouesb.dialog.DialogExcluir;
import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.adapter.AdapterDisciplina;
import com.example.cassio.alunouesb.database.dao.DisciplinaDAO;
import com.example.cassio.alunouesb.model.Disciplina;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisciplinasActivity extends AppCompatActivity implements AdapterDisciplina.OnClick, AdapterDisciplina.OnLongClick, DialogExcluir.OnExcluir {

    private Intent intent;
    private RecyclerView recyclerView;
    private AdapterDisciplina adapter;

    public List<Disciplina> listaExclusao = new ArrayList<>();
    private List<View> listaViewSelecionadas = new ArrayList<>();

    private int REQUEST_NOVA_DISCIPLINA = 1;
    private int REQUEST_ABRIR_DISCIPLINA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        setTitle("Minhas Disciplinas");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_disciplinas);

        if (savedInstanceState != null) {
            listaExclusao = (List<Disciplina>) savedInstanceState.getSerializable("lista");
        }

        adapter = new AdapterDisciplina(DisciplinaDAO.getInstance(this).buscarTodos(PrincipalActivity.USUARIO.getIdSemestre()), this, this, this);

        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("lista",(Serializable) listaExclusao);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (listaExclusao.isEmpty()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getMenuInflater().inflate(R.menu.menu_lembretes, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_excluir) {

            DialogExcluir dialog = new DialogExcluir();
            dialog.setOnExcluir(this);
            dialog.show(getSupportFragmentManager(), "Deseja excluir a disciplina?");

        } else {

            listaExclusao.clear();
            for (View view : listaViewSelecionadas) {
                view.setBackgroundResource(R.drawable.shape_cinza);
            }
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (listaExclusao.isEmpty()) {
            intent = new Intent(this, DisciplinaActivity.class);
            intent.putExtra("disciplina", (Disciplina) adapter.getItem((int) view.getTag()));
            startActivityForResult(intent, REQUEST_ABRIR_DISCIPLINA);

        } else {
            adicionarParaExclusao(view);
        }

    }

    @Override
    public void onLongClick(View view) {
        adicionarParaExclusao(view);
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Disciplina disciplina;

        //Caso adicionou um novo Item
        if (requestCode == REQUEST_NOVA_DISCIPLINA && data != null) {
            disciplina = (Disciplina) data.getSerializableExtra("disciplina");
            adapter.addItem(disciplina);
        }

        //Caso editou um Item
        if (requestCode == REQUEST_ABRIR_DISCIPLINA && resultCode == 1) {
            disciplina = (Disciplina) data.getSerializableExtra("disciplina");
            adapter.update(disciplina);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void chamarTelaAdicionarDisciplina(View view) {
        listaExclusao.clear();
        listaViewSelecionadas.clear();
        invalidateOptionsMenu();
        adapter.notifyDataSetChanged();

        intent = new Intent(this, AdicionarDisciplinaActivity.class);
        startActivityForResult(intent, REQUEST_NOVA_DISCIPLINA);
    }

    private void adicionarParaExclusao(View view) {
        Disciplina disciplina = (Disciplina) adapter.getItem((int) view.getTag());

        if (this.listaExclusao.contains(disciplina)) {
            listaViewSelecionadas.remove(view);
            listaExclusao.remove(disciplina);
            view.setBackgroundResource(R.drawable.shape_cinza);
        } else {
            listaViewSelecionadas.add(view);
            listaExclusao.add(disciplina);
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        invalidateOptionsMenu();
    }

    @Override
    public void onExcluir() {
        for (Disciplina disciplina : listaExclusao) {
            adapter.removeItem(disciplina);
            DisciplinaDAO.getInstance(this).deletarRegistros(disciplina.getId());
        }

        listaExclusao.clear();
        invalidateOptionsMenu();
    }
}
