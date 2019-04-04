package com.fcpunlimited.ubersport.di.game

import java.lang.RuntimeException

interface HttpEmptyResponseCallBack {
    fun onResponse() {
        throw RuntimeException("Need implementation")
    }

    fun onFailure(message: String)
}