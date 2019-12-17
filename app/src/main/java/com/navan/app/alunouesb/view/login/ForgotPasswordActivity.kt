package com.navan.app.alunouesb.view.login;

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.app.contract.IForgotPasswordContract
import com.android.app.presenter.login.ForgotPasswordPresenter
import com.navan.app.alunouesb.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity(), IForgotPasswordContract.View {
    internal lateinit var iForgotPasswordPresenter: IForgotPasswordContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        iForgotPasswordPresenter = ForgotPasswordPresenter(this)
    }

    fun send(view: View) {
        btnSend.visibility = View.GONE
        iForgotPasswordPresenter.onSend(txtEmail.text.toString())
    }

    override fun onSuccessResult() {
        setContentView(R.layout.activity_forgot_password_success)
    }

    override fun onFailureResult(message: String) {
        btnSend.visibility = View.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun onBackActivity(view: View) {
        finish()
    }

}