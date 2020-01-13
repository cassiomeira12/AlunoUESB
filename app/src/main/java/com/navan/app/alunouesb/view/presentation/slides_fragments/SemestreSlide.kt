package com.navan.app.alunouesb.view.presentation.slides_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.navan.app.alunouesb.R
import io.github.dreierf.materialintroscreen.SlideFragment

class SemestreSlide : SlideFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_semestre, container, false)
    }

    override fun backgroundColor(): Int {
        return R.color.slideSemestre
    }

    override fun buttonsColor(): Int {
        return R.color.button_presentation
    }
}