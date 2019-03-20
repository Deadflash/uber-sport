package com.fcpunlimited.ubersport.di.api

interface HttpResponseCallBack<T> {
    fun onOkResponse(data: T)

    fun onErrorResponse(message: String)
}