package com.navan.app.alunouesb.view.login;

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.navan.app.alunouesb.R
import com.navan.app.alunouesb.view.activity.PrincipalActivity


class ContinueUserDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_dados)
    }

    fun termosUso(view: View) {
        val intent = Intent(getApplication(), TermosAppActivity::class.java)
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.TERMO_DE_USO)
        startActivity(intent)
    }

    fun politicaPrivacidade(view: View) {
        val intent = Intent(getApplication(), TermosAppActivity::class.java)
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.POLITICA_DE_PRIVACIDADE)
        startActivity(intent)
    }

    fun politicaDados(view: View) {
        val intent = Intent(getApplication(), TermosAppActivity::class.java)
        intent.putExtra(TermosAppActivity.TERMO, TermosAppActivity.POLITICA_DE_DADOS)
        startActivity(intent)
    }

    fun avancar(view: View) {
        startActivity(Intent(getApplication(), PrincipalActivity::class.java))
        finish()
    }

}