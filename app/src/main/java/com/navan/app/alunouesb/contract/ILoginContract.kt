package com.android.app.contract

import android.app.Activity
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

interface ILoginContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(user: Usuario)
        fun onFailure(message: String)

        fun setLoginNameError(message: String)
        fun setPasswordNameError(message: String)
    }

    interface Presenter {
        fun onLogin(activity: Activity, email : String, password : String)
        fun onDestroy()
    }

    interface Service {
        fun onLogin(activity : Activity, login : String, password: String)
    }

    interface Listener {
        fun onSuccess(user: Usuario)
        fun onFailure(message : String)
    }

}