package com.fcpunlimited.ubersport.di.game

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.JoinGameMutation
import com.fcpunlimited.ubersport.LeaveGameMutation
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

    fun joinGame(userId: String, gameId: String, callBack: HttpResponseCallBack<String>) {
        httpRequestClients.getApolloClient().mutate(JoinGameMutation.builder()
                .gameId(gameId)
                .userId(userId)
                .build())
                .enqueue(object : ApolloCall.Callback<JoinGameMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { callBack.onErrorResponse(it) }
                    }

                    override fun onResponse(response: Response<JoinGameMutation.Data>) {
                        if (response.hasErrors())
                            callBack.onErrorResponse(response.errors().toString())
                        else
                            callBack.onOkResponse(response.data().toString())
                    }
                })
    }

    fun leaveGame(userId: String, gameId: String, callBack: HttpResponseCallBack<String>) {
        httpRequestClients.getApolloClient().mutate(LeaveGameMutation.builder()
                .gameId(gameId)
                .userId(userId)
                .build())
                .enqueue(object : ApolloCall.Callback<LeaveGameMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { callBack.onErrorResponse(it) }
                    }

                    override fun onResponse(response: Response<LeaveGameMutation.Data>) {
                        if (response.hasErrors())
                            callBack.onErrorResponse(response.errors().toString())
                        else
                            callBack.onOkResponse(response.data().toString())
                    }
                })
    }
}