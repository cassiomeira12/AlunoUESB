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
import android.widget.Toast;

import com.navan.app.alunouesb.data.db.References;
import com.navan.app.alunouesb.view.dialog.DialogExcluir;
import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.view.adapter.AdapterDisciplina;
import com.navan.app.alunouesb.data.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class DisciplinasActivity extends AppCompatActivity implements AdapterDisciplina.OnClick, AdapterDisciplina.OnLongClick, DialogExcluir.OnExcluir{

    private RecyclerView recyclerView;
    private AdapterDisciplina adapter;

    private ArrayList<Disciplina> listaDisciplinas = new ArrayList<Disciplina>();
    public List<Disciplina> listaExclusao = new ArrayList<>();
    private List<View> listaViewSelecionadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        setTitle("Minhas Disciplinas");

        recyclerView = findViewById(R.id.recycler_view_disciplinas);

        DividerItemDecoration divider = new DividerItemDecoration(this, LinearLayout.VERTICAL);


        recyclerView.addItemDecoration(divider);

        carregarDadosTela();
    }

    private void carregarDadosTela() {

        if(PrincipalActivity.semestre != null){ // se n fizer isso pode dar crash quando o usuario clicar rapido na tela Disciplinas
            listaDisciplinas = (ArrayList<Disciplina>) PrincipalActivity.semestre.getDisciplinaList();
        }else{
            // algum erro com a conexao pode fazer isso
            Toast.makeText(this, "Falha na conexão com o servidor", Toast.LENGTH_SHORT).show();
        }

        adapter = new AdapterDisciplina(listaDisciplinas, this, this, this);

        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lembretes, menu);
        MenuItem excluir = menu.findItem(R.id.action_excluir);

        if (listaExclusao.isEmpty()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            excluir.setVisible(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            Intent telaDisciplina = new Intent(this, DisciplinaActivity.class);
            telaDisciplina.putExtra("idDisciplina", (Integer) view.getTag());
            startActivity(telaDisciplina);

        } else {
            adicionarParaExclusao(view);
        }

    }

    @Override
    public void onLongClick(View view) {
        adicionarParaExclusao(view);
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    public void chamarTelaAdicionarDisciplina(View view) {
        listaExclusao.clear();
        listaViewSelecionadas.clear();
        invalidateOptionsMenu();
        adapter.notifyDataSetChanged();

        Intent telaAdicionarDisciplina = new Intent(this, AdicionarDisciplinaActivity.class);
        startActivity(telaAdicionarDisciplina);
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
            PrincipalActivity.semestre.getDisciplinaList().remove(disciplina);
        }

        listaExclusao.clear();

        if(References.uid != null){
            String semestreSelecionado = PrincipalActivity.semestre.getSemestre();
            References.db.collection("/semestres").document(semestreSelecionado).set(PrincipalActivity.semestre);// apagar disciplinas selecionadas do banco de dados
            invalidateOptionsMenu();
        }else{
            Toast.makeText(this, "Faça login para prosseguir.", Toast.LENGTH_SHORT).show();
            //start activity login
        }

    }

    @Override
    protected void onRestart() {
        carregarDadosTela();
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
