package com.example.cassio.alunouesb.dialog;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.cassio.alunouesb.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cassio on 12/09/17.
 */

public class DialogAdicionarHorario extends DialogFragment implements  RadioGroup.OnCheckedChangeListener{

    private RadioButton manha, tarde, noite;
    private OnClickDialog onClickDialog;
    private RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_adicionar_horario, container);

        radioGroup = view.findViewById(R.id.radio_group);
        manha = view.findViewById(R.id.radio_button_manha);
        tarde = view.findViewById(R.id.radio_button_tarde);
        noite = view.findViewById(R.id.radio_button_noite);


        radioGroup.setOnCheckedChangeListener(this);


        new ViewHolder(view);

        return view;
    }

    public void setOnClickDialog(OnClickDialog onClickDialog) {
        this.onClickDialog = onClickDialog;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Resources res = getResources();
        try {
            switch (i){
                case R.id.radio_button_manha:
                    manha.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.selected_radio_background)));
                    manha.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.WHITE)));
                    manha.setTextColor(ColorStateList.valueOf(res.getColor(R.color.WHITE)));

                    tarde.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.unselected_radio_background)));
                    tarde.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.colorPrimary)));
                    tarde.setTextColor(ColorStateList.valueOf(res.getColor(R.color.BLACK)));

                    noite.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.unselected_radio_background)));
                    noite.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.colorPrimary)));
                    noite.setTextColor(ColorStateList.valueOf(res.getColor(R.color.BLACK)));


                    break;
                case R.id.radio_button_tarde:

                    tarde.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.selected_radio_background)));
                    tarde.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.WHITE)));
                    tarde.setTextColor(ColorStateList.valueOf(res.getColor(R.color.WHITE)));

                    manha.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.unselected_radio_background)));
                    manha.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.colorPrimary)));
                    manha.setTextColor(ColorStateList.valueOf(res.getColor(R.color.BLACK)));

                    noite.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.unselected_radio_background)));
                    noite.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.colorPrimary)));
                    noite.setTextColor(ColorStateList.valueOf(res.getColor(R.color.BLACK)));


                    break;
                case R.id.radio_button_noite:

                    noite.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.selected_radio_background)));
                    noite.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.WHITE)));
                    noite.setTextColor(ColorStateList.valueOf(res.getColor(R.color.WHITE)));

                    manha.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.unselected_radio_background)));
                    manha.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.colorPrimary)));
                    manha.setTextColor(ColorStateList.valueOf(res.getColor(R.color.BLACK)));

                    tarde.setBackground(Drawable.createFromXml(res, res.getXml(R.drawable.unselected_radio_background)));
                    tarde.setButtonTintList(ColorStateList.valueOf(res.getColor(R.color.colorPrimary)));
                    tarde.setTextColor(ColorStateList.valueOf(res.getColor(R.color.BLACK)));

                    break;
            }
        } catch (XmlPullParserException e) {
            Log.e("Erro", "Erro na busca pelo XML do check");
        } catch (IOException e) {
            Log.e("Erro", "Erro na busca pelo XML do check");
        }
    }

    public interface OnClickDialog {
        void onClickDialog(ViewHolder view);
    }

    public class ViewHolder implements View.OnClickListener {
        public Spinner spinnerDia;
        public Spinner spinnerHorario;
        public RadioGroup radioGroup;
        final Button salvar;
        final Button cancelar;

        private List<String> dias = new ArrayList<>();
        private List<String> horarios = new ArrayList<>();

        public ViewHolder(View view) {
            spinnerDia = view.findViewById(R.id.spinner_dia);
            spinnerHorario =  view.findViewById(R.id.spinner_horario);
            salvar = view.findViewById(R.id.button_salvar);
            cancelar = view.findViewById(R.id.button_cancelar);
            radioGroup = view.findViewById(R.id.radio_group);


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
