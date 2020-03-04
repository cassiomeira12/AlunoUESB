package com.navan.app.alunouesb.presenter.usuario

import android.net.Uri
import com.navan.app.alunouesb.contract.IUserContract
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.model.Usuario
import com.navan.app.alunouesb.data.service.usuario.FirebaseUsuarioService
import java.io.File

class UsuarioPresenter (view: IUserContract.View) : IUserContract.Presenter, IUserContract.Listener {
    var view : IUserContract.View? = view
    var service : IUserContract.Service = FirebaseUsuarioService(this)

    override fun update(user: Usuario) {
        view?.showProgress()
        service.update(user)
    }

    override fun updateProfilePick(img: Uri) {
        view?.showProgress()
        service.updateProfilePick(img)
    }

    override fun getProfilePick(arquivo: File) {
        view?.showProgress()
        service.getProfilePick(arquivo)
    }

    override fun remove(user: Usuario) {
        view?.showProgress()
        service.remove(user)
    }

    override fun onUpdateUserSuccess(user: Usuario) {
        view?.hideProgress()
        view?.onUpdateUserSuccess(user)
    }

    override fun onRemoveUserSucess(user: Usuario) {
        view?.hideProgress()
        view?.onRemoveUserSucess(user)
    }

    override fun onUpdateProfilePicSuccess(url: String) {
        view?.hideProgress()
        view?.onUpdateProfilePicSuccess(url)
    }

    override fun onRemoveProfilePicSucess(url: String) {
        view?.hideProgress()
        view?.onRemoveProfilePicSucess(url)
    }

    override fun onGetProfilePicSucess(url: String) {
        view?.hideProgress()
        view?.onGetProfilePicSucess(url)
    }

    override fun onFailure(message: String) {
        view?.hideProgress()
        view?.onFailure(message)
    }


}