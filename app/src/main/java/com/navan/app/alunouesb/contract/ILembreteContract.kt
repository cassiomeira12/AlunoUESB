package com.navan.app.alunouesb.contract

import com.navan.app.alunouesb.data.model.Lembrete
import com.navan.app.alunouesb.data.service.ICrudService

interface ILembreteContract{

    interface View {
        fun showProgress()
        fun hideProgress()

        fun onListSuccess(list: List<Lembrete>)
        fun onCreatedSuccess(item: Lembrete)
        fun onUpdateSuccess(item: Lembrete)
        fun onRemovedSuccess(item: Lembrete)

        fun onFailure(message : String)
    }

    interface Presenter : ICrudService<Lembrete> {
        fun onDestroy()
    }

    interface Service : ICrudService<Lembrete> {

    }

    interface Listener {
        fun onListSuccess(list: List<Lembrete>)
        fun onCreatedSuccess(item: Lembrete)
        fun onUpdateSuccess(item: Lembrete)
        fun onRemovedSuccess(item: Lembrete)
        fun onFailure(message: String)
    }
}