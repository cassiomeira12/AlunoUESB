package com.example.cassio.alunouesb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class AdicionarHorarioActivity extends AppCompatActivity {

    private Disciplina disciplina;

    private Spinner spinnerDia;
    private Spinner spinnerHorario;

    private List<String> dias = new ArrayList<>();

    private List<String> horarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_horario);

        this.disciplina = (Disciplina) getIntent().getSerializableExtra("disciplina");

        dias.add("Segunda");
        dias.add("Terça");
        dias.add("Quarta");
        dias.add("Quinta");
        dias.add("Sexta");
        dias.add("Sabado");

        horarios.add("1º Horário");
        horarios.add("2º Horário");
        horarios.add("3º Horário");

        spinnerDia = findViewById(R.id.spinner_dia);
        spinnerHorario = findViewById(R.id.spinner_horario);


        ArrayAdapter<String> adapterDia = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, dias);
        ArrayAdapter<String> adapterHorario = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, horarios);

        spinnerDia.setAdapter(adapterDia);
        spinnerHorario.setAdapter(adapterHorario);

    }
}
