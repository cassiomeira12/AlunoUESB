package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.database.dao.SemestreDAO;
import com.example.cassio.alunouesb.database.dao.UsuarioDAO;
import com.example.cassio.alunouesb.model.Lembrete;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.model.Document;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class PrincipalActivity extends AppCompatActivity {

    public static Usuario usuario;
    public static Semestre semestre;
    public static final int ALTERAR_DADOS = 1;
    public static final int NOVO_USUARIO = 2;

    private TextView usuarioNome;
    private TextView usuarioCurso;
    private TextView textSemestre;
    private ImageView imageCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        usuarioNome = findViewById(R.id.usuario_nome);
        usuarioCurso = findViewById(R.id.usuario_curso);
        textSemestre = findViewById(R.id.text_semestre);
        imageCurso = findViewById(R.id.image_curso);

        if(FirebaseAuth.getInstance().getUid() != null ){

            // carrega os dados do usuario
            String uid = FirebaseAuth.getInstance().getUid();
            FirebaseFirestore.getInstance().collection("/users").document(uid)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if(e != null){
                                Toast.makeText(PrincipalActivity.this, "Erro no Snapshot", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            usuario = documentSnapshot.toObject(Usuario.class);
                            carregarDados();
                        }
            });



        }else{
            Intent telaLogin = new Intent(this, LoginActivity.class);
            telaLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(telaLogin);
        }



    }

    private void carregarDados() {
        semestre = usuario.getSemestre(usuario.getIdSemestre());

        usuarioNome.setText(usuario.getNome());
        usuarioCurso.setText(usuario.getCurso());
        textSemestre.setText(semestre.getSemestre());
        mudarImagemCurso(usuario.getCurso());
    }

    public void chamarTelaHorarios(View view) {
        Intent telaHorarios = new Intent(this, HorariosActivity.class);
        startActivity(telaHorarios);
    }

    public void chamarTelaLembretes(View view) {
        Intent telaLembretes = new Intent(this, LembretesActivity.class);
        startActivity(telaLembretes);
    }

    public void chamarTelaDisciplinas(View view) {
        Intent telaDisciplinas = new Intent(this, DisciplinasActivity.class);
        startActivity(telaDisciplinas);
    }

    public void chamarTelaCalcularMedia(View view) {
        Intent telaCalcularMedia = new Intent(this, CalcularMediaActivity.class);
        startActivity(telaCalcularMedia);
    }

    public void chamarTelaUsuario(View view) {
         Intent telaUsuario = new Intent(this, UsuarioActivity.class);
         startActivityForResult(telaUsuario, NOVO_USUARIO);
    }


    private void mudarImagemCurso(String curso) {

        switch (curso) {

            case "Administração":
                imageCurso.setImageResource(R.drawable.administracao);
                break;

            case "Agronomia":
                imageCurso.setImageResource(R.drawable.agronomia);
                break;

            case "Biologia":
                imageCurso.setImageResource(R.drawable.biologia);
                break;

            case "Cinema":
                imageCurso.setImageResource(R.drawable.cinema);
                break;

            case "Ciências Socias":
                imageCurso.setImageResource(R.drawable.ciencia_sociais);
                break;

            case "Ciência da Computação":
                imageCurso.setImageResource(R.drawable.ciencia_da_computacao);
                break;

            case "Contabilidade":
                imageCurso.setImageResource(R.drawable.contabilidade);
                break;

            case "Direito":
                imageCurso.setImageResource(R.drawable.direito);
                break;

            case "Economia":
                imageCurso.setImageResource(R.drawable.economia);
                break;

            case "Educação Física":
                imageCurso.setImageResource(R.drawable.educacao_fisica);
                break;

            case "Engenharia Florestal":
                imageCurso.setImageResource(R.drawable.engenharia_florestal);
                break;

            case "Filosofia":
                imageCurso.setImageResource(R.drawable.filosofia);
                break;

            case "Física":
                imageCurso.setImageResource(R.drawable.fisica);
                break;

            case "Geografia":
                imageCurso.setImageResource(R.drawable.geografia);
                break;

            case "História":
                imageCurso.setImageResource(R.drawable.historia);
                break;

            case "Matemática":
                imageCurso.setImageResource(R.drawable.matematica);
                break;

            case "Medicina":
                imageCurso.setImageResource(R.drawable.medicina);
                break;

            case "Pedagogia":
                imageCurso.setImageResource(R.drawable.pedagogia);
                break;

            case "Psicologia":
                imageCurso.setImageResource(R.drawable.psicologia);
                break;

            default:
                imageCurso.setImageResource(R.drawable.uesb);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NOVO_USUARIO && resultCode == ALTERAR_DADOS){
            carregarDados();
        }
        carregarDados();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
