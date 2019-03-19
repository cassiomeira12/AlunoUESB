package com.example.cassio.alunouesb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 04/09/17.
 */

public class Disciplina implements Serializable {

    private Long id;
    private String nome;
    private String abreviacao;
    private Long idProfessor;
    private Long idSemestre;
    private List<Horario> horarioList;
    private float unidade1 = 0;
    private float unidade2 = 0;
    private float unidade3 = 0;
    private float notaFinal = 0;
    private float media = 0;


    //Construtor da Classe
    public Disciplina(Long id, String nome, String abreviacao, Long idProfessor, Long idSemestre) {
        this.id = id;
        this.nome = nome;
        this.abreviacao = abreviacao;
        this.idProfessor = idProfessor;
        this.idSemestre = idSemestre;
        this.horarioList = new ArrayList<>();
    }

    public void adicionarHorario(Long idHorario, int dia, int horario) {
        Horario horarioTemp = new Horario(idHorario, dia, horario, this.id);
        horarioList.add(horarioTemp);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }

    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }

    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    public float getUnidade1() {
        return unidade1;
    }

    public void setUnidade1(float unidade1) {
        this.unidade1 = unidade1;
    }

    public float getUnidade2() {
        return unidade2;
    }

    public void setUnidade2(float unidade2) {
        this.unidade2 = unidade2;
    }

    public float getUnidade3() {
        return unidade3;
    }

    public void setUnidade3(float unidade3) {
        this.unidade3 = unidade3;
    }

    public float getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    @Override
    public boolean equals(Object disciplina) {
        return this.id.equals(((Disciplina) disciplina).getId());
    }
}
