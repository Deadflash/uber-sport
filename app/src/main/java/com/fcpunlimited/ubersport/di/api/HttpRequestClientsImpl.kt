package com.fcpunlimited.ubersport.di.api

import com.apollographql.apollo.ApolloClient
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://142.93.98.160/"

class HttpRequestClientsImpl : HttpRequestClients {

    private var okHttpClient: OkHttpClient
    private var retrofitApi: RetrofitApi
    private var apolloClient: ApolloClient
    private var token: String? = null

    init {
        this.okHttpClient = setupOkHttpClient()
        this.retrofitApi = setupRetrofit()
        this.apolloClient = setupApollo()
    }

    private fun setupOkHttpClient(): OkHttpClient {
        return OkHttpClient
                .Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val builder = original.newBuilder().method(original.method(),
                            original.body())
                    builder.addHeader("Authorization"
                            , "Bearer $token")
                    chain.proceed(builder.build())
                }
                .build()
    }

    private fun setupRetrofit(): RetrofitApi {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()


        return retrofit.create(RetrofitApi::class.java)
    }

    private fun setupApollo(): ApolloClient {

        return ApolloClient.builder()
                .serverUrl("${BASE_URL}graphql")
                .okHttpClient(okHttpClient)
                .build()
    }

    override fun checkToken() {
        if (token == null){
            retrofitApi.getToken()
        }
    }

    override fun setToken(token: String) {
        this.token = token
    }

    override fun getApolloClient(): ApolloClient = apolloClient

    override fun getRetrofitApi(): RetrofitApi = retrofitApi
}