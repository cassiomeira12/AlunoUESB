package com.navan.app.alunouesb.view.login;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.app.contract.IVerifiedEmailContract
import com.android.app.presenter.login.VerifiedEmailPresenter
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.view.activity.PrincipalActivity
import kotlinx.android.synthetic.main.activity_verified_email.*

class VerifiedEmailActivity : AppCompatActivity(), IVerifiedEmailContract.View {
    internal lateinit var iPresenter: IVerifiedEmailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verified_email)

        iPresenter = VerifiedEmailPresenter(this)

        val user = UserSingleton.instance
        txtEmail.text = user.email

        //Verificar confirmacao de email
        val emailVerified = iPresenter.isEmailVerified(user)
        user.emailVerified = emailVerified //Atualizar emailVerified do usuario

        if (emailVerified) {
            navigateToMainActivity()
        } else {
            iPresenter.sendEmailVerification()
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(getApplication(), PrincipalActivity::class.java))
        finish()
    }

    private fun navigateToContinueUserData() {
        startActivity(Intent(getApplication(), ContinueUserDataActivity::class.java))
        finish()
    }

    fun onBackActivity(view: View) {
        onBackPressed()
    }

    override fun onBackPressed() {
        startActivity(Intent(getApplication(), LoginActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        iPresenter.onDestroy()
        super.onDestroy()
    }

    fun sendEmailVerification(view: View) {
        iPresenter.sendEmailVerification()
    }

    override fun showProgress() {
        imgResult.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
        imgResult.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        imgResult.setImageResource(R.drawable.email)
        txtMessage.text = message
    }

    override fun onFailure(message: String) {
        imgResult.setImageResource(R.drawable.error)
        txtMessage.text = message
    }

}
