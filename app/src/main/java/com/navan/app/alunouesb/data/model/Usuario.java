package com.navan.app.alunouesb.data.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cassio on 06/09/17.
 */

public class Usuario extends BaseUser implements Serializable {
    private String curso;
    private int matricula;
    private int idSemestre; //Semestre selecionado
    private ArrayList<String> semestreList = new ArrayList<>();

    //Construtor da Classe
    public Usuario (String curso, int matricula, String semestre) {
        this.curso = curso;
        this.matricula = matricula;
        this.idSemestre = 0;
        this.semestreList = new ArrayList<>();
        addSemestre(semestre);
    }

    public Usuario() {

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


    public void addSemestre(String semestre){
        semestreList.add(semestre);
    }

    public void removerSemestre(int id){
        semestreList.remove(id);
    }

    public String getSemestre(int id){
        return semestreList.get(id);
    }

    public void setUsuario(Usuario user){

        this.curso = user.getCurso();
        this.matricula = user.getMatricula();
        this.semestreList = user.getSemestreList();
        this.idSemestre = user.getIdSemestre();
        this.uID = user.uID;
        this.status = user.status;
        this.name = user.name;
        this.email = user.email;
        this.emailVerified = user.emailVerified;
        this.createAt = user.createAt;
        this.updateAt = user.updateAt;

    }

}
