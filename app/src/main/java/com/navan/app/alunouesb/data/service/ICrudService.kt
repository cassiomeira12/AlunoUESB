package com.navan.app.alunouesb.data.service

interface ICrudService<T> {

    fun add(item: T)

    fun remove(item: T)

    fun update(item: T)

    fun list()

}