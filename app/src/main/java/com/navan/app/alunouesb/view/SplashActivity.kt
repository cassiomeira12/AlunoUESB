package com.navan.app.alunouesb.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android.app.contract.IUser
import com.android.app.presenter.login.UserPresenter
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.view.activity.PrincipalActivity
import com.navan.app.alunouesb.view.login.ContinueUserDataActivity
import com.navan.app.alunouesb.view.login.LoginActivity
import com.navan.app.alunouesb.view.login.VerifiedEmailActivity


class SplashActivity : AppCompatActivity(), IUser.View {
    internal lateinit var iPresenter: IUser.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
            if (user.emailVerified) {//Verificacao de email
                navigateToMainActivity(user)
            } else {
                navigateToVerifiedEmail(user)
            }
        }
    }

    private fun navigateToMainActivity(user: BaseUser) {
        val intent = Intent(getApplication(), PrincipalActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

    private fun navigateToContinueUserData(user: BaseUser) {
        val intent = Intent(getApplication(), ContinueUserDataActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

    private fun navigateToVerifiedEmail(user: BaseUser) {
        val intent = Intent(getApplication(), VerifiedEmailActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
        finish()
    }

}