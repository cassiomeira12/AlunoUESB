package com.navan.app.alunouesb.view.login;

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.model.BaseUser
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    internal lateinit var _user: BaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    override fun onBackPressed() {
        startActivity(Intent(getApplication(), LoginActivity::class.java))
        finish()
    }

    fun onSignUp(view: View) {
        if (isDataValid()) {
            val intent = Intent(getApplication(), CreateAccountActivity::class.java)
            intent.putExtra("user", _user)
            intent.putExtra("login", txtEmail.text.toString())
            intent.putExtra("password", txtPassword.text.toString())
            startActivity(intent)
            finish()
        }
    }

    fun isDataValid(): Boolean {
        val name = txtName.text.toString()
        val isNameValid = !TextUtils.isEmpty(name)
        if (!isNameValid) {
            txtName?.error = "Digite seu nome"
        }

        val email = txtEmail.text.toString()
        val isEmailValid = (!TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (!isEmailValid) {
            txtEmail?.error = "Email inválido"
        }

//        val phone = txtPhone.text.toString()
//        var isPhoneValid = (!TextUtils.isEmpty(phone)) && Patterns.PHONE.matcher(phone).matches()
//        if (!isPhoneValid) {
//            txtPhone?.error = "Telefone inválido"
//        }

        val firstPassword = txtPassword.text.toString()
        var isPassswordValid = firstPassword.length >= 6
        if (!isPassswordValid) {
            txtPassword?.error = "Senha muito curta"
            txtPasswordComfirmed.text.clear()
            txtPassword?.requestFocus()
        } else {
            val secondPassword = txtPasswordComfirmed.text.toString()
            isPassswordValid = isPassswordValid && TextUtils.equals(firstPassword, secondPassword)
            if (!isPassswordValid) {
                txtPasswordComfirmed?.error = "Senha nao confere"
            }
        }

        val isDataValid = isNameValid && isEmailValid && /*isPhoneValid &&*/ isPassswordValid

        if (isDataValid) {
            _user = BaseUser()
            _user.name = name
            _user.email = email
            _user.password = firstPassword
            //_user.phone = phone
        }

        return isDataValid
    }

    fun termosUso(view: View) {

    }

    fun politicaPrivacidade(view: View) {

    }

    fun politicaDados(view: View) {

    }

}