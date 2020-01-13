package com.navan.app.alunouesb.data.shared_preferences;

import android.content.Context

class SPInfo(val context: Context) {

    fun updateIntroStatus(status : Boolean){
        context
                .getSharedPreferences("AlunoUESB", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("presentation", status)
                .apply()
    }

    fun isIntroShown() = context
            .getSharedPreferences("AlunoUESB", Context.MODE_PRIVATE)
            .getBoolean("presentation", false)

}
