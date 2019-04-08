package com.fcpunlimited.ubersport.di.game

interface HttpEmptyResponseCallBack {
    fun onResponse() {}

    fun onFailure(message: String)
}