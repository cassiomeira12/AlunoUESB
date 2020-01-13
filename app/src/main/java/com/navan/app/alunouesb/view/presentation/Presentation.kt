package com.navan.app.alunouesb.view.presentation

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.core.view.get
import androidx.core.view.size
import com.google.android.gms.common.server.converter.StringToIntConverter
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.Usuario
import com.navan.app.alunouesb.data.shared_preferences.SPInfo
import com.navan.app.alunouesb.view.activity.PrincipalActivity
import com.navan.app.alunouesb.view.presentation.slides_fragments.AddDisciplinasSlide
import com.navan.app.alunouesb.view.presentation.slides_fragments.CursoSlide
import com.navan.app.alunouesb.view.presentation.slides_fragments.MatriculaSlide
import com.navan.app.alunouesb.view.presentation.slides_fragments.SemestreSlide
import io.github.dreierf.materialintroscreen.MaterialIntroActivity
import kotlinx.android.synthetic.main.fragment_curso.view.*

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
        if(SPInfo(this).isIntroShown()){
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


        //usuario
        val user = Usuario(curso, matricula, semestre)
        user.setUser(UserSingleton.instance) // Baser User

        CompleteUserSingleton.instance.setUsuario(user) // Usuario completo


        SPInfo(this).updateIntroStatus(true)
        val intent = Intent(this, PrincipalActivity::class.java)

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        this.startActivity(intent)
        super.onFinish()
    }

}