package com.android.app.data.services.login

import android.content.Context
import android.util.Log
import com.android.app.contract.IUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.navan.app.alunouesb.data.model.BaseUser

class FirebaseUserService(var listener : IUser.Listener) : IUser.Service {
    val TAG = this::class.java.canonicalName

    override fun currentUser(context: Context) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.d(TAG, "Usuario nao logado")
            listener.onResult(null)
        } else {
            Log.d(TAG, "Usuario logado")
            val email = currentUser?.email
            findUserByEmail(email!!)
        }
    }

    override fun signOut(context: Context) {
        val currentUser = FirebaseAuth.getInstance()
        currentUser.signOut()
        listener.onResult(null)
    }

    private fun findUserByEmail(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    Log.d(TAG, "Size " + querySnapshot.size())
                    if (querySnapshot.size() == 0) {
                        Log.d(TAG, "Usuario nao encontrado")
                        listener.onResult(null)//Erro usuario nao encontrado
                    } else if (querySnapshot.size() == 1) {
                        val user = querySnapshot.documents.get(0).toObject(BaseUser::class.java)
                        Log.d(TAG, user.toString())
                        listener.onResult(user)
                    } else {
                        Log.d(TAG, "Erro, mais de uma conta com mesmo email")
                        listener.onResult(null)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, exception.toString())
                    listener.onResult(null)
                }
    }
}