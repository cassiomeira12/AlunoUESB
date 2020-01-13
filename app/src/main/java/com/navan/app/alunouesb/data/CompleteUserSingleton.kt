package com.navan.app.alunouesb.data

import com.navan.app.alunouesb.data.model.BaseUser
import com.navan.app.alunouesb.data.model.Usuario

class CompleteUserSingleton{
    companion object {
        val instance = Usuario()
    }
}