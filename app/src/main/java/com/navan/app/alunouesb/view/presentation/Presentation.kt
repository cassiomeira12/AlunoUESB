package com.navan.app.alunouesb.view.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.Usuario
import com.navan.app.alunouesb.data.shared_preferences.SPInfo
import com.navan.app.alunouesb.presenter.login.CreateCompleteAccountPresenter
import com.navan.app.alunouesb.view.activity.PrincipalActivity
import com.navan.app.alunouesb.view.login.CreateCompleteAccountActivity
import com.navan.app.alunouesb.view.presentation.slides_fragments.CursoSlide
import com.navan.app.alunouesb.view.presentation.slides_fragments.MatriculaSlide
import com.navan.app.alunouesb.view.presentation.slides_fragments.SemestreSlide
import io.github.dreierf.materialintroscreen.MaterialIntroActivity
import java.sql.DriverManager

class Presentation : MaterialIntroActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        verifyPresentationActivity()

        // adicionar slides


        //CURSO
        addSlide( CursoSlide() )

        //MATRICULA
        addSlide( MatriculaSlide() )

        addSlide( SemestreSlide() )

        //addSlide( AddDisciplinasSlide() )
    }


    // Verifica no SharedPreferences se a tela de Presentation ja foi vista
    private fun verifyPresentationActivity(){
        if(CompleteUserSingleton.instance.matricula != 0){
            startActivity( Intent(this, PrincipalActivity::class.java))
            finish()
        }
    }

    // ao finilizar a Presentation, pegar os dados inseridos e criar o usuario
    override fun onFinish() {

        // curso selecionado
        val cursos: RadioGroup = findViewById(R.id.radioGroupCurso)
        val id = cursos.checkedRadioButtonId
        val radioButtonCurso = cursos.get(id - 1) as RadioButton
        val curso = radioButtonCurso.text.toString()


        // matricula
        val editTextMatricula: EditText = findViewById(R.id.editTextMatricula)
        val matricula = editTextMatricula.text.toString().toInt()


        // semestre
        val editTextSemestre: EditText = findViewById(R.id.editTextSemestre)
        val semestre = editTextSemestre.text.toString()

        Log.e("DADOS", "Curso: " + curso + "   Matricula: " + matricula + "    Semestre: " + semestre)

        CompleteUserSingleton.instance.curso = curso // Usuario completo
        CompleteUserSingleton.instance.matricula = matricula
        CompleteUserSingleton.instance.addSemestre(semestre)


        // tela de carregamento

        if(isDataValid()){
            if(CompleteUserSingleton.instance.matricula != 0){
                startActivity(Intent(getApplicationContext(), CreateCompleteAccountActivity::class.java))
                finish()
            }

        }



    }


    fun isDataValid(): Boolean {
        // verifica os dados inseridos

        return true
    }

}