package com.example.cassio.alunouesb.model;

import java.io.Serializable;

/**
 * Created by cassio on 27/09/17.
 */

public class Professor implements Serializable {

    private String nome;
    private String email;


    //Construtor da Classe
    public Professor (String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Professor(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
