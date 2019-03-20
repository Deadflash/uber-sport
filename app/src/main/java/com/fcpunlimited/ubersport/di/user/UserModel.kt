package com.fcpunlimited.ubersport.di.user

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.UsersQuery
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.di.api.HttpRequestClients
import com.fcpunlimited.ubersport.struct.auth.Token
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UserModel(private val httpRequestClients: HttpRequestClients) {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var token: String? = null

    fun hasToken() = token != null

    fun getUsers(callBack: HttpResponseCallBack<UsersQuery.Data>) {
        httpRequestClients.getApolloClient().query(UsersQuery.builder()
                .build())
                .enqueue(object : ApolloCall.Callback<UsersQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { callBack.onErrorResponse(it) }
                    }

                    override fun onResponse(response: Response<UsersQuery.Data>) {
                        if (response.hasErrors())
                            callBack.onErrorResponse(response.errors().toString())
                        else
                            callBack.onOkResponse(response.data()!!)
                    }

                })
    }

    fun getToken(callBack: HttpResponseCallBack<Token>) {
        httpRequestClients.getRetrofitApi().getToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token -> callBack.onOkResponse(token) }, { error -> error.message?.let { callBack.onErrorResponse(it) } })
    }
}