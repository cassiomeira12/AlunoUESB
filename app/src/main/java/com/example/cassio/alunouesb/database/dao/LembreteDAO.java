package com.example.cassio.alunouesb.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cassio.alunouesb.database.DadosOpenHelper;
import com.example.cassio.alunouesb.database.contract.LembreteContract;
import com.example.cassio.alunouesb.model.Lembrete;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 02/09/17.
 */

public class LembreteDAO {

    private SQLiteDatabase db;
    private DadosOpenHelper banco;

    public LembreteDAO(Context context) {
        banco = new DadosOpenHelper(context);
    }

    public static LembreteDAO getInstance(Context context) {
        return new LembreteDAO(context);
    }

    public long inserirDados(Lembrete lembrete) {
        ContentValues valores = new ContentValues();
        long resultadoID;

        db = banco.getWritableDatabase();

        valores.put(LembreteContract.TITULO, lembrete.getTitulo());
        valores.put(LembreteContract.MENSAGEM, lembrete.getMensagem());
        valores.put(LembreteContract.DATA, lembrete.getData());
        valores.put(LembreteContract.ID_SEMESTRE, lembrete.getIdSemestre());

        resultadoID = db.insertOrThrow(LembreteContract.TABELA, null, valores);//Insere dados com Excessao
        db.close();
        return resultadoID;
    }

    /*
    public Cursor carregarDados() {
        Cursor cursor;
        String[] compos = {LembreteContract.ID, LembreteContract.TITULO, LembreteContract.MENSAGEM, LembreteContract.DATA};
        db = banco.getReadableDatabase();
        cursor = db.query(LembreteContract.TABELA, compos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos =  {LembreteContract.ID,LembreteContract.TITULO,LembreteContract.MENSAGEM,LembreteContract.DATA};
        String where = LembreteContract.ID + "=" + id;

        db = banco.getReadableDatabase();

        cursor = db.query(LembreteContract.TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }
    */

    public void alteraRegistro(Lembrete lembrete){
        ContentValues valores = new ContentValues();
        String where;

        db = banco.getWritableDatabase();

        where = LembreteContract.ID + "=" + lembrete.getId();

        valores.put(LembreteContract.TITULO, lembrete.getTitulo());
        valores.put(LembreteContract.MENSAGEM, lembrete.getMensagem());
        valores.put(LembreteContract.DATA, lembrete.getData());
        valores.put(LembreteContract.ID_SEMESTRE, lembrete.getIdSemestre());

        db.update(LembreteContract.TABELA,valores,where,null);
        db.close();
    }

    public void deletaRegistro(long id){
        String where = LembreteContract.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(LembreteContract.TABELA, where, null);
        db.close();
    }

    public List<Lembrete> buscarTodos(long idSemestre) {
        List<Lembrete> lembreteList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        db = banco.getReadableDatabase();

        String[] campos =  {LembreteContract.ID, LembreteContract.TITULO, LembreteContract.MENSAGEM,
                            LembreteContract.DATA, LembreteContract.ID_SEMESTRE};


        String where = LembreteContract.ID_SEMESTRE + "=" + idSemestre;

        Cursor resultado = db.query(LembreteContract.TABELA, campos, where, null, null, null, null, null);



        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                long id = resultado.getInt(resultado.getColumnIndexOrThrow(LembreteContract.ID));
                String titulo = resultado.getString(resultado.getColumnIndexOrThrow(LembreteContract.TITULO));
                String mensagem = resultado.getString(resultado.getColumnIndexOrThrow(LembreteContract.MENSAGEM));
                long data = resultado.getLong(resultado.getColumnIndexOrThrow(LembreteContract.DATA));

                Lembrete lembrete = new Lembrete(id, titulo, mensagem, data);
                //lembreteList.add(lembrete);
                lembreteList.add(0, lembrete);

            } while (resultado.moveToNext());
        }


        return lembreteList;
    }


    public List<Lembrete> buscarTodos() {
        List<Lembrete> lembretes = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        db = banco.getReadableDatabase();

        sql.append(" SELECT " + LembreteContract.ID + ", " +
                                LembreteContract.TITULO + ", " +
                                LembreteContract.MENSAGEM + ", " +
                                LembreteContract.DATA + ", " +
                                LembreteContract.ID_SEMESTRE + " ");
        sql.append(" FROM " + LembreteContract.TABELA + " ");

        Cursor resultado = db.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                long id = resultado.getInt(resultado.getColumnIndexOrThrow(LembreteContract.ID));
                String titulo = resultado.getString(resultado.getColumnIndexOrThrow(LembreteContract.TITULO));
                String mensagem = resultado.getString(resultado.getColumnIndexOrThrow(LembreteContract.MENSAGEM));
                long data = resultado.getLong(resultado.getColumnIndexOrThrow(LembreteContract.DATA));
                long idSemestre = resultado.getLong(resultado.getColumnIndexOrThrow(LembreteContract.ID_SEMESTRE));

                Lembrete lembrete = new Lembrete(id, titulo, mensagem, data);
                lembretes.add(lembrete);

            } while (resultado.moveToNext());
        }

        return lembretes;
    }
}
