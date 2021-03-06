package com.android.app.contract

import android.app.Activity
import android.content.Intent
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

interface IGoogleSignContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(user: Usuario)
        fun onFailure(message: String)
    }

    interface Presenter {
        fun createGoogleClient(activity: Activity)
        fun onSignIn()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
        fun onDestroy()
    }

    interface Service {
        fun createGoogleClient(activity: Activity)
        fun signIn()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    interface Listener {
        fun onSuccess(user: Usuario)
        fun onFailure(message: String)
    }

}