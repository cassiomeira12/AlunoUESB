package com.navan.app.alunouesb.contract

import com.navan.app.alunouesb.data.model.Disciplina
import com.navan.app.alunouesb.data.service.ICrudService

interface IDisciplinaContract{

    interface View {
        fun showProgress()
        fun hideProgress()

        fun onListSuccess(list: List<Disciplina>)
        fun onCreatedSuccess(item: Disciplina)
        fun onUpdateSuccess(item: Disciplina)
        fun onRemovedSuccess(item: Disciplina)

        fun onFailure(message : String)
    }

    interface Presenter : ICrudService<Disciplina> {
        fun onDestroy()
    }

    interface Service : ICrudService<Disciplina> {

    }

    interface Listener {
        fun onListSuccess(list: List<Disciplina>)
        fun onCreatedSuccess(item: Disciplina)
        fun onUpdateSuccess(item: Disciplina)
        fun onRemovedSuccess(item: Disciplina)
        fun onFailure(message: String)
    }
}