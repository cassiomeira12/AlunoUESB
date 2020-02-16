package com.navan.app.alunouesb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.navan.app.alunouesb.view.adapter.AdapterLembretes;
import com.navan.app.alunouesb.data.db.References;
import com.navan.app.alunouesb.view.adapter.AdapterLembretes2;
import com.navan.app.alunouesb.view.dialog.DialogExcluir;
import com.navan.app.alunouesb.data.model.Lembrete;
import com.navan.app.alunouesb.data.model.Semestre;
import com.navan.app.alunouesb.data.model.Usuario;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LembretesActivity extends AppCompatActivity implements DialogExcluir.OnExcluir, ILembreteContract.View, Adapter2.Actions {

    private Usuario usuario = PrincipalActivity.usuario;
    private Semestre semestre =  PrincipalActivity.semestre;


    public List<Lembrete> listaExclusao = new ArrayList<>();
    private List<View> listaViewSelecionadas = new ArrayList<>();

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

    private void configAdapter() {
        recyclerLembretes = findViewById(R.id.recycler_view_lembretes);

        adapterLembrete = new AdapterLembretes2(listItens, getApplicationContext(), this);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        DividerItemDecoration divider = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        recyclerLembretes.addItemDecoration(divider);
        recyclerLembretes.setLayoutManager(layout);
        recyclerLembretes.setAdapter(adapterLembrete);
    }

    public void onClick(View view) {
        //        listaExclusao.clear();
//        listaViewSelecionadas.clear();
//        invalidateOptionsMenu();
//        adapterLembrete.notifyDataSetChanged();
//
        Intent telaAdicionarLembrete = new Intent(this, AdicionarLembreteActivity.class);
        startActivity(telaAdicionarLembrete);
    }

    @Override
    public void onClickItem(View view) {

    }

    @Override
    public void onLongClickItem(View view) {

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
        adapterLembrete.remove(item);
    }

    @Override
    public void onFailure(@NotNull String message) {

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lembretes, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MenuItem excluir = menu.findItem(R.id.action_excluir);

        if (listaExclusao.isEmpty()) {
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
            dialog.show(getSupportFragmentManager(), "Deseja excluir o lembrete?");

        }else if(listaExclusao.isEmpty()){
            finish();
        }else {

            listaExclusao.clear();
            for (View view : listaViewSelecionadas) {
                view.setBackgroundResource(R.drawable.shape_cinza);
            }
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onClick(View view) {
//
//        if (listaExclusao.isEmpty()) {
//            Intent telaLembrete = new Intent(this, LembreteActivity.class);
//
//            telaLembrete.putExtra("idLembrete", (Integer) view.getTag());
//            startActivity(telaLembrete);
//
//        } else {
//            adicionarParaExclusao(view);
//        }
//    }
//
//    @Override
//    public void onLongClick(View view) {
//        adicionarParaExclusao(view);
//        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//    }

//    private void adicionarParaExclusao(View view) {
//        Lembrete lembrete = (Lembrete) adapterLembrete.getItem((int) view.getTag());
//
//        if (this.listaExclusao.contains(lembrete)) {
//            listaViewSelecionadas.remove(view);
//            listaExclusao.remove(lembrete);
//            view.setBackgroundResource(R.drawable.shape_cinza);
//        } else {
//            listaViewSelecionadas.add(view);
//            listaExclusao.add(lembrete);
//            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        }
//
//        invalidateOptionsMenu();
//    }

    @Override
    public void onExcluir() {
        for (Lembrete lembrete : listaExclusao) {
            PrincipalActivity.semestre.getLembreteList().remove(lembrete);
            adapterLembrete.notifyDataSetChanged();
            salvarBancoDeDados();
        }

        listaExclusao.clear();
        invalidateOptionsMenu();
    }

    private void salvarBancoDeDados() {
        String semestreSelecionado =  PrincipalActivity.semestre.getSemestre();
        References.db.collection("/semestres").document(semestreSelecionado).set(PrincipalActivity.semestre);
    }

//    private void carregarDados() {
//        if(PrincipalActivity.semestre != null){
//            ArrayList<Lembrete> listLembretes = PrincipalActivity.semestre.getLembreteList();
//            adapterLembrete = new AdapterLembretes(listLembretes, this, this, this);
//
//            recyclerLembretes.setAdapter(adapterLembrete);
//
//        }else{
//            // Algum erro de conexao que fez isso acontecer
//            Toast.makeText(this, "Falha na conexão com o servidor", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

    @Override
    protected void onRestart() {
        //carregarDados();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if(!listaExclusao.isEmpty()){
            listaExclusao.clear();
            for (View view : listaViewSelecionadas) {
                view.setBackgroundResource(R.drawable.shape_cinza);
            }
            invalidateOptionsMenu();
        }else{
            finish();
        }
    }

}
