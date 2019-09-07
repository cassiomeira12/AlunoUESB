package com.example.cassio.alunouesb.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.model.Lembrete;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class LembreteActivity extends AppCompatActivity{

    private Usuario usuario =  PrincipalActivity.usuario;

    private Lembrete lembrete;

    private EditText titulo;
    private EditText mensagem;

    private boolean permitirEdicao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrete);
        setTitle("Lembrete");

        titulo = findViewById(R.id.text_titulo_lembrete);
        mensagem = findViewById(R.id.text_mensagem_lembrete);

        int idLembrete = (int) getIntent().getSerializableExtra("idLembrete");
        lembrete = usuario.getSemestreList().get(usuario.getIdSemestre()).getLembreteList().get(idLembrete);

        titulo.setText(lembrete.getTitulo());
        mensagem.setText(lembrete.getMensagem());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lembrete, menu);
        menu.findItem(R.id.action_salvar).setVisible(permitirEdicao);
        menu.findItem(R.id.action_editar).setVisible(!permitirEdicao);
        menu.findItem(R.id.excluir_lembrete).setVisible(!permitirEdicao);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.excluir_lembrete:
                deletarLembrete();
                finish();
                break;

            case R.id.action_salvar:
                this.salvar();
                break;

            case R.id.action_editar:
                habilitarEdicao();
                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deletarLembrete() {
        usuario.getSemestreList().get(usuario.getIdSemestre()).getLembreteList().remove(lembrete);
        Toast.makeText(this, "Lembrete excluído", Toast.LENGTH_SHORT).show();
        salvar();
    }

    private void salvar() {
        lembrete.setTitulo(titulo.getText().toString());
        lembrete.setMensagem(mensagem.getText().toString());

        FirebaseFirestore.getInstance().collection("users").document(usuario.getUid()).set(usuario)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LembreteActivity.this, "Falha ao adicionar lembrete", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
    }

    private void habilitarEdicao() {
        this.permitirEdicao = (!permitirEdicao);
        titulo.setEnabled(permitirEdicao);
        mensagem.setEnabled(permitirEdicao);
    }
}
