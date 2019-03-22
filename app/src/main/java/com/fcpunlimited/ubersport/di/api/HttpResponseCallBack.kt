package com.fcpunlimited.ubersport.di.api

import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack

interface HttpResponseCallBack<T>: HttpEmptyResponseCallBack {
    fun onResponse(data: T)
}