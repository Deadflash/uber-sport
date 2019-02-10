package com.fcpunlimited.ubersport.di.gql

import com.apollographql.apollo.ApolloClient

interface GraphQlClient {
    fun getApolloClient(): ApolloClient
}