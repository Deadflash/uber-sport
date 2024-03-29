package com.fcpunlimited.ubersport.di.game

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.JoinGameMutation
import com.fcpunlimited.ubersport.LeaveGameMutation
import com.fcpunlimited.ubersport.SportsQuery
import com.fcpunlimited.ubersport.di.api.HttpRequestClients
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.struct.game.ActiveGamesDto
import com.fcpunlimited.ubersport.struct.game.GameDto
import com.fcpunlimited.ubersport.struct.game.GamesHistoryDto
import com.fcpunlimited.ubersport.type.GameFiltersInput
import com.fcpunlimited.ubersport.type.GameStatus

class GameModel(private val httpRequestClients: HttpRequestClients,
                private val gamesLiveDataContainer: GamesLiveDataContainer,
                private val gameFilterContainer: GameFilterContainer,
                private val userModel: UserModel,
                private val activeGamesLiveDataContainer: ActiveGamesLiveDataContainer,
                private val gamesHistoryLiveDataContainer: GamesHistoryLiveDataContainer) {

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
                            gamesLiveDataContainer.gameData.postValue(games)
                            httpEmptyCallback.onResponse()
                        }
                    }
                })
    }

    fun getActiveGames(httpEmptyCallback: HttpEmptyResponseCallBack) {
        val activeGamesFilter = GameFiltersInput.builder()
                .status(GameStatus.PENDING)
                .participantsIds(arrayListOf(userModel.getUserId()))
                .build()
        httpRequestClients.getApolloClient().query(GamesQuery.builder()
                .filters(activeGamesFilter)
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
                                    ?.map { game -> ActiveGamesDto(game.fragments().gameFragment()) }
                                    ?.toCollection(arrayListOf())
                            activeGamesLiveDataContainer.activeGamesData.postValue(games)
                            httpEmptyCallback.onResponse()
                        }
                    }

                })
    }

    fun getGamesHistory(httpEmptyCallback: HttpEmptyResponseCallBack) {
        val gamesHistoryFilter = GameFiltersInput.builder()
                .status(GameStatus.FINISHED)
                .participantsIds(arrayListOf(userModel.getUserId()))
                .build()
        httpRequestClients.getApolloClient().query(GamesQuery.builder()
                .filters(gamesHistoryFilter)
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
                                    ?.map { game -> GamesHistoryDto(game.fragments().gameFragment()) }
                                    ?.toCollection(arrayListOf())
                            gamesHistoryLiveDataContainer.activeGamesData.postValue(games)
                            httpEmptyCallback.onResponse()
                        }
                    }

                })
    }

    fun getSports(httpEmptyCallback: HttpEmptyResponseCallBack) {
        httpRequestClients.getApolloClient().query(SportsQuery.builder()
                .build())
                .enqueue(object : ApolloCall.Callback<SportsQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        e.message?.let { httpEmptyCallback.onFailure(it) }
                    }

                    override fun onResponse(response: Response<SportsQuery.Data>) {
                        if (response.hasErrors()) {
                            httpEmptyCallback.onFailure(response.errors().toString())
                        } else {
                            gamesLiveDataContainer.sportsData.postValue(response.data()?.sports())
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

    fun excludeParticipant(gameId: String, userId: String, callBack: HttpResponseCallBack<LeaveGameMutation.Data>) {
        httpRequestClients.getApolloClient().mutate(LeaveGameMutation.builder()
                .gameId(gameId)
                .userId(userId)
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