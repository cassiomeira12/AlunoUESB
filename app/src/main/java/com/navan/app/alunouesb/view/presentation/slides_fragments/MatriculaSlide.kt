package com.navan.app.alunouesb.view.presentation.slides_fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.navan.app.alunouesb.R
import io.github.dreierf.materialintroscreen.SlideFragment

class MatriculaSlide : SlideFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matricula, container, false)
    }

    override fun backgroundColor(): Int {
        return R.color.slideMatricula
    }

    override fun buttonsColor(): Int {
        return R.color.button_presentation
    }

}