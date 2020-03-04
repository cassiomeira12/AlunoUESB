package com.navan.app.alunouesb.data.service.disciplina

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.navan.app.alunouesb.contract.IDisciplinaContract
import com.navan.app.alunouesb.data.CompleteUserSingleton
import com.navan.app.alunouesb.data.model.Disciplina

class FirebaseDisciplinaService (var listener: IDisciplinaContract.Listener) : IDisciplinaContract.Service {
    private val TAG = this::class.java.canonicalName

    override fun add(item: Disciplina) {
        val db = FirebaseFirestore.getInstance()
        val user = CompleteUserSingleton.instance
        val semestreSelecionado = user.getSemestre(user.idSemestre) // semestre em uso
        val idDisciplina = db.collection("users").document().id

        item.id = idDisciplina

        db.collection("users")
                .document(user.uID)
                .collection("semestres")
                .document(semestreSelecionado)
                .collection("disciplinas")
                .document(item.id)
                .set(item)
                .addOnSuccessListener {
                    listener.onCreatedSuccess(item)
                }
                .addOnFailureListener { exception ->
                    checkException(exception)
                }
    }

    override fun remove(item: Disciplina) {
        val db = FirebaseFirestore.getInstance()
        val user = CompleteUserSingleton.instance
        val semestreSelecionado = user.getSemestre(user.idSemestre) // semestre em uso

        db.collection("users")
                .document(user.uID)
                .collection("semestres")
                .document(semestreSelecionado)
                .collection("disciplinas")
                .document(item.id)
                .delete()
                .addOnSuccessListener {
                    listener.onRemovedSuccess(item)
                }
                .addOnFailureListener { exception ->
                    checkException(exception)
                }
    }

    override fun update(item: Disciplina) {
        val db = FirebaseFirestore.getInstance()
        val user = CompleteUserSingleton.instance
        val semestreSelecionado = user.getSemestre(user.idSemestre) // semestre em uso

        db.collection("users")
                .document(user.uID)
                .collection("semestres")
                .document(semestreSelecionado)
                .collection("disciplinas")
                .document(item.id)
                .set(item)
                .addOnSuccessListener {
                    listener.onCreatedSuccess(item)
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
                .collection("disciplinas")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.onListSuccess(task.result!!.toObjects(Disciplina::class.java))
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