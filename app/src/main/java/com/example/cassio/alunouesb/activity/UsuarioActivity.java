package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.SemestreDAO;
import com.example.cassio.alunouesb.database.dao.UsuarioDAO;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class UsuarioActivity extends AppCompatActivity {

    private EditText usuarioNome;
    private MaterialBetterSpinner spinnerCurso;
    private EditText matricula;
    private Spinner spinnerSemestre;
    private EditText novoSemestre;

    public static List<Semestre> semestreList;
    private Usuario usuario = PrincipalActivity.usuario;

    private boolean permitirEdicao = true;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        setTitle("Meus Dados");

        usuarioNome = findViewById(R.id.text_usuario_nome);
        spinnerCurso = findViewById(R.id.spinner_usuario_curso);
        matricula = findViewById(R.id.text_matricula);
        novoSemestre = findViewById(R.id.text_novo_semestre);


        String[] cursos = {"Administração", "Agronomia", "Biologia", "Cinema", "Ciências Socias", "Ciência da Computação",
                            "Contabilidade", "Direito", "Economia", "Educação Física", "Engenharia Florestal", "Filosofia",
                            "Física", "Geografia", "História", "Matemática", "Medicina", "Pedagogia", "Psicologia"};

        ArrayAdapter<String> adapterCurso = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cursos);
        spinnerCurso.setAdapter(adapterCurso);



        spinnerSemestre = findViewById(R.id.spinner_semestre);
        spinnerSemestre.setEnabled(false);

        semestreList = usuario.getSemestreList();
        ArrayAdapter<Semestre> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, semestreList);
        spinnerSemestre.setAdapter(arrayAdapter);
//
        usuarioNome.setText(usuario.getNome());
        spinnerCurso.setText(usuario.getCurso());

        if(usuario.getMatricula() == 0){
            matricula.setHint("Matricula");
        }else{
            matricula.setText(String.valueOf(usuario.getMatricula()));
        }


        spinnerSemestre.setSelection(arrayAdapter.getPosition(PrincipalActivity.semestre));
        spinnerSemestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // ao clicar sobre um novo semestre, deverá carregar os dados do tal semestre selecionado
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usuario.setIdSemestre(i);

                //salvar mudança no banco de dados
                FirebaseFirestore.getInstance().collection("/users").document(usuario.getUid()).set(usuario);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        habilitarEdicao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lembrete, menu);
        this.menu = menu;
        menu.findItem(R.id.action_salvar).setVisible(permitirEdicao);
        menu.findItem(R.id.action_editar).setVisible(!permitirEdicao);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_salvar:
                salvarModificacoes();
                menu.findItem(R.id.action_salvar).setVisible(permitirEdicao);
                menu.findItem(R.id.action_editar).setVisible(!permitirEdicao);
                break;

            case R.id.action_editar:
                habilitarEdicao();
                invalidateOptionsMenu();
                break;

            case R.id.action_cancelar:
                finish();
        }


        return super.onOptionsItemSelected(item);
    }



    public void adicionarSemestre(View view) {

        String semestre = novoSemestre.getText().toString();

        if(!semestre.isEmpty()){
            Semestre semestreTemp = new Semestre(semestre);

            ArrayList<Semestre> semestres = usuario.getSemestreList();
            for (Semestre doc : semestres) {
                if(doc.getSemestre().equals(semestreTemp.getSemestre())){
                    Toast.makeText(this, "Semestre existente", Toast.LENGTH_SHORT).show();
                    novoSemestre.setText("");
                    return;
                }
            }

            usuario.addSemestre(semestreTemp);
        }

        //salvar no banco de dados
        FirebaseFirestore.getInstance().collection("/users").document(usuario.getUid()).set(usuario)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UsuarioActivity.this, "Falha ao salvar", Toast.LENGTH_SHORT).show();
                    }
                });

        novoSemestre.setText("");
    }
    public void salvarModificacoes() {

        String nome = usuarioNome.getText().toString();
        String curso = spinnerCurso.getText().toString();

        int matricula = 0;
        if(!this.matricula.getText().toString().isEmpty()){ // campo não for vazio
            matricula = Integer.parseInt(this.matricula.getText().toString());
        }

        //salva o ID do semestre selecionado
        Semestre semestre = ((Semestre) spinnerSemestre.getSelectedItem()); // novo semestre selecionado
        ArrayList<Semestre> semestres = usuario.getSemestreList(); // lista de semestres do usuario

        for(int i = 0; i < semestres.size(); i++){
            if(semestres.get(i).getSemestre() == semestre.getSemestre()){// verifica qual o ID do novo semestre selecionado na lista dos semestres do usuario
                usuario.setIdSemestre(i); // seta o ID so semestre selecionado
                break;
            }
        }

        usuario.setNome(nome);
        usuario.setCurso(curso);

        if(matricula != 0) {
            usuario.setMatricula(matricula);
        }

        //salvar no banco de dados
        FirebaseFirestore.getInstance().collection("/users").document(usuario.getUid()).set(usuario)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UsuarioActivity.this, "Falha ao salvar", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent();
                        intent.putExtra("usuario", usuario);
                        setResult(1, intent);
                    }
                });

        habilitarEdicao();

    }


    private void habilitarEdicao() {

        this.permitirEdicao = (!this.permitirEdicao);

        usuarioNome.setEnabled(permitirEdicao);
        matricula.setEnabled(permitirEdicao);
        spinnerCurso.setEnabled(permitirEdicao);
        spinnerSemestre.setEnabled(permitirEdicao);

    }
}
