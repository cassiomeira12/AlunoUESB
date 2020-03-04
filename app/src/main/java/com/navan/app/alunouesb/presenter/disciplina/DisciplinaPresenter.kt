package com.navan.app.alunouesb.presenter.disciplina

import com.navan.app.alunouesb.contract.IDisciplinaContract
import com.navan.app.alunouesb.data.model.Disciplina
import com.navan.app.alunouesb.data.service.disciplina.FirebaseDisciplinaService

class DisciplinaPresenter (view: IDisciplinaContract.View) : IDisciplinaContract.Presenter, IDisciplinaContract.Listener {
    var view : IDisciplinaContract.View? = view
    var service: IDisciplinaContract.Service = FirebaseDisciplinaService(this)

    override fun onDestroy() {
        this.view = null
    }

    override fun add(item: Disciplina) {
        view?.showProgress()
        service.add(item)
    }

    override fun remove(item: Disciplina) {
        view?.showProgress()
        service.remove(item)
    }

    override fun update(item: Disciplina) {
        view?.showProgress()
        service.update(item)
    }

    override fun list() {
        view?.showProgress()
        service.list()
    }

    override fun onListSuccess(list: List<Disciplina>) {
        view?.hideProgress()
        view?.onListSuccess(list)
    }

    override fun onCreatedSuccess(item: Disciplina) {
        view?.hideProgress()
        view?.onCreatedSuccess(item)
    }

    override fun onUpdateSuccess(item: Disciplina) {
        view?.hideProgress()
        view?.onUpdateSuccess(item)
    }

    override fun onRemovedSuccess(item: Disciplina) {
        view?.hideProgress()
        view?.onRemovedSuccess(item)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }
}