package com.example.cassio.alunouesb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    public static Usuario USUARIO;
    public static Semestre SEMESTRE;

    private TextView usuarioNome;
    private TextView usuarioCurso;
    private TextView textSemestre;
    private ImageView imageCurso;

    private Intent intent;

    private int REQUEST_NOVO_USUARIO = 1;
    private int REQUEST_ABRIR_USUARIO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        usuarioNome = (TextView) findViewById(R.id.usuario_nome);
        usuarioCurso = (TextView) findViewById(R.id.usuario_curso);
        textSemestre = (TextView) findViewById(R.id.text_semestre);
        imageCurso = (ImageView) findViewById(R.id.image_curso);



        if (!UsuarioDAO.getInstance(this).buscarTodos().isEmpty()) {

            this.USUARIO = UsuarioDAO.getInstance(this).buscarTodos().get(0);
            usuarioNome.setText(USUARIO.getNome());
            usuarioCurso.setText(USUARIO.getCurso());
            SEMESTRE = SemestreDAO.getInstance(this).carregaDadoById(USUARIO.getIdSemestre());
            textSemestre.setText("Semestre " + SEMESTRE.getSemestre());
            mudarImagemCurso(USUARIO.getCurso());

        } else {

            this.intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_NOVO_USUARIO);

        }

    }

    public void chamarTelaHorarios(View view) {
        this.intent = new Intent(this, HorariosActivity.class);
        startActivity(intent);
    }

    public void chamarTelaLembretes(View view) {
        this.intent = new Intent(this, LembretesActivity.class);
        startActivity(intent);
    }

    public void chamarTelaDisciplinas(View view) {
        this.intent = new Intent(this, DisciplinasActivity.class);
        startActivity(intent);
    }

    public void chamarTelaCalcularMedia(View view) {
        this.intent = new Intent(this, CalcularMediaActivity.class);
        startActivity(intent);
    }

    public void chamarTelaUsuario(View view) {

        if (UsuarioDAO.getInstance(this).buscarTodos().isEmpty()) {
            this.intent = new Intent(this, AdicionarUsuarioActivity.class);
            startActivityForResult(intent, REQUEST_NOVO_USUARIO);

        } else {
            this.intent = new Intent(this, UsuarioActivity.class);
            startActivityForResult(intent, REQUEST_ABRIR_USUARIO);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Caso adicionou um novo Item
        if (requestCode == REQUEST_NOVO_USUARIO && data != null) {
            USUARIO = (Usuario) data.getSerializableExtra("usuario");
        }

        if (requestCode == REQUEST_ABRIR_USUARIO && resultCode == 1) {
            USUARIO = (Usuario) data.getSerializableExtra("usuario");
        }

        usuarioNome.setText(USUARIO.getNome());
        usuarioCurso.setText(USUARIO.getCurso());
        SEMESTRE = SemestreDAO.getInstance(this).carregaDadoById(USUARIO.getIdSemestre());
        textSemestre.setText("Semestre " + SEMESTRE.getSemestre());
        mudarImagemCurso(USUARIO.getCurso());

        super.onActivityResult(requestCode, resultCode, data);
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
}
