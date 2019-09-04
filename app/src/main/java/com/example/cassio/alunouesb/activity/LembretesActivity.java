package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.adapter.AdapterLembretes;
import com.example.cassio.alunouesb.database.dao.LembreteDAO;
import com.example.cassio.alunouesb.dialog.DialogExcluir;
import com.example.cassio.alunouesb.model.Lembrete;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class LembretesActivity extends AppCompatActivity implements AdapterLembretes.OnClick, AdapterLembretes.OnLongClick, DialogExcluir.OnExcluir{

    private Usuario usuario = PrincipalActivity.usuario;
    private RecyclerView recyclerView;
    private AdapterLembretes adapter;

    public List<Lembrete> listaExclusao = new ArrayList<>();
    private List<View> listaViewSelecionadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembretes);
        setTitle("Meus Lembretes");

        // pagina possui um Swipe para atualizar a lista
        recyclerView = findViewById(R.id.recycler_view_lembretes);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layout);

        carregarDados(); // carrega a lista de lembretes
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

    @Override
    public void onClick(View view) {

        if (listaExclusao.isEmpty()) {
            Intent telaLembrete = new Intent(this, LembreteActivity.class);

            telaLembrete.putExtra("idLembrete", (Integer) view.getTag());
            startActivity(telaLembrete);

        } else {
            adicionarParaExclusao(view);
        }
    }

    @Override
    public void onLongClick(View view) {
        adicionarParaExclusao(view);
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    public void chamarTelaAdicionarLembretes(View view) {
        listaExclusao.clear();
        listaViewSelecionadas.clear();
        invalidateOptionsMenu();
        adapter.notifyDataSetChanged();

        Intent telaAdicionarLembrete = new Intent(this, AdicionarLembreteActivity.class);
        startActivity(telaAdicionarLembrete);
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
            adapter.notifyDataSetChanged();
            salvarBancoDeDados();
        }

        listaExclusao.clear();
        invalidateOptionsMenu();
    }

    private void salvarBancoDeDados() {
        FirebaseFirestore.getInstance().collection("users").document(usuario.getUid()).set(usuario);
    }

    private void carregarDados() {
        ArrayList<Lembrete> listLembretes = (ArrayList<Lembrete>) usuario.getSemestreList().get(usuario.getIdSemestre()).getLembreteList();

        adapter = new AdapterLembretes(listLembretes, this, this, this);

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onRestart() {
        carregarDados();
        super.onRestart();
    }
}
