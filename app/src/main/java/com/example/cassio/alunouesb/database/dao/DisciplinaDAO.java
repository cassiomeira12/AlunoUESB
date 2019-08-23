package com.example.cassio.alunouesb.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cassio.alunouesb.database.DadosOpenHelper;
import com.example.cassio.alunouesb.database.contract.DisciplinaContract;
import com.example.cassio.alunouesb.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 05/09/17.
 */

public class DisciplinaDAO {

//    private SQLiteDatabase db;
//    private DadosOpenHelper banco;
//
//    //Construtor da classe
//    public DisciplinaDAO(Context context) {
//        banco = new DadosOpenHelper(context);
//    }
//
//    public static DisciplinaDAO getInstance(Context context) {
//        return new DisciplinaDAO(context);
//    }
//
//
//    public Disciplina carregaDadoById(long id){
//        Cursor resultado;
//        String[] campos =  {DisciplinaContract.ID, DisciplinaContract.NOME, DisciplinaContract.ABREVIACAO,
//                DisciplinaContract.ID_PROFESSOR, DisciplinaContract.ID_SEMESTRE};
//
//        String where = DisciplinaContract.ID + "=" + id;
//
//        db = banco.getReadableDatabase();
//
//        resultado = db.query(DisciplinaContract.TABELA,campos,where, null, null, null, null, null);
//
//        if(resultado!=null){
//            resultado.moveToFirst();
//        }
//
//        db.close();
//
//        long idDisciplina = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID));
//        String nome = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.NOME));
//        String abreviacao = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.ABREVIACAO));
//        long idProfessor = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID_PROFESSOR));
//        /*String unidade1 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE1));
//        String unidade2 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE2));
//        String unidade3 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE3));
//        String notaFinal = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.NOTA_FINAL));
//        String media = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.MEDIA));*/
//        long idSemestre = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID_SEMESTRE));
//
//        Disciplina disciplina = new Disciplina(idDisciplina, nome, abreviacao, idProfessor);
//        /*disciplina.setUnidade1(Float.parseFloat(unidade1));
//        disciplina.setUnidade2(Float.parseFloat(unidade2));
//        disciplina.setUnidade3(Float.parseFloat(unidade3));
//        disciplina.setNotaFinal(Float.parseFloat(notaFinal));
//        disciplina.setMedia(Float.parseFloat(media));*/
//
//        return disciplina;
//    }
//
//    public long inserirDados(Disciplina disciplina) {
//        ContentValues valores = new ContentValues();
//        long resultadoID;
//
//        db = banco.getWritableDatabase();
//
//        valores.put(DisciplinaContract.NOME, disciplina.getNome());
//        valores.put(DisciplinaContract.ABREVIACAO, disciplina.getAbreviacao());
//        valores.put(DisciplinaContract.ID_PROFESSOR, disciplina.getIdProfessor());
//        valores.put(DisciplinaContract.ID_SEMESTRE, disciplina.getIdSemestre());
//        valores.put(DisciplinaContract.UNIDADE1, disciplina.getUnidade1());
//        valores.put(DisciplinaContract.UNIDADE2, disciplina.getUnidade2());
//        valores.put(DisciplinaContract.UNIDADE3, disciplina.getUnidade3());
//        valores.put(DisciplinaContract.NOTA_FINAL, disciplina.getNotaFinal());
//        valores.put(DisciplinaContract.MEDIA, disciplina.getMedia());
//
//        resultadoID = db.insertOrThrow(DisciplinaContract.TABELA, null, valores);
//        db.close();
//        return resultadoID;
//    }
//
//    public void alterarRegistros(Disciplina disciplina) {
//        ContentValues valores = new ContentValues();
//        String where;
//
//        db = banco.getReadableDatabase();
//
//        where = DisciplinaContract.ID + "=" + disciplina.getId();
//
//        valores.put(DisciplinaContract.NOME, disciplina.getNome());
//        valores.put(DisciplinaContract.ABREVIACAO, disciplina.getAbreviacao());
//        valores.put(DisciplinaContract.ID_PROFESSOR, disciplina.getIdProfessor());
//        valores.put(DisciplinaContract.ID_SEMESTRE, disciplina.getIdSemestre());
//        valores.put(DisciplinaContract.UNIDADE1, disciplina.getUnidade1());
//        valores.put(DisciplinaContract.UNIDADE2, disciplina.getUnidade2());
//        valores.put(DisciplinaContract.UNIDADE3, disciplina.getUnidade3());
//        valores.put(DisciplinaContract.NOTA_FINAL, disciplina.getNotaFinal());
//        valores.put(DisciplinaContract.MEDIA, disciplina.getMedia());
//
//        db.update(DisciplinaContract.TABELA, valores, where, null);
//        db.close();
//    }
//
//    public void deletarRegistros(long id) {
//        String where = DisciplinaContract.ID + "=" + id;
//        db = banco.getReadableDatabase();
//        db.delete(DisciplinaContract.TABELA, where, null);
//        db.close();
//    }
//
//    public List<Disciplina> buscarTodos(long idSemestre) {
//        List<Disciplina> disciplinaList = new ArrayList<>();
//
//        db = banco.getReadableDatabase();
//
//        String[] campos =  {DisciplinaContract.ID, DisciplinaContract.NOME, DisciplinaContract.ABREVIACAO,
//                DisciplinaContract.ID_PROFESSOR, DisciplinaContract.ID_SEMESTRE, DisciplinaContract.UNIDADE1,
//                DisciplinaContract.UNIDADE2, DisciplinaContract.UNIDADE3, DisciplinaContract.NOTA_FINAL,
//                DisciplinaContract.MEDIA};
//
//        String where = DisciplinaContract.ID_SEMESTRE + "=" + idSemestre;
//
//        Cursor resultado = db.query(DisciplinaContract.TABELA, campos, where, null, null, null, null, null);
//
//
//
//        if (resultado.getCount() > 0) {
//            resultado.moveToFirst();
//
//            do {
//                long id = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID));
//                String nome = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.NOME));
//                String abreviacao = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.ABREVIACAO));
//                long idProfessor = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID_PROFESSOR));
//                String unidade1 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE1));
//                String unidade2 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE2));
//                String unidade3 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE3));
//                String notaFinal = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.NOTA_FINAL));
//                String media = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.MEDIA));
//
//                Disciplina disciplina = new Disciplina(id, nome, abreviacao, idProfessor);
//                disciplina.setUnidade1(Float.parseFloat(unidade1));
//                disciplina.setUnidade2(Float.parseFloat(unidade2));
//                disciplina.setUnidade3(Float.parseFloat(unidade3));
//                disciplina.setNotaFinal(Float.parseFloat(notaFinal));
//                disciplina.setMedia(Float.parseFloat(media));
//                disciplinaList.add(disciplina);
//
//            } while (resultado.moveToNext());
//        }
//
//
//        return disciplinaList;
//    }
//
//
//
//    public List<Disciplina> buscarTodos() {
//        List<Disciplina> disciplinas = new ArrayList<>();
//        StringBuilder sql = new StringBuilder();
//
//        db = banco.getReadableDatabase();
//
//        sql.append(" SELECT " + DisciplinaContract.ID + ", " +
//                DisciplinaContract.NOME + ", " +
//                DisciplinaContract.ABREVIACAO + ", " +
//                DisciplinaContract.ID_PROFESSOR + ", " +
//                DisciplinaContract.ID_SEMESTRE + ", " +
//                DisciplinaContract.UNIDADE1 + ", " +
//                DisciplinaContract.UNIDADE2 + ", " +
//                DisciplinaContract.UNIDADE3 + ", " +
//                DisciplinaContract.NOTA_FINAL + ", " +
//                DisciplinaContract.MEDIA + " ");
//        sql.append(" FROM " + DisciplinaContract.TABELA + " ");
//
//        Cursor resultado = db.rawQuery(sql.toString(), null);
//
//        if (resultado.getCount() > 0) {
//            resultado.moveToFirst();
//
//            do {
//                long id = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID));
//                String nome = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.NOME));
//                String abreviacao = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.ABREVIACAO));
//                long idProfessor = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID_PROFESSOR));
//                long idSemestre = resultado.getInt(resultado.getColumnIndexOrThrow(DisciplinaContract.ID_SEMESTRE));
//                String unidade1 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE1));
//                String unidade2 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE2));
//                String unidade3 = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.UNIDADE3));
//                String notaFinal = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.NOTA_FINAL));
//                String media = resultado.getString(resultado.getColumnIndexOrThrow(DisciplinaContract.MEDIA));
//
//                Disciplina disciplina = new Disciplina(id, nome, abreviacao, idProfessor);
//                disciplina.setUnidade1(Float.parseFloat(unidade1));
//                disciplina.setUnidade2(Float.parseFloat(unidade2));
//                disciplina.setUnidade3(Float.parseFloat(unidade3));
//                disciplina.setNotaFinal(Float.parseFloat(notaFinal));
//                disciplina.setMedia(Float.parseFloat(media));
//                disciplinas.add(disciplina);
//
//            } while (resultado.moveToNext());
//        }
//
//        return disciplinas;
//    }
}
