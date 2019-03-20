package com.fcpunlimited.ubersport.di.api

import com.fcpunlimited.ubersport.struct.auth.Token
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitApi {
    @GET("auth/token")
    fun getToken(): Observable<Token>
}