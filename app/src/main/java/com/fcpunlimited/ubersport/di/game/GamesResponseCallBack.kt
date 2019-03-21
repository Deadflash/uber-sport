package com.fcpunlimited.ubersport.di.game

interface GamesResponseCallBack {
    fun onResponse()
    fun onFailure(message: String)
}