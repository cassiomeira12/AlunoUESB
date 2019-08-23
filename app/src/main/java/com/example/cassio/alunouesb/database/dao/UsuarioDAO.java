package com.example.cassio.alunouesb.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cassio.alunouesb.database.DadosOpenHelper;
import com.example.cassio.alunouesb.database.contract.UsuarioContract;
import com.example.cassio.alunouesb.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 06/09/17.
 */

public class UsuarioDAO {

    private SQLiteDatabase db;
    private DadosOpenHelper banco;

    //Construtor da Classe
    public UsuarioDAO(Context context) {
        banco = new DadosOpenHelper(context);
    }

    public static UsuarioDAO getInstance(Context context) {
        return new UsuarioDAO(context);
    }



    public long inserirDados(Usuario usuario) {
        ContentValues valores = new ContentValues();
        long resultadoID;

        db = banco.getWritableDatabase();

        valores.put(UsuarioContract.NOME, usuario.getNome());
        valores.put(UsuarioContract.CURSO, usuario.getCurso());
        valores.put(UsuarioContract.EMAIL, usuario.getEmail());
        valores.put(UsuarioContract.SENHA, usuario.getSenha());
        valores.put(UsuarioContract.ID_SEMESTRE, "0");

        resultadoID = db.insertOrThrow(UsuarioContract.TABELA, null, valores);
        db.close();
        return resultadoID;
    }

    public void alterarRegistro(Usuario usuario) {
        ContentValues valores = new ContentValues();
        String where;

        db = banco.getReadableDatabase();
        //add os novos valores para Email, senha e Uid

        valores.put(UsuarioContract.NOME, usuario.getNome());
        valores.put(UsuarioContract.CURSO, usuario.getCurso());
        valores.put(UsuarioContract.EMAIL, usuario.getEmail());
        valores.put(UsuarioContract.SENHA, usuario.getSenha());
        valores.put(UsuarioContract.UID, usuario.getUid()); //novo
        valores.put(UsuarioContract.MATRICULA, usuario.getMatricula()); //novo
        valores.put(UsuarioContract.ID_SEMESTRE, usuario.getIdSemestre()); //novo

        db.close();
    }

    public void deletarRegistro(long id) {
        String where = UsuarioContract.TABELA + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(UsuarioContract.TABELA, where, null);
        db.close();
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> usuarioList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        db = banco.getReadableDatabase();

        sql.append(" SELECT " + UsuarioContract.ID + ", " +
                UsuarioContract.NOME + ", " +
                UsuarioContract.EMAIL + ", " +
                UsuarioContract.SENHA + ", " +
                UsuarioContract.UID + ", " +
                UsuarioContract.CURSO + ", " +
                UsuarioContract.MATRICULA + ", " +
                UsuarioContract.ID_SEMESTRE + " ");
        sql.append(" FROM " + UsuarioContract.TABELA + " ");

        Cursor resultado = db.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                long id = resultado.getInt(resultado.getColumnIndexOrThrow(UsuarioContract.ID));
                String email = resultado.getString(resultado.getColumnIndexOrThrow(UsuarioContract.EMAIL));
                String senha = resultado.getString(resultado.getColumnIndexOrThrow(UsuarioContract.SENHA));
                String uid = resultado.getString(resultado.getColumnIndexOrThrow(UsuarioContract.UID));
                String nome = resultado.getString(resultado.getColumnIndexOrThrow(UsuarioContract.NOME));
                String curso = resultado.getString(resultado.getColumnIndexOrThrow(UsuarioContract.CURSO));
                int matricula = resultado.getInt(resultado.getColumnIndexOrThrow(UsuarioContract.MATRICULA));
                long idSemestre = resultado.getInt(resultado.getColumnIndexOrThrow(UsuarioContract.ID_SEMESTRE));

                Usuario usuario = new Usuario();
                usuario.setUid(uid);
                usuario.setEmail(email);
                usuario.setSenha(senha);
                usuario.setNome(nome);
                usuario.setCurso(curso);
                usuario.setMatricula(matricula);

                usuarioList.add(usuario);

            } while (resultado.moveToNext());
        }

        return usuarioList;
    }
}
