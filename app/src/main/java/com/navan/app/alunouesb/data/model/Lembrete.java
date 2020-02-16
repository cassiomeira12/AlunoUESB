package com.navan.app.alunouesb.data.model;

import java.io.Serializable;

/**
 * Created by cassio on 02/09/17.
 */

public class Lembrete implements Serializable {

    private String id;
    private String titulo;
    private String mensagem;

    //Construtor da Classe
    public Lembrete(String titulo, String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public Lembrete() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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


}
