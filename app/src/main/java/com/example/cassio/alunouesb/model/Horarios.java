package com.example.cassio.alunouesb.model;

public class Horarios {
    private int turno;
    private int horario;
    private int dia;
    private String abreviacao;

    public Horarios(int turno, int dia, int horario, String abreviacao){
        this.turno = turno;
        this.dia = dia;
        this.horario = horario;
        this.abreviacao = abreviacao;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
