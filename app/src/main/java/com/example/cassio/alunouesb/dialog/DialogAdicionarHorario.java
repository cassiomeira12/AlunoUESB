package com.example.cassio.alunouesb.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.model.Horario;

import java.util.ArrayList;
import java.util.List;

import static com.example.cassio.alunouesb.R.layout.support_simple_spinner_dropdown_item;

/**
 * Created by cassio on 12/09/17.
 */

public class DialogAdicionarHorario extends DialogFragment {


    private OnClickDialog onClickDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_adicionar_horario, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        new ViewHolder(view);

        return view;
    }

    public void setOnClickDialog(OnClickDialog onClickDialog) {
        this.onClickDialog = onClickDialog;
    }

    public interface OnClickDialog {
        void onClickDialog(ViewHolder view);
    }

    public class ViewHolder implements View.OnClickListener {
        public Spinner spinnerDia;
        public Spinner spinnerHorario;
        final Button salvar;
        final Button cancelar;

        private List<String> dias = new ArrayList<>();
        private List<String> horarios = new ArrayList<>();

        public ViewHolder(View view) {
            spinnerDia = view.findViewById(R.id.spinner_dia);
            spinnerHorario =  view.findViewById(R.id.spinner_horario);
            salvar = view.findViewById(R.id.button_salvar);
            cancelar = view.findViewById(R.id.button_cancelar);


            dias.add("Segunda");
            dias.add("Terça");
            dias.add("Quarta");
            dias.add("Quinta");
            dias.add("Sexta");
            dias.add("Sábado");

            horarios.add("1º Horário");
            horarios.add("2º Horário");
            horarios.add("3º Horário");


            ArrayAdapter<String> adapterDia = new ArrayAdapter<String> (getContext(), R.layout.support_simple_spinner_dropdown_item, dias);

            ArrayAdapter<String> adapterHorario = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, horarios);

            spinnerDia.setAdapter(adapterDia);
            spinnerHorario.setAdapter(adapterHorario);


            salvar.setOnClickListener(this);
            cancelar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.button_salvar) {
                if (onClickDialog != null) {
                    onClickDialog.onClickDialog(this);
                }
            }

            dismiss();

        }


    }
}
