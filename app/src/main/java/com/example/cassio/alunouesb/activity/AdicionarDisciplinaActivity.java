package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.DisciplinaDAO;
import com.example.cassio.alunouesb.database.dao.ProfessorDAO;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Professor;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdicionarDisciplinaActivity extends AppCompatActivity {

    private Usuario usuario = PrincipalActivity.usuario;
    private EditText nome;
    private EditText abreviatura;
    private EditText nomeProfessor;
    private EditText emailProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_disciplina);

        nome = findViewById(R.id.text_disciplina_nome);
        abreviatura = findViewById(R.id.text_disciplina_abreviatura);
        nomeProfessor = findViewById(R.id.text_disciplina_professor);
        emailProfessor = findViewById(R.id.text_disciplina_email_professor);

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
                this.salvarDisciplina();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvarDisciplina() {

        String nome = this.nome.getText().toString();
        String abreviatura = this.abreviatura.getText().toString();
        String nomeProfessor = this.nomeProfessor.getText().toString();
        String emailProfessor = this.emailProfessor.getText().toString();

        if (!nome.isEmpty() && !abreviatura.isEmpty() && !nomeProfessor.isEmpty()) {

            Professor professorTemp = new Professor(nomeProfessor, emailProfessor);
            Disciplina disciplina = new Disciplina(nome, abreviatura, professorTemp);

            Semestre semestre = usuario.getSemestreList().get(usuario.getIdSemestre());
            semestre.adicionarDisciplina(disciplina);

            FirebaseFirestore.getInstance().collection("/users").document(usuario.getUid()).set(usuario)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdicionarDisciplinaActivity.this, "Falha ao adicionar", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                        }
                    });
        }
    }
}
