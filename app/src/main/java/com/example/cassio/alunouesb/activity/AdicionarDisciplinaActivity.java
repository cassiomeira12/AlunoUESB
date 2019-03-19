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
import com.example.cassio.alunouesb.database.dao.DisciplinaDAO;
import com.example.cassio.alunouesb.database.dao.ProfessorDAO;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Professor;

public class AdicionarDisciplinaActivity extends AppCompatActivity {

    private EditText nome;
    private EditText abreviatura;
    private EditText nomeProfessor;
    private EditText emailProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_disciplina);

        nome = (EditText) findViewById(R.id.text_disciplina_nome);
        abreviatura = (EditText) findViewById(R.id.text_disciplina_abreviatura);
        nomeProfessor = (EditText) findViewById(R.id.text_disciplina_professor);
        emailProfessor = (EditText) findViewById(R.id.text_disciplina_email_professor);

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

            Professor professorTemp = new Professor(null, nomeProfessor);
            professorTemp.setEmail(emailProfessor);

            long idProfessor = ProfessorDAO.getInstance(this).inserirDados(professorTemp);

            long idSemestre = PrincipalActivity.SEMESTRE.getId();

            Disciplina disciplina = new Disciplina(null, nome, abreviatura, idProfessor, idSemestre);

            long id = DisciplinaDAO.getInstance(this).inserirDados(disciplina);

            if (id < 0) {
                Toast.makeText(getApplicationContext(), "Erro, ao inserir", Toast.LENGTH_LONG).show();
            } else {
                disciplina.setId(id);
                Intent intent = new Intent();
                intent.putExtra("disciplina", disciplina);
                setResult(1, intent);//Passando resultado 1, a disciplina foi criada
                finish();
            }
        } else {
            Toast toast = Toast.makeText(this, "Dados insuficentes", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
