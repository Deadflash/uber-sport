package com.fcpunlimited.ubersport.di.game

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.di.api.HttpRequestClients
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.type.GameFiltersInput

class GameModel(private val httpRequestClients: HttpRequestClients) {

    fun getGames(filters: GameFiltersInput, callBack: HttpResponseCallBack<GamesQuery.Data>) {
        httpRequestClients.getApolloClient().query(GamesQuery.builder()
                .filters(filters)
                .build())
                .enqueue(object : ApolloCall.Callback<GamesQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { callBack.onErrorResponse(it) }
                    }

                    override fun onResponse(response: Response<GamesQuery.Data>) {
                        if (response.hasErrors())
                            callBack.onErrorResponse(response.errors().toString())
                        else
                            callBack.onOkResponse(response.data()!!)
                    }
                })
    }
}