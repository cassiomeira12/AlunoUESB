package com.navan.app.alunouesb.view.login;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.app.contract.IGoogleSignContract
import com.android.app.contract.ILoginContract
import com.android.app.presenter.login.GoogleSignPresenter
import com.android.app.presenter.login.LoginPresenter
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.view.activity.PrincipalActivity
import com.navan.app.alunouesb.view.dialog.ProgressDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.txtPassword

class LoginActivity : AppCompatActivity(), ILoginContract.View, IGoogleSignContract.View {
    internal lateinit var progressDialog: ProgressDialog
    internal lateinit var loginPresenter: ILoginContract.Presenter
    internal lateinit var googlePresenter: IGoogleSignContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog = ProgressDialog(this)

        loginPresenter = LoginPresenter(this)
        googlePresenter = GoogleSignPresenter(this)
        googlePresenter.createGoogleClient(this)
    }

    override fun onDestroy() {
        loginPresenter.onDestroy()
        googlePresenter.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googlePresenter.onActivityResult(requestCode, resultCode, data)
    }

    fun signIn(view: View) {
        loginPresenter.onLogin(this, txtLogin.text.toString(), txtPassword.text.toString())
    }

    fun recuperarSenha(view: View) {
        startActivity(Intent(getApplication(), ForgotPasswordActivity::class.java))
    }

    fun googleSignIn(view: View) {
        googlePresenter.onSignIn()
    }

    fun createAccount(view: View) {
        startActivity(Intent(getApplication(), SignupActivity::class.java))
        finish()
    }

    override fun showProgress() {
        progressDialog.show()
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun setLoginNameError(message: String) {
        txtLogin?.error = message
    }

    override fun setPasswordNameError(message: String) {
        txtPassword?.error = message
    }

    fun navigateToHome(user: BaseUser) {
        if (user.emailVerified) {
            navigateToMainActivity(user)
        } else {
            navigateToVerifiedEmail(user)
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

    override fun onSuccess(user: BaseUser) {
        navigateToHome(user)
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}