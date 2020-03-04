package com.navan.app.alunouesb.contract

import android.net.Uri
import com.navan.app.alunouesb.data.model.Usuario
import java.io.File

interface IUserContract {

    interface View {
        fun showProgress()
        fun hideProgress()

        fun onGetProfilePicSucess(url: String)
        fun onFailure(message : String)
        fun onUpdateUserSuccess(user: Usuario)
        fun onRemoveUserSucess(user: Usuario)
        fun onUpdateProfilePicSuccess(url: String)
        fun onRemoveProfilePicSucess(url: String)
    }

    interface Presenter {
        fun update(user: Usuario)
        fun updateProfilePick(img: Uri)
        fun getProfilePick(arquivo: File)
        fun remove(user: Usuario)
    }

    interface Listener {
        fun onUpdateUserSuccess(user: Usuario)
        fun onRemoveUserSucess(user: Usuario)
        fun onUpdateProfilePicSuccess(url: String)
        fun onRemoveProfilePicSucess(url: String)
        fun onGetProfilePicSucess(url: String)
        fun onFailure(message: String)
    }

    interface Service {
        fun update(user: Usuario)
        fun updateProfilePick(img: Uri)
        fun getProfilePick(arquivo: File)
        fun remove(user: Usuario)
    }
}