package com.example.cassio.alunouesb.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.db.References;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Professor;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdicionarDisciplinaActivity extends AppCompatActivity {

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

            PrincipalActivity.semestre.adicionarDisciplina(disciplina);

            // criar Thread para adicionar ao Firebase
            //Motivo: Internet pode estar lenta e demorar para inserir no firebase, demorando para sair da tela tb
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
}
