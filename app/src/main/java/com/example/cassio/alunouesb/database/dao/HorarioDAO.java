package com.example.cassio.alunouesb.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cassio.alunouesb.database.DadosOpenHelper;
import com.example.cassio.alunouesb.database.contract.HorarioContract;
import com.example.cassio.alunouesb.model.Horario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 05/09/17.
 */

public class HorarioDAO {

    private SQLiteDatabase db;
    private DadosOpenHelper banco;

    //Construtor da classe
    public HorarioDAO(Context context) {
        banco = new DadosOpenHelper(context);
    }

    public static HorarioDAO getInstance(Context context) {
        return new HorarioDAO(context);
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos =  {HorarioContract.ID, HorarioContract.DIA, HorarioContract.HORARIO, HorarioContract.ID_DISCIPLINA};
        String where = HorarioContract.ID + "=" + id;

        db = banco.getReadableDatabase();

        cursor = db.query(HorarioContract.TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    public long inserirDados(Horario horario) {
        ContentValues valores = new ContentValues();
        long resultadoID;

        db = banco.getWritableDatabase();

        valores.put(HorarioContract.DIA, horario.getDia());
        valores.put(HorarioContract.HORARIO, horario.getHorario());

        resultadoID = db.insertOrThrow(HorarioContract.TABELA, null, valores);
        db.close();
        return resultadoID;
    }
//
//    public void alterarRegistro(Horario horario) {
//        ContentValues valores = new ContentValues();
//        String where;
//
//        db = banco.getReadableDatabase();
//
//
//        valores.put(HorarioContract.DIA, horario.getDia());
//        valores.put(HorarioContract.HORARIO, horario.getHorario());
//
//        db.update(HorarioContract.TABELA, valores, where, null);
//        db.close();
//    }
//
//    public void deletarRegistro(long id) {
//        String where = HorarioContract.TABELA + "=" + id;
//        db = banco.getReadableDatabase();
//        db.delete(HorarioContract.TABELA, where, null);
//        db.close();
//    }
//
//    public List<Horario> buscarTodos(long idDisciplina) {
//        List<Horario> horarioList = new ArrayList<>();
//        StringBuilder sql = new StringBuilder();
//
//        db = banco.getReadableDatabase();
//
//        String[] campos =  {HorarioContract.ID, HorarioContract.DIA, HorarioContract.HORARIO,
//                HorarioContract.ID_DISCIPLINA};
//
//
//        String where = HorarioContract.ID_DISCIPLINA + "=" + idDisciplina;
//
//        Cursor resultado = db.query(HorarioContract.TABELA, campos, where, null, null, null, null, null);
//
//
//
//        if (resultado.getCount() > 0) {
//            resultado.moveToFirst();
//
//            do {
//                long id = resultado.getInt(resultado.getColumnIndexOrThrow(HorarioContract.ID));
//                int dia = resultado.getInt(resultado.getColumnIndexOrThrow(HorarioContract.DIA));
//                int horario = resultado.getInt(resultado.getColumnIndexOrThrow(HorarioContract.HORARIO));
//
//                Horario horarioTemp = new Horario(id, dia, horario, idDisciplina);
//                horarioList.add(horarioTemp);
//
//            } while (resultado.moveToNext());
//        }
//
//
//        return horarioList;
//    }


}
