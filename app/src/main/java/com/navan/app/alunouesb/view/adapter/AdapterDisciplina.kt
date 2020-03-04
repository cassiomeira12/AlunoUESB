package com.navan.app.alunouesb.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.model.Disciplina

class AdapterDisciplina (itensList: MutableList<Disciplina>, context: Context, actions: Actions): Adapter2<Disciplina>(itensList, context, actions) {
    private val layoutID = R.layout.list_item_disciplina

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)

        viewHolder.txtAbrev.setText(item.abreviacao)
        viewHolder.txtUnit1.setText(item.unidade1.toString())
        viewHolder.txtUnit2.setText(item.unidade2.toString())
        viewHolder.txtUnit3.setText(item.unidade3.toString())
        viewHolder.txtMedia.setText(item.media.toString())

        if (itensSelected.contains(item)) {
            viewHolder.layout.setBackgroundResource(R.color.colorAccent)
        } else {
            viewHolder.layout.setBackgroundResource(android.R.color.white)
        }

        viewHolder.layout.setTag(position)
    }

    override fun update(item: Disciplina?): Boolean {
        return false
    }

    override fun searchValue(item: Disciplina?): String {
        return ""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout

        val txtAbrev: TextView
        val txtUnit1: TextView
        val txtUnit2: TextView
        val txtUnit3: TextView
        val txtMedia: TextView

        init {
            layout = itemView.findViewById(R.id.disciplina_item)

            txtAbrev = itemView.findViewById(R.id.text_disciplina_nome)
            txtUnit1 = itemView.findViewById(R.id.text_uni1_item)
            txtUnit2 = itemView.findViewById(R.id.text_uni2_item)
            txtUnit3 = itemView.findViewById(R.id.text_uni3_item)
            txtMedia = itemView.findViewById(R.id.text_media_item)

            layout.setOnClickListener(this@AdapterDisciplina)
            layout.setOnLongClickListener(this@AdapterDisciplina)
        }

    }

}