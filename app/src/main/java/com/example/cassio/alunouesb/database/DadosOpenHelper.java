package com.example.cassio.alunouesb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cassio.alunouesb.database.contract.DisciplinaContract;
import com.example.cassio.alunouesb.database.contract.HorarioContract;
import com.example.cassio.alunouesb.database.contract.LembreteContract;
import com.example.cassio.alunouesb.database.contract.ProfessorContract;
import com.example.cassio.alunouesb.database.contract.SemestreContract;
import com.example.cassio.alunouesb.database.contract.UsuarioContract;

/**
 * Created by cassio on 02/09/17.
 */

public class DadosOpenHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "aluno_uesb";
    private static final int VERSAO = 1;

    public DadosOpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    private static final String SQL_DROP_USUARIO = "DROP TABLE IF EXISTS " + UsuarioContract.TABELA;
    private static final String SQL_CREATE_TABLE_USUARIO = String.format(
            "CREATE TABLE " + UsuarioContract.TABELA + " ( " +
                    UsuarioContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UsuarioContract.NOME + " TEXT NOT NULL, " +
                    UsuarioContract.CURSO + " TEXT NOT NULL, " +
                    UsuarioContract.MATICULA + " TEXT, " +
                    UsuarioContract.ID_SEMESTRE + " INTEGER NOT NULL);"
    );


    private static final String SQL_DROP_SEMESTRE = "DROP TABLE IF EXISTS " + SemestreContract.TABELA;
    private static final String SQL_CREATE_TABLE_SEMESTRE = String.format(
            "CREATE TABLE " + SemestreContract.TABELA + " ( " +
                    SemestreContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SemestreContract.SEMESTRE + " TEXT NOT NULL, " +
                    SemestreContract.ID_USUARIO + " INTEGER NOT NULL);"
    );

    private static final String SQL_DROP_DISCIPLINA = "DROP TABLE IF EXISTS " + DisciplinaContract.TABELA;
    private static final String SQL_CREATE_TABLE_DISCIPLINA = String.format(
            "CREATE TABLE " + DisciplinaContract.TABELA + " ( " +
                    DisciplinaContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DisciplinaContract.NOME + " TEXT NOT NULL, " +
                    DisciplinaContract.ABREVIACAO + " TEXT NOT NULL, " +
                    DisciplinaContract.ID_PROFESSOR + " TEXT NOT NULL, " +
                    DisciplinaContract.ID_SEMESTRE + " INTEGER NOT NULL, " +
                    DisciplinaContract.UNIDADE1 + " TEXT NOT NULL, " +
                    DisciplinaContract.UNIDADE2 + " TEXT NOT NULL, " +
                    DisciplinaContract.UNIDADE3 + " TEXT NOT NULL, " +
                    DisciplinaContract.NOTA_FINAL + " TEXT NOT NULL, " +
                    DisciplinaContract.MEDIA + " TEXT NOT NULL);"
    );

    private static final String SQL_DROP_PROFESSOR = "DROP TABLE IF EXISTS " + ProfessorContract.TABELA;
    private static final String SQL_CREATE_TABLE_PROFESSOR = String.format(
            "CREATE TABLE " + ProfessorContract.TABELA + " ( " +
                    ProfessorContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProfessorContract.NOME + " TEXT NOT NULL, " +
                    ProfessorContract.EMAIL + " TEXT);"
    );


    private static final String SQL_DROP_LEMBRETE = "DROP TABLE IF EXISTS " + LembreteContract.TABELA;
    private static final String SQL_CREATE_TABLE_LEMBRETE = String.format(
            "CREATE TABLE " + LembreteContract.TABELA + " ( " +
                    LembreteContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LembreteContract.TITULO + " TEXT NOT NULL, " +
                    LembreteContract.MENSAGEM + " TEXT NOT NULL, " +
                    LembreteContract.DATA + " LONG NOT NULL, " +
                    LembreteContract.ID_SEMESTRE + " INTEGER NOT NULL);"
    );

    private static final String SQL_DROP_HORARIO = "DROP TABLE IF EXISTS " + HorarioContract.TABELA;
    private static final String SQL_CREATE_TABLE_HORARIO = String.format(
            "CREATE TABLE " + HorarioContract.TABELA + " ( " +
                    HorarioContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HorarioContract.DIA + " TEXT NOT NULL, " +
                    HorarioContract.HORARIO + " TEXT NOT NULL, " +
                    HorarioContract.ID_DISCIPLINA + " INTEGER NOT NULL);"
    );



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DROP_USUARIO);
        db.execSQL(SQL_DROP_SEMESTRE);
        db.execSQL(SQL_DROP_DISCIPLINA);
        db.execSQL(SQL_DROP_PROFESSOR);
        db.execSQL(SQL_DROP_LEMBRETE);
        db.execSQL(SQL_DROP_HORARIO);

        db.execSQL(SQL_CREATE_TABLE_USUARIO);
        db.execSQL(SQL_CREATE_TABLE_SEMESTRE);
        db.execSQL(SQL_CREATE_TABLE_DISCIPLINA);
        db.execSQL(SQL_CREATE_TABLE_PROFESSOR);
        db.execSQL(SQL_CREATE_TABLE_LEMBRETE);
        db.execSQL(SQL_CREATE_TABLE_HORARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }
}
