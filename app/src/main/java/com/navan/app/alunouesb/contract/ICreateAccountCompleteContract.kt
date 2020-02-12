package com.navan.app.alunouesb.contract

import android.app.Activity
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

interface ICreateAccountCompleteContract{

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onCreatedSuccess(user: Usuario)
        fun onFailure(message : String)
    }

    interface Presenter {
        fun onDestroy()
        fun register(activity: Activity, user : Usuario)
    }

    interface Service{
        fun register(activity: Activity, user: Usuario)
    }

    interface Listener {
        fun onCreatedSuccess(user: Usuario)
        fun onFailure(message: String)
    }
}