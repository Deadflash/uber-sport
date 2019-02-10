package com.fcpunlimited.ubersport.di.gql

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class GraphQlClientImpl: GraphQlClient {

    private var apolloClient: ApolloClient

    init {
        this.apolloClient = setupApollo()
    }

    private fun setupApollo(): ApolloClient {
        println("test")
        val okHttp = OkHttpClient
                .Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val builder = original.newBuilder().method(original.method(),
                            original.body())
                    //                    builder.addHeader("Authorization"
                    //                            , "Bearer " + BuildConfig.AUTH_TOKEN)
                    chain.proceed(builder.build())
                }
                .build()
        return ApolloClient.builder()
                .serverUrl("http://142.93.98.160/graphql")
                .okHttpClient(okHttp)
                .build()
    }

    override fun getApolloClient(): ApolloClient = apolloClient
}