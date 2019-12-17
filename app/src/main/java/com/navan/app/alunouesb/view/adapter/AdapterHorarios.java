package com.navan.app.alunouesb.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.navan.app.alunouesb.data.model.Disciplina;

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
