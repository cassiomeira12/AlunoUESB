package com.android.app.presenter.login

import android.app.Activity
import android.content.Intent
import com.android.app.contract.IGoogleSignContract
import com.android.app.data.services.login.FirebaseGoogleSignService
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

class GoogleSignPresenter (view: IGoogleSignContract.View): IGoogleSignContract.Presenter, IGoogleSignContract.Listener {
    var view: IGoogleSignContract.View? = view
    var service: IGoogleSignContract.Service = FirebaseGoogleSignService(this)

    override fun createGoogleClient(activity: Activity) {
        service.createGoogleClient(activity)
    }

    override fun onSignIn() {
        view?.showProgress()
        service.signIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        service.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onSuccess(user: Usuario) {
        view?.hideProgress()
        view?.onSuccess(user)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }

}