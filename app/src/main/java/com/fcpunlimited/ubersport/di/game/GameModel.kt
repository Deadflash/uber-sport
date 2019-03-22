package com.fcpunlimited.ubersport.di.game

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.JoinGameMutation
import com.fcpunlimited.ubersport.LeaveGameMutation
import com.fcpunlimited.ubersport.di.api.HttpRequestClients
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.struct.game.GameDto

class GameModel(private val httpRequestClients: HttpRequestClients,
                private val gameContainer: GameContainer,
                private val gameFilterContainer: GameFilterContainer,
                private val userModel: UserModel) {

    fun getGames(httpEmptyCallback: HttpEmptyResponseCallBack) {
        httpRequestClients.getApolloClient().query(GamesQuery.builder()
                .filters(gameFilterContainer.getFilter())
                .build())
                .enqueue(object : ApolloCall.Callback<GamesQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { httpEmptyCallback.onFailure(it) }
                    }

                    override fun onResponse(response: Response<GamesQuery.Data>) {
                        if (response.hasErrors()) {
                            httpEmptyCallback.onFailure(response.errors().toString())
                        } else {
                            val games = response.data()?.games()?.games()
                                    ?.map { game -> GameDto(game.fragments().gameFragment()) }
                                    ?.toCollection(arrayListOf())
                            gameContainer.gameData.postValue(games)
                            httpEmptyCallback.onResponse()
                        }
                    }
                })
    }

    fun joinGame(gameId: String, callBack: HttpResponseCallBack<JoinGameMutation.Data>) {
        httpRequestClients.getApolloClient().mutate(JoinGameMutation.builder()
                .gameId(gameId)
                .userId(userModel.getUserId())
                .build())
                .enqueue(object : ApolloCall.Callback<JoinGameMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { callBack.onFailure(it) }
                    }

                    override fun onResponse(response: Response<JoinGameMutation.Data>) {
                        if (response.hasErrors())
                            callBack.onFailure(response.errors().toString())
                        else
                            callBack.onResponse(response.data()!!)
                    }
                })
    }

    fun leaveGame(gameId: String, callBack: HttpResponseCallBack<LeaveGameMutation.Data>) {
        httpRequestClients.getApolloClient().mutate(LeaveGameMutation.builder()
                .gameId(gameId)
                .userId(userModel.getUserId())
                .build())
                .enqueue(object : ApolloCall.Callback<LeaveGameMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { callBack.onFailure(it) }
                    }

                    override fun onResponse(response: Response<LeaveGameMutation.Data>) {
                        if (response.hasErrors())
                            callBack.onFailure(response.errors().toString())
                        else
                            callBack.onResponse(response.data()!!)
                    }
                })
    }
}