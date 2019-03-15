package com.fcpunlimited.ubersport.di.game

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.di.gql.GraphQlClient
import com.fcpunlimited.ubersport.di.gql.GraphQlResponseCallback
import com.fcpunlimited.ubersport.type.GameFiltersInput

class GameModel(private val graphQlClient: GraphQlClient) {

    fun getGames(filters: GameFiltersInput, callBack: GraphQlResponseCallback<GamesQuery.Data>) {
        graphQlClient.getApolloClient().query(GamesQuery.builder()
                .filters(filters)
                .build())
                .enqueue(object : ApolloCall.Callback<GamesQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        callBack.onErrorResponse()
                    }

                    override fun onResponse(response: Response<GamesQuery.Data>) {
                        if (response.hasErrors())
                            callBack.onErrorResponse()
                        else
                            callBack.onOkResponse(response.data()!!)
                    }
                })
    }
}