package com.navan.app.alunouesb.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.data.model.Disciplina;
import com.navan.app.alunouesb.data.model.Horario;
import com.navan.app.alunouesb.data.model.Horarios;
import com.navan.app.alunouesb.data.model.Semestre;

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
        //disciplinaList = (ArrayList<Disciplina>) semestre.getDisciplinaList();

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, configurarListView(disciplinaList));

        //listDisciplinas.setAdapter(adapter);
        //carregarHorarios();
        //carregarListaTipoHorarios();

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
