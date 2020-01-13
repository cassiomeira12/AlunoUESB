package com.navan.app.alunouesb.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navan.app.alunouesb.view.activity.LembretesActivity;
import com.navan.app.alunouesb.R;
import com.navan.app.alunouesb.data.model.Lembrete;

import java.util.List;


/**
 * Created by cassio on 16/08/17.
 */

public class AdapterLembretes extends Adapter {

    private List<Lembrete> lembreteList;

    private Context context;
    private OnClick onClick;
    private OnLongClick onLongClick;

    //Construtor da Classe
    public AdapterLembretes(List<Lembrete> lembreteList, Context context, OnClick onClick, OnLongClick onLongClick) {
        this.lembreteList = lembreteList;
        this.context = context;
        this.onClick = onClick;
        this.onLongClick = onLongClick;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_lembretes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        Lembrete lembrete = lembreteList.get(position);

        String data = "";

//        if (DateUtils.getMinutosPassado(lembrete.getData()) < 2) {
//            data = "Agora mesmo";
//        } else if (DateUtils.getMinutosPassado(lembrete.getData()) < 60) {
//            data = "Há " + DateUtils.getMinutosPassado(lembrete.getData()) + " minutos atrás";
//        } else if (DateUtils.getMinutosPassado(lembrete.getData()) < (24*60)) {
//            data = "Ontem às " + DateUtils.getHoraFromDate(lembrete.getData());
//        } else {
//            data = DateUtils.formatDateExtenso(lembrete.getData());
//        }

        viewHolder.txtTitulo.setText(lembrete.getTitulo());
        viewHolder.txtMensagem.setText(lembrete.getMensagem());
        viewHolder.txtData.setText(data);

        viewHolder.lembreteItem.setTag(position);

        if (((LembretesActivity) context).listaExclusao.contains(lembrete)) {
            ((ViewHolder) holder).lembreteItem.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            ((ViewHolder) holder).lembreteItem.setBackgroundResource(R.drawable.shape_cinza);
        }
    }

    @Override
    public int getItemCount() {
        if (lembreteList == null) {
            return 0;
        } else {
            return lembreteList.size();
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
    public void addItem(Object item) {
        this.lembreteList.add(0, (Lembrete) item);
        notifyDataSetChanged();
        super.addItem(item);
    }

    @Override
    public void removeItem(Object item) {
        this.lembreteList.remove((Lembrete) item);
        notifyDataSetChanged();
        super.removeItem(item);
    }

    @Override
    public Object getItem(int position) {
        if (lembreteList == null || lembreteList.isEmpty()) {
            return null;
        }
        return lembreteList.get(position);
    }

    @Override
    public void update(Object item) {
        Lembrete lembrete = (Lembrete) item;

        lembreteList.get(lembreteList.indexOf(lembrete)).setTitulo(lembrete.getTitulo());
        lembreteList.get(lembreteList.indexOf(lembrete)).setMensagem(lembrete.getMensagem());
//        lembreteList.get(lembreteList.indexOf(lembrete)).setData(lembrete.getData());

        notifyDataSetChanged();
        super.update(item);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout lembreteItem;

        final TextView txtTitulo;
        final TextView txtMensagem;
        final TextView txtData;

        public ViewHolder(View view) {
            super(view);
            lembreteItem = (LinearLayout) view.findViewById(R.id.lembrete_item);

            txtTitulo = (TextView) view.findViewById(R.id.text_titulo_lembrete_item);
            txtMensagem = (TextView) view.findViewById(R.id.text_mensagem_lembrete_item);
            txtData = (TextView) view.findViewById(R.id.text_data_lembrete_item);

            lembreteItem.setOnClickListener(AdapterLembretes.this);
            lembreteItem.setOnLongClickListener(AdapterLembretes.this);
        }

    }

    public interface OnClick {
        void onClick(View view);
    }

    public interface OnLongClick {
        void onLongClick(View view);
    }


}