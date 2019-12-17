package com.android.app.data.services.login

import android.util.Log
import com.android.app.contract.IVerifiedEmailContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.navan.app.alunouesb.data.model.BaseUser

class FirebaseVerifiedEmailService (var listener : IVerifiedEmailContract.Listener) : IVerifiedEmailContract.Service {
    val TAG = this::class.java.canonicalName

    override fun sendEmailVerification() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.
                addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.onSuccess("Email de verificação enviado com sucesso!")
                        Log.d(TAG, "Email verification send successful")
                    } else {
                        listener.onFailure("Não foi possível enviar o email de verificação")
                        Log.d(TAG, "Email verification send error")
                    }
                }
    }

    override fun isEmailVerified(user: BaseUser): Boolean {
        val emailVerified = FirebaseAuth.getInstance().currentUser!!.isEmailVerified
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .document(user.uID)
                .update("emailVerified", emailVerified);
        return emailVerified
    }

}