package com.navan.app.alunouesb.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.contract.IUserContract;
import com.navan.app.alunouesb.data.CompleteUserSingleton;
import com.navan.app.alunouesb.data.model.Semestre;
import com.navan.app.alunouesb.data.model.Usuario;
import com.navan.app.alunouesb.presenter.usuario.UsuarioPresenter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsuarioActivity extends AppCompatActivity implements IUserContract.View {
    File arquivo;
    private static final int PICK_IMAGE = 1234;

    IUserContract.Presenter iPresenter;

    private EditText usuarioNome;
    private MaterialBetterSpinner spinnerCurso;
    private EditText matricula;
    private Spinner spinnerSemestre;
    private EditText novoSemestre;
    private ArrayAdapter<String> arrayAdapter;

    private CircleImageView imagePerfil;

    private Usuario usuario;
    private boolean permitirEdicao = true;
    private Menu menu;

    private ProgressDialog mDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        setTitle("Meus Dados");

        usuario = CompleteUserSingleton.Companion.getInstance();

        iPresenter = new UsuarioPresenter(this);
        arquivo = new File(getBaseContext().getFilesDir(), "profilePic");

        usuarioNome = findViewById(R.id.text_usuario_nome);
        spinnerCurso = findViewById(R.id.spinner_usuario_curso);
        matricula = findViewById(R.id.text_matricula);
        novoSemestre = findViewById(R.id.text_novo_semestre);

        progressBar = findViewById(R.id.progressBarProfile);

        imagePerfil = findViewById(R.id.imagemPerfil);

        carregarImagemDePerfil();


        String[] cursos = {"Administração", "Agronomia", "Biologia", "Cinema", "Ciências Socias", "Ciência da Computação",
                            "Contabilidade", "Direito", "Economia", "Educação Física", "Engenharia Florestal", "Filosofia",
                            "Física", "Geografia", "História", "Matemática", "Medicina", "Pedagogia", "Psicologia"};

        ArrayAdapter<String> adapterCurso = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cursos);
        spinnerCurso.setAdapter(adapterCurso);


        spinnerSemestre = findViewById(R.id.spinner_semestre);
        spinnerSemestre.setEnabled(false);


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, usuario.getSemestreList());
        spinnerSemestre.setAdapter(arrayAdapter);

        usuarioNome.setText(usuario.name);
        spinnerCurso.setText(usuario.getCurso());

        if(usuario.getMatricula() == 0){
            matricula.setHint("Matricula");
        }else{
            matricula.setText(String.valueOf(usuario.getMatricula()));
        }

        spinnerSemestre.setSelection(arrayAdapter.getPosition(usuario.getSemestre(usuario.getIdSemestre())));

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

            usuario.getSemestreList().add(semestre);
            spinnerSemestre.setSelection(arrayAdapter.getPosition(semestre));

            iPresenter.update(usuario);

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

        usuario.name = nome;
        usuario.setCurso(curso);

        if(matricula != 0) {
            usuario.setMatricula(matricula);
        }

        iPresenter.update(usuario);

        habilitarEdicao();

    }


    private void habilitarEdicao() {

        this.permitirEdicao = (!this.permitirEdicao);

        usuarioNome.setEnabled(permitirEdicao);
        matricula.setEnabled(permitirEdicao);
        spinnerCurso.setEnabled(permitirEdicao);
        spinnerSemestre.setEnabled(permitirEdicao);

    }


    public void selecionarImagem(View view){

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE);

    }

    // carrega imagem do usuario do ImageView (localmente ou pela internet)
    private void carregarImagemDePerfil() {

        if (arquivo.exists()){ // existe na memoria interna no cell

            Uri uri = Uri.fromFile(arquivo);

            try {

                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagePerfil.setImageBitmap(imagem); // carrega imagem no imageView

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{ // se nao existe na memoria interna, faz download do firebase

            iPresenter.getProfilePick(arquivo); // busca a imagem no firebase
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // setar Imagem no ImageView, salva na memoria do APP e faz Upload para o banco de dados

        if (requestCode == PICK_IMAGE) {
            if (data != null) { // imagem selecionada

                Uri imagemSelecionada = data.getData();

                imagePerfil.setImageURI(imagemSelecionada); // seta imagem no ImageView

                try { // salva localmente na memoria do APP

                    FileOutputStream saida = new FileOutputStream(arquivo);

                    Bitmap imagem = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagemSelecionada);

                    imagem.compress(Bitmap.CompressFormat.PNG, 100, saida);

                    saida.flush();
                    saida.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                // chama função do FIRABASE PARA SALVAR FOTO

                iPresenter.updateProfilePick(imagemSelecionada); // salva no firebase
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onGetProfilePicSucess(@NotNull String url) {
        if (arquivo.exists()) {

            Uri uri = Uri.fromFile(arquivo);

            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagePerfil.setImageBitmap(imagem);

                FileOutputStream saida = new FileOutputStream(arquivo);

                if (imagem != null) { //
                    imagem.compress(Bitmap.CompressFormat.PNG, 100, saida);
                    saida.flush();
                    saida.close();
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public void onFailure(@NotNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserSuccess(@NotNull Usuario user) {
        Toast.makeText(this, "Atualizado com Sucesso", Toast.LENGTH_SHORT).show();
        CompleteUserSingleton.Companion.getInstance().setUsuario(user); // atualizar o usuario localmente
    }

    @Override
    public void onRemoveUserSucess(@NotNull Usuario user) {
        Toast.makeText(this, "Usuario removido com Sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateProfilePicSuccess(@NotNull String url) {
        CompleteUserSingleton.Companion.getInstance().avatarURL = url; // salva a url do avatar
        iPresenter.update(CompleteUserSingleton.Companion.getInstance()); // adiciona no banco de dados atualizacao do user com avatar
    }

    @Override
    public void onRemoveProfilePicSucess(@NotNull String url) {
        Toast.makeText(this, "Avatar removido com Sucesso", Toast.LENGTH_SHORT).show();
    }

}
