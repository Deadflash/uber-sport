package com.fcpunlimited.ubersport.di.api

import com.fcpunlimited.ubersport.struct.auth.LoginBody
import com.fcpunlimited.ubersport.struct.auth.Token
import com.fcpunlimited.ubersport.struct.auth.UberAuth
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApi {
    @GET("auth/token")
    fun getToken(): Observable<Token>

    @POST("auth/login")
    fun login(@Body login: LoginBody): Observable<UberAuth>
}