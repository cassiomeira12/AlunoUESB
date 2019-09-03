package com.example.cassio.alunouesb.dialog;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.cassio.alunouesb.R;

/**
 * Created by cassio on 12/09/17.
 */

public class DialogExcluir extends DialogFragment {

    private OnExcluir onExcluir;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_excluir, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        new ViewHolder(view);

        return view;
    }

    public void setOnExcluir(OnExcluir onExcluir) {
        this.onExcluir = onExcluir;
    }

    public interface OnExcluir {
        void onExcluir();
    }

    public class ViewHolder implements View.OnClickListener {
        final TextView textInformacao;
        final Button buttonSim;
        final Button buttonNao;

        public ViewHolder(View view) {
            textInformacao = view.findViewById(R.id.text_informacao);
            buttonSim = view.findViewById(R.id.button_sim);
            buttonNao = view.findViewById(R.id.button_nao);

            textInformacao.setText(getTag());

            buttonSim.setOnClickListener(this);
            buttonNao.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.button_sim) {
                if (onExcluir != null) {
                    onExcluir.onExcluir();
                }
            }

            dismiss();

        }
    }
}
