package com.navan.app.alunouesb.data

import com.navan.app.alunouesb.data.model.BaseUser

class UserSingleton {
    companion object {
        val instance = BaseUser()
    }
}