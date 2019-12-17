package com.navan.app.alunouesb.view.activity;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.data.db.References;
import com.navan.app.alunouesb.data.model.Lembrete;
import com.navan.app.alunouesb.data.model.Usuario;

public class AdicionarLembreteActivity extends AppCompatActivity {
    private Usuario usuario = PrincipalActivity.usuario;
    private EditText titulo;
    private EditText mensagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lembrete);
        setTitle("Adicionar Lembrete");
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
        int id = item.getItemId();

        switch (id) {
            case R.id.action_cancelar:
                finish();
                break;

            case R.id.action_salvar:
                this.salvarLembrete();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvarLembrete() {

        String titulo = this.titulo.getText().toString();
        String mensagem = this.mensagem.getText().toString();

        Lembrete lembrete = new Lembrete(titulo, mensagem);

        PrincipalActivity.semestre.getLembreteList().add(0, lembrete);

        Handler handler = new Handler();
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                String semestreSelecionado = PrincipalActivity.semestre.getSemestre();
                References.db.collection("/semestres").document(semestreSelecionado).set(PrincipalActivity.semestre);
            }
        };
        handler.post(thread);
        finish();
    }
}
