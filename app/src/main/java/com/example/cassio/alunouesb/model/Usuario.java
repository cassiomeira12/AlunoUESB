package com.example.cassio.alunouesb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 06/09/17.
 */

public class Usuario implements Serializable{

    private Long id;
    private String nome;
    private String curso;
    private int matricula;
    private Long idSemestre;//Semestre selecionado
    private List<Semestre> semestreList;


    //Construtor da Classe
    public Usuario (Long id, String nome, String curso) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.semestreList = new ArrayList<>();
    }

    public void adiconarSemestre(Long idSemestre, String semestre) {
        Semestre semestreTemp = new Semestre(idSemestre, semestre, this.id);
        semestreList.add(semestreTemp);
    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public List<Semestre> getSemestreList() {
        return semestreList;
    }

    public void setSemestreList(List<Semestre> semestreList) {
        this.semestreList = semestreList;
    }

    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }
}
