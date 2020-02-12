package com.android.app.contract

import android.content.Context
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

interface IUser {

    interface View {
        fun onResult(user: Usuario?)
    }

    interface Presenter {
        fun currentUser(context: Context)
        fun signOut(context: Context)
        fun onDestroy()
    }

    interface Service {
        fun currentUser(context: Context)
        fun signOut(context: Context)
    }

    interface Listener {
        fun onResult(user: Usuario?)
    }

}