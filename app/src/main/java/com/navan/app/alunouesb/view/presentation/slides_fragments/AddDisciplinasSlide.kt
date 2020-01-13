package com.navan.app.alunouesb.view.presentation.slides_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.navan.app.alunouesb.R
import io.github.dreierf.materialintroscreen.SlideFragment

class AddDisciplinasSlide : SlideFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_disciplinas, container, false)

        var buttonAddDisciplina : Button = view.findViewById(R.id.buttonAdicionarDisciplina)
        var listView : ListView = view.findViewById(R.id.listViewAddDisciplinas)
        val editText : EditText = view.findViewById(R.id.editTextAdicionarDisciplina)

        var disciplinas = emptyArray<String>()

        lateinit var adapter: ArrayAdapter<String>

        buttonAddDisciplina.setOnClickListener {
            val novaDisciplina = editText.text.toString()

            disciplinas = disciplinas.plus(novaDisciplina)

            disciplinas.plus(novaDisciplina)

            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, disciplinas)
            listView.adapter = adapter

            editText.setText("")

        }

        return view
    }

    override fun backgroundColor(): Int {
        return R.color.slideDisciplinas
    }

    override fun buttonsColor(): Int {
        return R.color.button_presentation
    }
}