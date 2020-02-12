package com.android.app.contract

import com.navan.app.alunouesb.data.model.Usuario

interface IVerifiedEmailContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(message: String)
        fun onFailure(message : String)
    }

    interface Presenter {
        fun onDestroy()
        fun sendEmailVerification()
        fun isEmailVerified(user: Usuario): Boolean
    }

    interface Service {
        fun sendEmailVerification()
        fun isEmailVerified(user: Usuario): Boolean
    }

    interface Listener {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

}
