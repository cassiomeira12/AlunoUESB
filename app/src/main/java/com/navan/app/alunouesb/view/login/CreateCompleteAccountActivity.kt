package com.navan.app.alunouesb.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.app.contract.ICreateAccountContract
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.contract.ICreateAccountCompleteContract
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario
import com.navan.app.alunouesb.presenter.login.CreateCompleteAccountPresenter
import com.navan.app.alunouesb.view.activity.PrincipalActivity
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateCompleteAccountActivity : AppCompatActivity(), ICreateAccountCompleteContract.View {

    internal lateinit var iPresenter: ICreateAccountCompleteContract.Presenter

    internal lateinit var _user: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_complete_account)

        _user = CompleteUserSingleton.instance

        iPresenter = CreateCompleteAccountPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        createAccount()
    }

    private fun createAccount() {
        iPresenter.register(this, _user)
    }

    override fun showProgress() {
        imgResult.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
        imgResult.visibility = View.VISIBLE
    }

    override fun onCreatedSuccess(user: Usuario) {
        CompleteUserSingleton.instance.setUsuario(user) //Atualizando instancia do usuario
        txtMessage.text = getString(R.string.conta_criada_com_sucesso)
        Handler().postDelayed(
                Runnable {
                    val intent = Intent(getApplicationContext(), PrincipalActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }, 1500
        )
    }


    override fun onFailure(message: String) {
        imgResult.setImageResource(R.drawable.error)
        txtMessage.text = message
    }
}