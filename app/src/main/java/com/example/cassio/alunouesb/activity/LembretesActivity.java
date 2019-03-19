package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.adapter.AdapterLembretes;
import com.example.cassio.alunouesb.database.dao.LembreteDAO;
import com.example.cassio.alunouesb.dialog.DialogExcluir;
import com.example.cassio.alunouesb.model.Lembrete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LembretesActivity extends AppCompatActivity implements AdapterLembretes.OnClick, AdapterLembretes.OnLongClick, DialogExcluir.OnExcluir {

    private Intent intent;
    private RecyclerView recyclerView;
    private AdapterLembretes adapter;

    public List<Lembrete> listaExclusao = new ArrayList<>();
    private List<View> listaViewSelecionadas = new ArrayList<>();

    private int REQUEST_NOVO_LEMBRETE = 1;
    private int REQUEST_ABRIR_LEMBRETE = 2;
    //private int REQUEST_BUSCAR_IMAGEM = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembretes);
        setTitle("Meus Lembretes");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_lembretes);

        if (savedInstanceState != null) {
            listaExclusao = (List<Lembrete>) savedInstanceState.getSerializable("lista");
        }

        adapter = new AdapterLembretes(LembreteDAO.getInstance(this).buscarTodos(PrincipalActivity.USUARIO.getIdSemestre()), this, this, this);

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
            dialog.show(getSupportFragmentManager(), "Deseja excluir o lembrete?");

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
            intent = new Intent(this, LembreteActivity.class);
            intent.putExtra("lembrete", (Lembrete) adapter.getItem((int) view.getTag()));
            startActivityForResult(intent, REQUEST_ABRIR_LEMBRETE);

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

        Lembrete lembrete;

        //Caso adicionou um novo Item
        if (requestCode == REQUEST_NOVO_LEMBRETE && data != null) {
            lembrete = (Lembrete) data.getSerializableExtra("lembrete");
            adapter.addItem(lembrete);
        }

        //Caso editou um Item
        if (requestCode == REQUEST_ABRIR_LEMBRETE && resultCode == 1) {
            lembrete = (Lembrete) data.getSerializableExtra("lembrete");
            adapter.update(lembrete);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void chamarTelaAdicionarLembretes(View view) {
        listaExclusao.clear();
        listaViewSelecionadas.clear();
        invalidateOptionsMenu();
        adapter.notifyDataSetChanged();

        intent = new Intent(this, AdicionarLembreteActivity.class);
        startActivityForResult(intent, REQUEST_NOVO_LEMBRETE);
    }

    private void adicionarParaExclusao(View view) {
        Lembrete lembrete = (Lembrete) adapter.getItem((int) view.getTag());

        if (this.listaExclusao.contains(lembrete)) {
            listaViewSelecionadas.remove(view);
            listaExclusao.remove(lembrete);
            view.setBackgroundResource(R.drawable.shape_cinza);
        } else {
            listaViewSelecionadas.add(view);
            listaExclusao.add(lembrete);
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        invalidateOptionsMenu();
    }

    @Override
    public void onExcluir() {
        for (Lembrete lembrete : listaExclusao) {
            adapter.removeItem(lembrete);
            LembreteDAO.getInstance(this).deletaRegistro(lembrete.getId());
        }

        listaExclusao.clear();
        invalidateOptionsMenu();
    }
}
