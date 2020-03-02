package com.navan.app.alunouesb.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.model.Lembrete

class AdapterLembretes2(itensList: MutableList<Lembrete>, context: Context, actions: Actions): Adapter2<Lembrete>(itensList, context, actions) {
    private val TAG = javaClass.simpleName
    private val layoutID = R.layout.list_item_lembretes //Id do item layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)

        /*
        viewHolder.txtTitulo.setText(lembrete.getTitulo());
        viewHolder.txtMensagem.setText(lembrete.getMensagem());
        viewHolder.txtData.setText(data);

        viewHolder.lembreteItem.setTag(position);

        if (((LembretesActivity) context).listaExclusao.contains(lembrete)) {
            ((ViewHolder) holder).lembreteItem.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            ((ViewHolder) holder).lembreteItem.setBackgroundResource(R.drawable.shape_cinza);
        }
         */

        viewHolder.txtTitulo.setText(item.titulo)
        viewHolder.txtMensagem.setText(item.mensagem)
        viewHolder.txtData.setText("")

        if (itensSelected.contains(item)) {
            viewHolder.layout.setBackgroundResource(R.color.colorAccent)
        } else {
            viewHolder.layout.setBackgroundResource(android.R.color.white)
        }

        viewHolder.layout.setTag(position)
    }

    override fun update(item: Lembrete?): Boolean {
        return false
    }

    override fun searchValue(item: Lembrete?): String {
        return ""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout

        val txtTitulo: TextView
        val txtMensagem: TextView
        val txtData: TextView

        init {
            layout = itemView.findViewById(R.id.lembrete_item)


            txtTitulo = itemView.findViewById(R.id.text_titulo_lembrete_item)
            txtMensagem = itemView.findViewById(R.id.text_mensagem_lembrete_item)
            txtData = itemView.findViewById(R.id.text_data_lembrete_item)

            layout.setOnClickListener(this@AdapterLembretes2)
            layout.setOnLongClickListener(this@AdapterLembretes2)
        }

    }

}