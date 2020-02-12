package com.navan.app.alunouesb.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.app.contract.IUser;
import com.android.app.presenter.login.UserPresenter;
import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.data.CompleteUserSingleton;
import com.navan.app.alunouesb.data.model.BaseUser;
import com.navan.app.alunouesb.data.model.Semestre;
import com.navan.app.alunouesb.data.model.Usuario;
import com.navan.app.alunouesb.view.dialog.DialogExcluir;
import com.navan.app.alunouesb.view.login.LoginActivity;

import org.jetbrains.annotations.Nullable;

import static java.sql.DriverManager.println;

public class PrincipalActivity extends AppCompatActivity implements DialogExcluir.OnExcluir {

    public static Usuario usuario;
    public static Semestre semestre;

    private TextView txtNome;
    private TextView txtCurso;
    private TextView textSemestre;
    private ImageView imageCurso;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        txtNome = findViewById(R.id.txtNome);
        txtCurso = findViewById(R.id.txtCurso);
        textSemestre = findViewById(R.id.txtSemestrePrincipal);
        imageCurso = findViewById(R.id.image_curso);

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Carregando..");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);

        Usuario user = CompleteUserSingleton.Companion.getInstance();

        System.out.println("Curso: " + user.getCurso());
        System.out.println("Matricula: " + user.getMatricula());
        System.out.println("Semestre list: " + user.getSemestreList().toString());
        System.out.println("Id semestre: " + user.getIdSemestre());
        System.out.println("uID: " + user.uID);
        System.out.println("Status: " + user.status);
        System.out.println("Id semestre: " + user.getIdSemestre());
        System.out.println("nome: " + user.name);
        System.out.println("email: " + user.email);
        System.out.println("EmailVirified: " + user.emailVerified);
        System.out.println("creatAt: " + user.createAt);
        System.out.println("updateAt: " + user.updateAt);

        mostrarDadosUsuario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){
            DialogExcluir sair = new DialogExcluir(); // reaproveitar o Dialog de excluir
            sair.setOnExcluir(this);
            sair.show(getSupportFragmentManager(), "Deseja sair da sua conta?");
        }

        return super.onOptionsItemSelected(item);
    }

    private void mostrarDadosUsuario() {
        Usuario user = CompleteUserSingleton.Companion.getInstance();

        txtNome.setText(user.name);
        txtCurso.setText(user.getCurso());
        textSemestre.setText(user.getSemestre(user.getIdSemestre()));
        mudarImagemCurso(user.getCurso());
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
         startActivity(telaUsuario);
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
    public void onExcluir() { // reusa o Dialog de excluir para fazer Logout
        new UserPresenter(new IUser.View() {

            @Override
            public void onResult(@Nullable Usuario user) {

            }

            public void onResult(@Nullable BaseUser user) {
                startActivity(new Intent(getApplication(), LoginActivity.class));
                finish();
            }
        }).signOut(this);
    }
}
