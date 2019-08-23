package com.example.cassio.alunouesb.model;

import java.io.Serializable;

/**
 * Created by cassio on 05/09/17.
 */

public class Horario implements Serializable {

    private int dia;
    private int horario;

    //Construtor da classe
    public Horario (int dia, int horario) {
        this.dia = dia;
        this.horario = horario;
    }
    public Horario(){}


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
