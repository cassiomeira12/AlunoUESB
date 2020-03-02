package com.android.app.presenter.login

import android.app.Activity
import com.navan.app.alunouesb.contract.ILembreteContract
import com.navan.app.alunouesb.data.model.Lembrete
import com.navan.app.alunouesb.data.model.Usuario
import com.navan.app.alunouesb.data.service.lembrete.FirebaseLembreteService


class LembretePresenter(view: ILembreteContract.View) : ILembreteContract.Presenter, ILembreteContract.Listener {
    var view : ILembreteContract.View? = view
    var service: ILembreteContract.Service = FirebaseLembreteService(this)

    override fun onDestroy() {
        this.view = null
    }

    override fun add(item: Lembrete) {
        view?.showProgress()
        service.add(item)
    }

    override fun remove(item: Lembrete) {
        view?.showProgress()
        service.remove(item)
    }

    override fun update(item: Lembrete) {
        view?.showProgress()
        service.update(item)
    }

    override fun list() {
        view?.showProgress()
        service.list()
    }

    override fun onListSuccess(list: List<Lembrete>) {
        view?.hideProgress()
        view?.onListSuccess(list)
    }

    override fun onCreatedSuccess(item: Lembrete) {
        view?.hideProgress()
        view?.onCreatedSuccess(item)
    }

    override fun onUpdateSuccess(item: Lembrete) {
        view?.hideProgress()
        view?.onUpdateSuccess(item)
    }

    override fun onRemovedSuccess(item: Lembrete) {
        view?.hideProgress()
        view?.onRemovedSuccess(item)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }

}