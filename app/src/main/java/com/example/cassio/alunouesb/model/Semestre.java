package com.example.cassio.alunouesb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 06/09/17.
 */

public class Semestre implements Serializable{

    private String semestre;
    private List<Disciplina> disciplinaList;
    private List<Lembrete> lembreteList;


    //Construtor da Classe
    public Semestre(String semestre) {
        this.semestre = semestre;
        this.disciplinaList = new ArrayList<>();
        this.lembreteList = new ArrayList<>();
    }

    public Semestre(){}

    public void adicionarDisciplina(Disciplina disciplina) {
        disciplinaList.add(disciplina);
    }

    public void adicionarLembrete(String titulo, String mensagem){
//        Lembrete lembrete = new Lembrete(iitulo, mensagem);
//        lembreteList.add(lembrete);
    }


    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<Disciplina> getDisciplinaList() {
        return disciplinaList;
    }

    public void setDisciplinaList(List<Disciplina> disciplinaList) {
        this.disciplinaList = disciplinaList;
    }

    public ArrayList<Lembrete> getLembreteList() {
        return (ArrayList<Lembrete>) lembreteList;
    }

    public void setLembreteList(List<Lembrete> lembreteList) {
        this.lembreteList = lembreteList;
    }

    @Override
    public String toString() {
        return semestre;
    }

}
