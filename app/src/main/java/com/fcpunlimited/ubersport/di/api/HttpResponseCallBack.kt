package com.fcpunlimited.ubersport.di.api

interface HttpResponseCallBack<T> {
    fun onResponse(data: T)

    fun onFailure(message: String)
}