package com.navan.app.alunouesb.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.app.presenter.login.LembretePresenter;
import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.contract.ILembreteContract;
import com.navan.app.alunouesb.view.adapter.Adapter2;
import com.navan.app.alunouesb.view.adapter.AdapterLembretes2;
import com.navan.app.alunouesb.view.dialog.DialogExcluir;
import com.navan.app.alunouesb.data.model.Lembrete;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LembretesActivity extends AppCompatActivity implements DialogExcluir.OnExcluir, ILembreteContract.View, Adapter2.Actions {

    private ProgressBar progressBar;

    private AdapterLembretes2 adapterLembrete;
    private RecyclerView recyclerLembretes;
    private ILembreteContract.Presenter iPresenter;
    private List<Lembrete> listItens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembretes);
        setTitle("Meus Lembretes");

        progressBar = findViewById(R.id.progressBar);

        iPresenter = new LembretePresenter(this);
        configAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterLembrete.itensList.clear();
        iPresenter.list();
    }

    @Override
    public void onBackPressed() {
        if (adapterLembrete.itensSelected.isEmpty()) {
            finish();
        } else {
            adapterLembrete.clearItensSelected();
            invalidateOptionsMenu();
        }
    }

    private void configAdapter() {
        recyclerLembretes = findViewById(R.id.recycler_view_lembretes);

        adapterLembrete = new AdapterLembretes2(listItens, getApplicationContext(), this);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        DividerItemDecoration divider = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        recyclerLembretes.addItemDecoration(divider);
        recyclerLembretes.setLayoutManager(layout);
        recyclerLembretes.setAdapter(adapterLembrete);
    }

    public void onClick(View view) {
        adapterLembrete.clearItensSelected();
        invalidateOptionsMenu();
        Intent telaAdicionarLembrete = new Intent(this, AdicionarLembreteActivity.class);
        startActivity(telaAdicionarLembrete);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lembretes, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MenuItem excluir = menu.findItem(R.id.action_excluir);
        if (adapterLembrete.isItensSelectedEmpty()) {
            excluir.setVisible(false);
        } else {
            excluir.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_excluir:
                DialogExcluir dialog = new DialogExcluir();
                dialog.setOnExcluir(this);
                dialog.show(getSupportFragmentManager(), "Deseja excluir o lembrete?");
                break;
            default:
                finish(); // volta para tela anterior
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(View view) {
        if (adapterLembrete.itensSelected.isEmpty() && adapterLembrete.objectSelected != null) {
            Intent intent = new Intent(getApplicationContext(), LembreteActivity.class);
            intent.putExtra("lembrete", adapterLembrete.objectSelected);
            startActivity(intent);
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onExcluir() {
        for (Lembrete lembrete : adapterLembrete.itensSelected) {
            iPresenter.remove(lembrete);
        }
    }

    @Override
    public void onLongClickItem(View view) {
        invalidateOptionsMenu();
    }

    @Override
    public void showProgress() {
        recyclerLembretes.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        recyclerLembretes.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListSuccess(@NotNull List<? extends Lembrete> list) {
        adapterLembrete.itensList.addAll(list);
        adapterLembrete.notifyDataSetChanged();
    }

    @Override
    public void onCreatedSuccess(@NotNull Lembrete item) {
        adapterLembrete.add(item);
    }

    @Override
    public void onUpdateSuccess(@NotNull Lembrete item) {
        adapterLembrete.update(item);
    }

    @Override
    public void onRemovedSuccess(@NotNull Lembrete item) {
        Toast.makeText(this, "Lembrete removido!", Toast.LENGTH_LONG).show();
        adapterLembrete.itensSelected.remove(item);
        adapterLembrete.remove(item);
        invalidateOptionsMenu();
    }

    @Override
    public void onFailure(@NotNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
