package com.navan.app.alunouesb.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android.app.contract.IUser
import com.android.app.presenter.login.UserPresenter
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.Usuario
import com.navan.app.alunouesb.view.activity.PrincipalActivity
import com.navan.app.alunouesb.view.login.ContinueUserDataActivity
import com.navan.app.alunouesb.view.login.LoginActivity
import com.navan.app.alunouesb.view.login.VerifiedEmailActivity
import com.navan.app.alunouesb.view.presentation.Presentation


class SplashActivity : AppCompatActivity(), IUser.View {
    internal lateinit var iPresenter: IUser.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //teste tela Presentation
        //SPInfo(this).updateIntroStatus(false)

        iPresenter = UserPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed(Runnable {
            iPresenter.currentUser(this)
        }, 1000)
    }

    override fun onDestroy() {
        iPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onResult(user: Usuario?) {
        if (user == null) {
            startActivity(Intent(getApplication(), LoginActivity::class.java))
            finish()
        } else {

            println("DADOOOOOS")
            println("Curso: " + user.curso)
            println("Matricula: " + user.matricula)
            println("Semestre list: " + user.semestreList.toString())
            println("Id semestre: " + user.idSemestre)
            println("uID: " + user.uID)
            println("Status: " + user.status)
            println("Id semestre: " + user.idSemestre)
            println("nome: " + user.name)
            println("email: " + user.email)
            println("EmailVirified: " + user.emailVerified)
            println("creatAt: " + user.createAt)
            println("updateAt: " + user.updateAt)


            CompleteUserSingleton.instance.setUsuario(user)
            if(user.matricula != 0){ // se for !0 de zero, usuario esta comopleto e ja foi verificado o email
                // usuario completo
                navigateToMainActivity()
                return
            }

            if (user.emailVerified) {//Verificacao de email

                navigateToPresentationActivity()
            } else {
                navigateToVerifiedEmail()
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(getApplication(), PrincipalActivity::class.java))
        finish()
    }

    private fun navigateToPresentationActivity(){
        startActivity(Intent(getApplication(), Presentation::class.java))
        finish()
    }

    private fun navigateToContinueUserData() {
        startActivity(Intent(getApplication(), ContinueUserDataActivity::class.java))
        finish()
    }

    private fun navigateToVerifiedEmail() {
        startActivity(Intent(getApplication(), VerifiedEmailActivity::class.java))
        finish()
    }

}