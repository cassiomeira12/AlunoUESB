package com.example.cassio.alunouesb.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cassio.alunouesb.database.DadosOpenHelper;
import com.example.cassio.alunouesb.database.contract.ProfessorContract;
import com.example.cassio.alunouesb.database.contract.SemestreContract;
import com.example.cassio.alunouesb.model.Professor;
import com.example.cassio.alunouesb.model.Semestre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 06/09/17.
 */

public class ProfessorDAO {

    private SQLiteDatabase db;
    private DadosOpenHelper banco;

    //Construtor da Classe
    public ProfessorDAO(Context context) {
        banco = new DadosOpenHelper(context);
    }

    public static ProfessorDAO getInstance(Context context) {
        return new ProfessorDAO(context);
    }




//    public Professor carregaDadoById(long id){
//        Cursor cursor;
//        String[] campos =  {ProfessorContract.ID, ProfessorContract.NOME, ProfessorContract.EMAIL};
//
//        String where = ProfessorContract.ID + "=" + id;
//
//        db = banco.getReadableDatabase();
//
//        cursor = db.query(ProfessorContract.TABELA,campos,where, null, null, null, null, null);
//
//        if(cursor!=null){
//            cursor.moveToFirst();
//        }
//
//        db.close();
//
//        long idProfessor = cursor.getInt(cursor.getColumnIndexOrThrow(ProfessorContract.ID));
//        String nome = cursor.getString(cursor.getColumnIndexOrThrow(ProfessorContract.NOME));
//        String email = cursor.getString(cursor.getColumnIndexOrThrow(ProfessorContract.EMAIL));
//
//        Professor professorTemp = new Professor(idProfessor, nome);
//        professorTemp.setEmail(email);
//
//        return professorTemp;
//    }
//
//    public long inserirDados(Professor professor) {
//        ContentValues valores = new ContentValues();
//        long resultadoID;
//
//        db = banco.getWritableDatabase();
//
//        valores.put(ProfessorContract.NOME, professor.getNome());
//        if (!professor.getEmail().isEmpty()) {
//            valores.put(ProfessorContract.EMAIL, professor.getEmail());
//        }
//
//        resultadoID = db.insertOrThrow(ProfessorContract.TABELA, null, valores);
//        db.close();
//        return resultadoID;
//    }
//
//    public void alterarRegistro(Professor professor) {
//        ContentValues valores = new ContentValues();
//        String where;
//
//        db = banco.getReadableDatabase();
//
//        where = ProfessorContract.ID + "=" + professor.getId();
//
//        valores.put(ProfessorContract.NOME, professor.getNome());
//        valores.put(ProfessorContract.EMAIL, professor.getEmail());
//
//        db.update(ProfessorContract.TABELA, valores, where, null);
//        db.close();
//    }
//
//    public void deletarRegistro(long id) {
//        String where = ProfessorContract.TABELA + "=" + id;
//        db = banco.getReadableDatabase();
//        db.delete(ProfessorContract.TABELA, where, null);
//        db.close();
//    }
//
//    public List<Professor> buscarTodos() {
//        List<Professor> professorList = new ArrayList<>();
//        StringBuilder sql = new StringBuilder();
//
//        db = banco.getReadableDatabase();
//
//        sql.append(" SELECT " + ProfessorContract.ID + ", " +
//                ProfessorContract.NOME + ", " +
//                ProfessorContract.EMAIL + " ");
//        sql.append(" FROM " + ProfessorContract.TABELA + " ");
//
//        Cursor resultado = db.rawQuery(sql.toString(), null);
//
//        if (resultado.getCount() > 0) {
//            resultado.moveToFirst();
//
//            do {
//                long id = resultado.getInt(resultado.getColumnIndexOrThrow(ProfessorContract.ID));
//                String nome = resultado.getString(resultado.getColumnIndexOrThrow(ProfessorContract.NOME));
//                String email = resultado.getString(resultado.getColumnIndexOrThrow(ProfessorContract.EMAIL));
//
//                Professor professorTemp = new Professor(id, nome);
//                professorTemp.setEmail(email);
//                professorList.add(professorTemp);
//
//            } while (resultado.moveToNext());
//        }
//
//        return professorList;
//    }


}
