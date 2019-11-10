package com.example.cassio.alunouesb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.cassio.alunouesb.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class AdapterHorarios extends ArrayAdapter<Disciplina> {
    private ArrayList<Disciplina> horarios;

    public AdapterHorarios(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
        this.horarios = (ArrayList<Disciplina>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {





        return super.getView(position, convertView, parent);
    }
}
