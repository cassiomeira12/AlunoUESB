package com.example.cassio.alunouesb.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cassio.alunouesb.database.DadosOpenHelper;
import com.example.cassio.alunouesb.database.contract.SemestreContract;
import com.example.cassio.alunouesb.model.Semestre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 06/09/17.
 */

public class SemestreDAO {

    private SQLiteDatabase db;
    private DadosOpenHelper banco;

    //Construtor da Classe
    public SemestreDAO(Context context) {
        banco = new DadosOpenHelper(context);
    }

    public static SemestreDAO getInstance(Context context) {
        return new SemestreDAO(context);
    }




    public Semestre carregaDadoById(long id){
        Cursor cursor;
        String[] campos =  {SemestreContract.ID, SemestreContract.SEMESTRE, SemestreContract.ID_USUARIO};

        String where = SemestreContract.ID + "=" + id;

        db = banco.getReadableDatabase();

        cursor = db.query(SemestreContract.TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        long idSemestre = cursor.getInt(cursor.getColumnIndexOrThrow(SemestreContract.ID));
        String semestre = cursor.getString(cursor.getColumnIndexOrThrow(SemestreContract.SEMESTRE));
        long idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow(SemestreContract.ID_USUARIO));

        Semestre semestreTemp = new Semestre(semestre);

        return semestreTemp;
    }

    public long inserirDados(Semestre semestre) {
        ContentValues valores = new ContentValues();
        long resultadoID;

        db = banco.getWritableDatabase();

        valores.put(SemestreContract.SEMESTRE, semestre.getSemestre());

        resultadoID = db.insertOrThrow(SemestreContract.TABELA, null, valores);
        db.close();
        return resultadoID;
    }

    public void alterarRegistro(Semestre semestre) {
        ContentValues valores = new ContentValues();
        String where;


        valores.put(SemestreContract.SEMESTRE, semestre.getSemestre());

    }

    public void deletarRegistro(long id) {
        String where = SemestreContract.TABELA + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(SemestreContract.TABELA, where, null);
        db.close();
    }

    public List<Semestre> buscarTodos() {
        List<Semestre> semestreList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        db = banco.getReadableDatabase();

        sql.append(" SELECT " + SemestreContract.ID + ", " +
                SemestreContract.SEMESTRE + ", " +
                SemestreContract.ID_USUARIO + " ");
        sql.append(" FROM " + SemestreContract.TABELA + " ");

        Cursor resultado = db.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                long id = resultado.getInt(resultado.getColumnIndexOrThrow(SemestreContract.ID));
                String semestre = resultado.getString(resultado.getColumnIndexOrThrow(SemestreContract.SEMESTRE));
                long idUsuario = resultado.getInt(resultado.getColumnIndexOrThrow(SemestreContract.ID_USUARIO));

                Semestre semestreTemp = new Semestre(semestre);
                semestreList.add(semestreTemp);

            } while (resultado.moveToNext());
        }

        return semestreList;
    }

    public List<String> getSemestres() {
        List<String> semestreList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        db = banco.getReadableDatabase();

        sql.append(" SELECT " + SemestreContract.ID + ", " +
                SemestreContract.SEMESTRE + ", " +
                SemestreContract.ID_USUARIO + " ");
        sql.append(" FROM " + SemestreContract.TABELA + " ");

        Cursor resultado = db.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                String semestre = resultado.getString(resultado.getColumnIndexOrThrow(SemestreContract.SEMESTRE));

                semestreList.add(semestre);

            } while (resultado.moveToNext());
        }

        return semestreList;
    }
}
