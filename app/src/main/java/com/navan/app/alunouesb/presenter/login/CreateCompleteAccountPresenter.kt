package com.navan.app.alunouesb.presenter.login

import android.app.Activity
import com.android.app.contract.ICreateAccountContract
import com.android.app.data.services.login.FirebaseCreateAccountService
import com.navan.app.alunouesb.contract.ICreateAccountCompleteContract
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario
import com.navan.app.alunouesb.data.service.login.FirebaseCreateCompleteUserService

class CreateCompleteAccountPresenter(view: ICreateAccountCompleteContract.View) : ICreateAccountCompleteContract.Presenter, ICreateAccountCompleteContract.Listener {
    var view : ICreateAccountCompleteContract.View? = view
    var service : ICreateAccountCompleteContract.Service = FirebaseCreateCompleteUserService(this)

    override fun register(activity: Activity, user: Usuario) {
        service.register(activity, user)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onCreatedSuccess(user: Usuario) {
        view?.hideProgress()
        view?.onCreatedSuccess(user)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }
}