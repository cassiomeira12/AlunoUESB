package com.android.app.data.services.login

import android.util.Log
import com.android.app.contract.IVerifiedEmailContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

class FirebaseVerifiedEmailService (var listener : IVerifiedEmailContract.Listener) : IVerifiedEmailContract.Service {
    val TAG = this::class.java.canonicalName

    override fun sendEmailVerification() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.
                addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email de verificação enviado com sucesso!")
                        listener.onSuccess("Email de verificação enviado com sucesso!")
                    } else {
                        listener.onFailure("Não foi possível enviar o email de verificação")
                        Log.e(TAG, task.exception.toString())
                    }
                }
    }

    override fun isEmailVerified(user: Usuario): Boolean {
        val emailVerified = FirebaseAuth.getInstance().currentUser!!.isEmailVerified
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .document(user.uID)
                .update("emailVerified", emailVerified)
        return emailVerified
    }

}