package com.fcpunlimited.ubersport.di.api

import com.apollographql.apollo.ApolloClient

interface HttpRequestClients {
    fun getRetrofitApi(): RetrofitApi
    fun getApolloClient(): ApolloClient
    fun checkToken()
}