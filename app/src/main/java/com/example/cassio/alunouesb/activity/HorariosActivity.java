package com.example.cassio.alunouesb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.model.Disciplina;
import com.example.cassio.alunouesb.model.Horario;
import com.example.cassio.alunouesb.model.Horarios;
import com.example.cassio.alunouesb.model.Semestre;
import com.example.cassio.alunouesb.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class HorariosActivity extends AppCompatActivity {

    private Semestre semestre =  PrincipalActivity.semestre;
    private ListView listDisciplinas;
    private ListView listarHorarios;
    private ArrayList<Disciplina> disciplinaList;
    private ArrayList<Horarios> mlistaHorarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
        setTitle("Meus Horários");

        listarHorarios = findViewById(R.id.listar_horarios);
        listDisciplinas = findViewById(R.id.list_disciplinas);
        disciplinaList = (ArrayList<Disciplina>) semestre.getDisciplinaList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, configurarListView(disciplinaList));

        listDisciplinas.setAdapter(adapter);
        carregarHorarios();
        carregarListaTipoHorarios();

    }

    public void carregarListaTipoHorarios(){
        for (Disciplina disciplina : disciplinaList) {
            for(Horario horario: disciplina.getHorarioList()){
                Horarios novo = new Horarios(horario.getTurno(), horario.getDia(), horario.getHorario(), disciplina.getAbreviacao());
                mlistaHorarios.add(novo);
            }
        }

        for (Horarios horario: mlistaHorarios) {
            Log.e("Teste ", "Turno: " + horario.getTurno() + "  Dia: " + horario.getTurno() + "  Horario: " + horario.getHorario() + "  Abrev: " + horario.getAbreviacao());
        }
    }

    public void carregarHorarios(){

    }

    private List<String> configurarListView(List<Disciplina> lista) {
        List<String> resultado = new ArrayList<>();

        for (Disciplina disciplina : lista) {
            resultado.add(disciplina.getAbreviacao() + " - " + disciplina.getNome());
        }

        return resultado;
    }


}
