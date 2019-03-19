package com.example.cassio.alunouesb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 06/09/17.
 */

public class Semestre implements Serializable{

    private Long id;
    private String semestre;
    List<Disciplina> disciplinaList;
    List<Lembrete> lembreteList;
    private Long idUsuario;


    //Construtor da Classe
    public Semestre(Long id, String semestre, Long idUsuario) {
        this.id = id;
        this.semestre = semestre;
        this.disciplinaList = new ArrayList<>();
        this.lembreteList = new ArrayList<>();
        this.idUsuario = idUsuario;
    }

    public void adicionarDisciplina(Long idDisciplina, String nome, String abreviacao, Long idProfessor) {
        Disciplina disciplina = new Disciplina(idDisciplina, nome, abreviacao, idProfessor, this.id);
        disciplinaList.add(disciplina);
    }

    public void adicionarLembrete(Long idLembrete, String titulo, String mensagem, Long data) {
        Lembrete lembrete = new Lembrete(idLembrete, titulo, mensagem, data, this.id);
        lembreteList.add(lembrete);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Lembrete> getLembreteList() {
        return lembreteList;
    }

    public void setLembreteList(List<Lembrete> lembreteList) {
        this.lembreteList = lembreteList;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return semestre;
    }

    @Override
    public boolean equals(Object semestre) {
        return this.id.equals(((Semestre) semestre).getId());
    }
}
