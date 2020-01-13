package com.navan.app.alunouesb.view.presentation.slides_fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.navan.app.alunouesb.R
import io.github.dreierf.materialintroscreen.SlideFragment

class CursoSlide : SlideFragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.fragment_curso, container, false)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupCurso)

        var cursos = arrayOf<String?>("Administração", "Agronomia", "Biologia", "Cinema", "Ciências Socias", "Ciência da Computação",
                "Contabilidade", "Direito", "Economia", "Educação Física", "Engenharia Florestal", "Filosofia",
                "Física", "Geografia", "História", "Matemática", "Medicina", "Pedagogia", "Psicologia")

        radioGroup.removeAllViews()
        for (curso in cursos){
            val radioButton = RadioButton(requireContext())
            radioButton.text = curso

            radioGroup.addView(radioButton)
        }

        radioGroup.clearCheck()


        return view

    }

    override fun backgroundColor(): Int {
        return R.color.slideCurso
    }

    override fun buttonsColor(): Int {
        return R.color.button_presentation
    }
}

