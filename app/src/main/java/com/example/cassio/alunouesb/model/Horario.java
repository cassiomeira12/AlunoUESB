package com.example.cassio.alunouesb.model;

import java.io.Serializable;

/**
 * Created by cassio on 05/09/17.
 */

public class Horario implements Serializable {

    private Long id;
    private int dia;
    private int horario;
    private Long idDisciplina;

    //Construtor da classe
    public Horario (Long id, int dia, int horario, Long idDisciplina) {
        this.id = id;
        this.dia = dia;
        this.horario = horario;
        this.idDisciplina = idDisciplina;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
    public String toString() {
        String resultado = "";

        switch (this.dia) {
            case 0:
                resultado = "Segunda\t";
                break;

            case 1:
                resultado = "Terça\t";
                break;

            case 2:
                resultado = "Quarta\t";
                break;

            case 3:
                resultado = "Quinta\t";
                break;

            case 4:
                resultado = "Sexta\t";
                break;

            case 5:
                resultado = "Sábado\t";
                break;
        }

        switch (this.horario) {
            case 0:
                resultado += "1º Horário";
                break;

            case 1:
                resultado += "2º Horário";
                break;

            case 2:
                resultado += "3º Horário";
                break;
        }

        return resultado;
    }
}
