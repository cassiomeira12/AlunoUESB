package com.navan.app.alunouesb.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.app.contract.IUser
import com.android.app.presenter.login.UserPresenter
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.shared_preferences.SPInfo
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

    override fun onResult(user: BaseUser?) {
        if (user == null) {
            startActivity(Intent(getApplication(), LoginActivity::class.java))
            finish()
        } else {
            UserSingleton.instance.setUser(user) //Salvar dados numa unica instancia de BaseUser
            if (user.emailVerified) {//Verificacao de email
                navigateToMainActivity()
            } else {
                navigateToVerifiedEmail()
            }
        }
    }

    private fun navigateToMainActivity() {
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