package com.fcpunlimited.ubersport.di.user

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.fcpunlimited.ubersport.UsersQuery
import com.fcpunlimited.ubersport.di.gql.GraphQlResponseCallback
import com.fcpunlimited.ubersport.di.gql.GraphQlClient

class UserModel(private val graphQlClient: GraphQlClient) {

    fun getUsers(callBack: GraphQlResponseCallback<UsersQuery.Data>) {
        graphQlClient.getApolloClient().query(UsersQuery.builder()
                .build())
                .enqueue(object : ApolloCall.Callback<UsersQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        callBack.onErrorResponse()
                    }

                    override fun onResponse(response: Response<UsersQuery.Data>) {
                        if (response.hasErrors())
                            callBack.onErrorResponse()
                        else
                            callBack.onOkResponse(response.data()!!)
                    }

                })
    }
}