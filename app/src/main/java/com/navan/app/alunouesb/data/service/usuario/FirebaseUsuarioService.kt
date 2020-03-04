package com.navan.app.alunouesb.data.service.usuario

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.navan.app.alunouesb.contract.IUserContract
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.model.Usuario
import java.io.File
import java.io.FileOutputStream

class FirebaseUsuarioService (var listener: IUserContract.Listener) : IUserContract.Service {
    private val TAG = this::class.java.canonicalName

    override fun update(user: Usuario) {
        val db = FirebaseFirestore.getInstance()
        val userUID = CompleteUserSingleton.instance.uID

        db.collection("users")
                .document(userUID)
                .set(user)
                .addOnSuccessListener {
                    listener.onUpdateUserSuccess(user)
                }
                .addOnFailureListener { exception ->
                    checkException(exception)
                }
    }

    override fun remove(user: Usuario) {
        val db = FirebaseFirestore.getInstance()
        val userUID = CompleteUserSingleton.instance.uID

        db.collection("users")
                .document(userUID)
                .delete()
                .addOnSuccessListener {
                    listener.onRemoveUserSucess(user)
                }
                .addOnFailureListener { exception ->
                    checkException(exception)
                }
    }

    override fun updateProfilePick(img: Uri) {

        val db = FirebaseStorage.getInstance()
        val userUID = CompleteUserSingleton.instance.uID


        db.getReference( userUID + "/profilePic/picture")
                .putFile(img) // salva no Storage do usuario
                .addOnCompleteListener {snapshot ->
                    if (snapshot.isSuccessful) {
                        db.getReference( userUID + "/profilePic/picture").downloadUrl.addOnSuccessListener{
                            uri -> listener.onUpdateProfilePicSuccess(uri.toString())
                        }

                    }
                }.addOnFailureListener {
                    exception -> checkException(exception)
                }

    }

    override fun getProfilePick(arquivo: File) {
        val db = FirebaseStorage.getInstance()
        val userUID = CompleteUserSingleton.instance.uID

        db.getReference(userUID + "/profilePic/picture").getFile(arquivo)
                .addOnCompleteListener {
                    task -> if (task.isSuccessful){
                        listener.onGetProfilePicSucess(task.result.toString())
                    }
                }
                .addOnFailureListener{
                    exception -> checkException(exception)
                }
    }



    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when(ex.message) {
            DISCONNECTED_NETWORK -> {
                listener.onFailure("Verifique sua conexão com a internet")
            }
            else -> {
                listener.onFailure(ex.message.toString())
            }
        }
    }

    companion object {
        private val DISCONNECTED_NETWORK = "An internal error has occurred. [ 7: ]"
    }
}