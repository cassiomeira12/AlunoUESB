package com.example.cassio.alunouesb.model;

import java.io.Serializable;

/**
 * Created by cassio on 02/09/17.
 */

public class Lembrete implements Serializable{

    private Long id;
    private String titulo;
    private String mensagem;
    private Long data;
    private Long idSemestre;


    //Construtor da Classe
    public Lembrete(Long id, String titulo, String mensagem, Long data) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.data = data;
        this.idSemestre = idSemestre;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }

    @Override
    public boolean equals(Object lembrete) {
        return this.id.equals(((Lembrete) lembrete).getId());
    }

}
