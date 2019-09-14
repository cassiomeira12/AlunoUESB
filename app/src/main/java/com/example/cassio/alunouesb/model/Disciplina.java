package com.example.cassio.alunouesb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 04/09/17.
 */

public class Disciplina implements Serializable {

    private String nome;
    private String abreviacao;
    private Professor professor;
    private ArrayList<Horario> horarioList;
    private float unidade1 = 0;
    private float unidade2 = 0;
    private float unidade3 = 0;
    private float notaFinal = 0;
    private float media = 0;
    private int faltas = 0;


    //Construtor da Classe
    public Disciplina(String nome, String abreviacao, Professor professor) {
        this.nome = nome;
        this.abreviacao = abreviacao;
        this.professor = professor;
        this.horarioList = new ArrayList<>();
    }
    public Disciplina(){}

    public void adicionarHorario(int turno, int dia, int horario) {
        Horario horarioTemp = new Horario(turno, dia, horario);
        horarioList.add(horarioTemp);
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
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

    public ArrayList<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(ArrayList<Horario> horarioList) {
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

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
