package com.fcpunlimited.ubersport.di.gql

interface GraphQlResponseCallback<T> {
    fun onOkResponse(data: T)

    fun onErrorResponse(message: String)
}