package com.example.cassio.alunouesb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cassio.alunouesb.R;
import com.example.cassio.alunouesb.activity.DisciplinasActivity;
import com.example.cassio.alunouesb.model.Disciplina;

import java.util.List;

/**
 * Created by cassio on 09/09/17.
 */

public class AdapterDisciplina extends Adapter {

    private List<Disciplina> disciplinaList;

    private Context context;
    private OnClick onClick;
    private OnLongClick onLongClick;

    //Construtor da Classe
    public AdapterDisciplina(List<Disciplina> disciplinaList, Context context, OnClick onClick, OnLongClick onLongClick) {
        this.disciplinaList = disciplinaList;
        this.context = context;
        this.onClick = onClick;
        this.onLongClick = onLongClick;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_disciplina, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        Disciplina disciplina = disciplinaList.get(position);

        viewHolder.textNome.setText(disciplina.getAbreviacao());
        viewHolder.textUnidade1.setText(String.valueOf(disciplina.getUnidade1()));
        viewHolder.textUnidade2.setText(String.valueOf(disciplina.getUnidade2()));
        viewHolder.textUnidade3.setText(String.valueOf(disciplina.getUnidade3()));
        viewHolder.textMedia.setText(String.valueOf(disciplina.getMedia()));

        viewHolder.disciplinaItem.setTag(position);

        if (((DisciplinasActivity) context).listaExclusao.contains(disciplina)) {
            ((ViewHolder) holder).disciplinaItem.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            ((ViewHolder) holder).disciplinaItem.setBackgroundResource(R.drawable.shape_cinza);
        }
    }

    @Override
    public int getItemCount() {
        if (disciplinaList == null) {
            return 0;
        } else {
            return disciplinaList.size();
        }
    }

    @Override
    public void onClick(View view) {
        this.onClick.onClick(view);
    }

    @Override
    public boolean onLongClick(View view) {
        this.onLongClick.onLongClick(view);
        return true;
    }

    @Override
    public void addItem(Object item){
        disciplinaList.add((Disciplina) item);
        notifyDataSetChanged();
        super.addItem(item);
    }

    @Override
    public void removeItem(Object item) {
        disciplinaList.remove((Disciplina) item);
        notifyDataSetChanged();
        super.removeItem(item);
    }

    @Override
    public Object getItem(int position) {
        if (disciplinaList == null || disciplinaList.isEmpty()) {
            return null;
        }
        return disciplinaList.get(position);
    }

    @Override
    public void update(Object item) {
        Disciplina disciplina = (Disciplina) item;

        disciplinaList.get(disciplinaList.indexOf(disciplina)).setNome(disciplina.getNome());
        disciplinaList.get(disciplinaList.indexOf(disciplina)).setAbreviacao(disciplina.getAbreviacao());
        disciplinaList.get(disciplinaList.indexOf(disciplina)).setIdProfessor(disciplina.getIdProfessor());

        disciplinaList.get(disciplinaList.indexOf(disciplina)).setUnidade1(disciplina.getUnidade1());
        disciplinaList.get(disciplinaList.indexOf(disciplina)).setUnidade2(disciplina.getUnidade2());
        disciplinaList.get(disciplinaList.indexOf(disciplina)).setUnidade3(disciplina.getUnidade3());
        disciplinaList.get(disciplinaList.indexOf(disciplina)).setNotaFinal(disciplina.getNotaFinal());
        disciplinaList.get(disciplinaList.indexOf(disciplina)).setMedia(disciplina.getMedia());

        notifyDataSetChanged();
        super.update(item);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout disciplinaItem;

        final TextView textNome;
        final TextView textUnidade1;
        final TextView textUnidade2;
        final TextView textUnidade3;
        final TextView textMedia;


        public ViewHolder(View view) {
            super(view);
            disciplinaItem = (LinearLayout) view.findViewById(R.id.disciplina_item);

            textNome = (TextView) view.findViewById(R.id.text_disciplina_nome);
            textUnidade1 = (TextView) view.findViewById(R.id.text_uni1_item);
            textUnidade2 = (TextView) view.findViewById(R.id.text_uni2_item);
            textUnidade3 = (TextView) view.findViewById(R.id.text_uni3_item);
            textMedia = (TextView) view.findViewById(R.id.text_media_item);

            disciplinaItem.setOnClickListener(AdapterDisciplina.this);
            disciplinaItem.setOnLongClickListener(AdapterDisciplina.this);
        }

    }

    public interface OnClick {
        void onClick(View view);
    }

    public interface OnLongClick {
        void onLongClick(View view);
    }
}
