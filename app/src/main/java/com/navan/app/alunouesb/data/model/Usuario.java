package com.navan.app.alunouesb.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cassio on 06/09/17.
 */

public class Usuario implements Serializable {

    private String nome;
    private String email;
    private String senha;
    private String uid;
    private String curso;
    private int matricula;
    private int idSemestre; //Semestre selecionado
    private ArrayList<String> semestreList = new ArrayList<>();
    private String urlPhotoProfile = "";


    //Construtor da Classe
    public Usuario (String nome, String email, String senha, String curso) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.curso = curso;
        this.idSemestre = 0;
        this.semestreList = new ArrayList<>();
        this.urlPhotoProfile = "";
    }
    public Usuario(){}

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

    public ArrayList<String> getSemestreList() {
        return semestreList;
    }

    public void setSemestreList(ArrayList<String> semestreList) {
        this.semestreList = semestreList;
    }

    public int getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uiid) {
        this.uid = uiid;
    }

    public void addSemestre(String semestre){
        semestreList.add(semestre);
    }

    public void removerSemestre(int id){
        semestreList.remove(id);
    }

    public String getSemestre(int id){
        return semestreList.get(id);
    }

    public String getUrlPhotoProfile(){
        return this.urlPhotoProfile;
    }

    public void setUrlPhotoProfile(String url){
        this.urlPhotoProfile = url;
    }
}
