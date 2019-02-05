package com.fcpunlimited.ubersport.di

import com.apollographql.apollo.ApolloClient

interface GraphQlClient {
    fun getApolloClient(): ApolloClient
}