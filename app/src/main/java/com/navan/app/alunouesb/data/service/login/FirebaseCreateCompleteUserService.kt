package com.navan.app.alunouesb.data.service.login


import android.app.Activity
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.navan.app.alunouesb.contract.ICreateAccountCompleteContract
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.Usuario

class FirebaseCreateCompleteUserService (var listener : ICreateAccountCompleteContract.Listener) : ICreateAccountCompleteContract.Service{
    val TAG = this::class.java.canonicalName


    override fun register(activity: Activity, user: Usuario) {
        createUser(user)
    }


    private fun createUser(user: Usuario) {
        val db = FirebaseFirestore.getInstance()

        val uID = CompleteUserSingleton.instance.uID // id do BaseUser

        db.collection("users")
                .document(uID)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "Usuario adicionado no BD com sucesso")
                    listener.onCreatedSuccess(user)
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, exception.toString())
                    listener.onFailure(exception.toString())
                }
    }
}