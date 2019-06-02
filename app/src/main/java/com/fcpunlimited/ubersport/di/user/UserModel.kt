package com.fcpunlimited.ubersport.di.user

import android.annotation.SuppressLint
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.UsersQuery
import com.fcpunlimited.ubersport.di.api.HttpRequestClients
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.struct.auth.LoginBody
import com.fcpunlimited.ubersport.struct.auth.Token
import com.fcpunlimited.ubersport.struct.auth.UberAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UserModel(private val httpRequestClients: HttpRequestClients) {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var token: String? = null
    private var userId = "5ba405680a5d34363fba418c"

    fun hasToken() = token != null

    fun getUsers(callBack: HttpResponseCallBack<UsersQuery.Data>) {
        httpRequestClients.getApolloClient().query(UsersQuery.builder()
                .build())
                .enqueue(object : ApolloCall.Callback<UsersQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { callBack.onFailure(it) }
                    }

                    override fun onResponse(response: Response<UsersQuery.Data>) {
                        if (response.hasErrors())
                            callBack.onFailure(response.errors().toString())
                        else
                            callBack.onResponse(response.data()!!)
                    }

                })
    }

    @SuppressLint("CheckResult")
    fun getToken(callBack: HttpResponseCallBack<Token>) {
        httpRequestClients.getRetrofitApi().getToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token -> callBack.onResponse(token) }, { error -> error.message?.let { callBack.onFailure(it) } })
    }

    @SuppressLint("CheckResult")
    fun login(callBack: HttpResponseCallBack<UberAuth>) {
        httpRequestClients.getRetrofitApi().login(LoginBody("l11p1a2@mail.ru", "123"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ uberAuth ->
                    httpRequestClients.setToken(uberAuth.accessToken)
                    userId = uberAuth.user._id
                    callBack.onResponse(uberAuth)
                }, { error -> error.message?.let { callBack.onFailure(it) } })
    }

    fun getUserId(): String = userId
}