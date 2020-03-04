package com.navan.app.alunouesb.data.service.lembrete

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.navan.app.alunouesb.contract.ILembreteContract
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.UserSingleton
import com.navan.app.alunouesb.data.model.Lembrete

class FirebaseLembreteService(var listener : ILembreteContract.Listener) : ILembreteContract.Service {
    private val TAG = this::class.java.canonicalName

    override fun add(item: Lembrete) {
        val db = FirebaseFirestore.getInstance()
        val user = CompleteUserSingleton.instance
        val semestreSelecionado = user.getSemestre(user.idSemestre) // semestre em uso
        val lembreteID = db.collection("users").document().id

        item.id = lembreteID

        db.collection("users")
                .document(user.uID)
                .collection("semestres")
                .document(semestreSelecionado)
                .collection("lembretes")
                .document(lembreteID)
                .set(item)
                .addOnSuccessListener {
                    listener.onCreatedSuccess(item)
                }
                .addOnFailureListener { exception ->
                    checkException(exception)
                }
    }

    override fun remove(item: Lembrete) {
        val db = FirebaseFirestore.getInstance()
        val user = CompleteUserSingleton.instance
        val semestreSelecionado = user.getSemestre(user.idSemestre) // semestre em uso
        val lembreteID = item.id

        db.collection("users")
                .document(user.uID)
                .collection("semestres")
                .document(semestreSelecionado)
                .collection("lembretes")
                .document(lembreteID)
                .delete()
                .addOnSuccessListener {
                    listener.onRemovedSuccess(item)
                }
                .addOnFailureListener { exception ->
                    checkException(exception)
                }
    }

    override fun update(item: Lembrete) {
        val db = FirebaseFirestore.getInstance()
        val user = CompleteUserSingleton.instance
        val semestreSelecionado = user.getSemestre(user.idSemestre) // semestre em uso
        val lembreteID = item.id

        db.collection("users")
                .document(user.uID)
                .collection("semestres")
                .document(semestreSelecionado)
                .collection("lembretes")
                .document(lembreteID)
                .set(item)
                .addOnSuccessListener {
                    listener.onUpdateSuccess(item)
                }
                .addOnFailureListener { exception ->
                    checkException(exception)
                }
    }

    override fun list() {
        val db = FirebaseFirestore.getInstance()
        val user = CompleteUserSingleton.instance
        val semestreSelecionado = user.getSemestre(user.idSemestre) // semestre em uso

        db.collection("users")
                .document(user.uID)
                .collection("semestres")
                .document(semestreSelecionado)
                .collection("lembretes")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.onListSuccess(task.result!!.toObjects(Lembrete::class.java))
                    } else {
                        checkException(task.exception!!)
                    }
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