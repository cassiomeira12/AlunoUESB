package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.LembreteDAO;
import com.example.cassio.alunouesb.model.Lembrete;

public class AdicionarLembreteActivity extends AppCompatActivity {

    private EditText titulo;
    private EditText mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lembrete);
        setTitle("Adicionar Lembrete");
        titulo = (EditText) findViewById(R.id.text_titulo_adicionar_lembrete);
        mensagem = (EditText) findViewById(R.id.text_mensagem_adicionar_lembrete);

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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvarLembrete() {

        String titulo = this.titulo.getText().toString();
        String mensagem = this.mensagem.getText().toString();

        if (!titulo.isEmpty()) {

            long idSemestre = PrincipalActivity.SEMESTRE.getId();
            Lembrete lembrete = new Lembrete(null, titulo, mensagem, System.currentTimeMillis(), idSemestre);

            long id = LembreteDAO.getInstance(this).inserirDados(lembrete);

            if (id < 0) {
                Toast.makeText(getApplicationContext(), "Erro, ao inserir", Toast.LENGTH_LONG).show();
            } else {
                lembrete.setId(id);
                Intent intent = new Intent();
                intent.putExtra("lembrete", lembrete);
                setResult(1, intent);
                finish();
            }

        } else {
            Toast toast = Toast.makeText(this, "Dados insuficentes", Toast.LENGTH_LONG);
            toast.show();
        }



    }


}
