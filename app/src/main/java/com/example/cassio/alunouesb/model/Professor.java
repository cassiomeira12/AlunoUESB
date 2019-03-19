package com.example.cassio.alunouesb.model;

import java.io.Serializable;

/**
 * Created by cassio on 27/09/17.
 */

public class Professor implements Serializable {

    private Long id;
    private String nome;
    private String email;


    //Construtor da Classe
    public Professor (Long id, String nome) {
        this.id = id;
        this.nome = nome;
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

    @Override
    public boolean equals(Object professor) {
        return this.id.equals(((Professor) professor).getId());
    }
}
