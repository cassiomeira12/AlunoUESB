package com.android.app.presenter.login

import com.android.app.contract.IVerifiedEmailContract
import com.android.app.data.services.login.FirebaseVerifiedEmailService
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

class VerifiedEmailPresenter (view: IVerifiedEmailContract.View) : IVerifiedEmailContract.Presenter, IVerifiedEmailContract.Listener {
    var view : IVerifiedEmailContract.View? = view
    var service: IVerifiedEmailContract.Service = FirebaseVerifiedEmailService(this)

    override fun onDestroy() {
        this.view = null
    }

    override fun sendEmailVerification() {
        view?.showProgress()
        service.sendEmailVerification()
    }

    override fun isEmailVerified(user: Usuario): Boolean {
        return service.isEmailVerified(user)
    }

    override fun onSuccess(message: String) {
        view?.hideProgress()
        view?.onSuccess(message)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }
}