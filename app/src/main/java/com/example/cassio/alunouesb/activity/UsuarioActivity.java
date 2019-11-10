package com.example.cassio.alunouesb.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.db.References;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class UsuarioActivity extends AppCompatActivity {

    private EditText usuarioNome;
    private MaterialBetterSpinner spinnerCurso;
    private EditText matricula;
    private Spinner spinnerSemestre;
    private EditText novoSemestre;
    private ArrayAdapter<String> arrayAdapter;


    private Usuario usuario = PrincipalActivity.usuario;
    private boolean permitirEdicao = true;
    private Menu menu;

    private ProgressDialog mDialog;

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


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, usuario.getSemestreList());
        spinnerSemestre.setAdapter(arrayAdapter);
//
        usuarioNome.setText(usuario.getNome());
        spinnerCurso.setText(usuario.getCurso());

        if(usuario.getMatricula() == 0){
            matricula.setHint("Matricula");
        }else{
            matricula.setText(String.valueOf(usuario.getMatricula()));
        }


        spinnerSemestre.setSelection(arrayAdapter.getPosition(PrincipalActivity.semestre.getSemestre()));

        habilitarEdicao();

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Adicionando semestre...");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lembrete, menu);
        this.menu = menu;
        menu.findItem(R.id.action_salvar).setVisible(permitirEdicao);
        menu.findItem(R.id.action_editar).setVisible(!permitirEdicao);
        menu.findItem(R.id.excluir_lembrete).setTitle("Cancelar");

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

            case R.id.excluir_lembrete:
                finish();
        }


        return super.onOptionsItemSelected(item);
    }

    public void adicionarSemestre(View view) {
        mDialog.show();

        final String semestre = novoSemestre.getText().toString();

        if(!semestre.isEmpty()){
            Semestre semestreTemp = new Semestre(semestre);

            ArrayList<String> semestres = usuario.getSemestreList();
            for (String doc : semestres) {
                if(doc.equals(semestreTemp.getSemestre())){
                    Toast.makeText(this, "Semestre existente", Toast.LENGTH_SHORT).show();
                    novoSemestre.setText("");

                    mDialog.dismiss();

                    return;
                }
            }

            References.db.collection("/semestres").document(semestre).set(semestreTemp)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                        }
                    });

            usuario.getSemestreList().add(semestre);
            spinnerSemestre.setSelection(arrayAdapter.getPosition(semestre));

        }

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
        String semestreSelecionado = (String) spinnerSemestre.getSelectedItem(); // novo semestre selecionado
        ArrayList<String> semestres = usuario.getSemestreList(); // lista de semestres do usuario

        for(int i = 0; i < semestres.size(); i++){
            if(semestres.get(i).equals(semestreSelecionado)){// verifica qual o ID do novo semestre selecionado na lista dos semestres do usuario
                usuario.setIdSemestre(i); // seta o ID so semestre selecionado
                break;
            }
        }

        usuario.setNome(nome);
        usuario.setCurso(curso);

        if(matricula != 0) {
            usuario.setMatricula(matricula);
        }



        if(References.uid != null){
            PrincipalActivity.usuario = usuario;

            References.db.collection("profile").document("user").set(usuario);

        }else{
            // fazer login
        }

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
