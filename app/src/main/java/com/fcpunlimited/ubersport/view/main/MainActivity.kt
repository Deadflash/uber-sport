package com.fcpunlimited.ubersport.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.UsersQuery
import com.fcpunlimited.ubersport.di.GraphQlClient
import com.fcpunlimited.ubersport.di.GraphQlClientImpl
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.CREATE_EVENT_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.PROFILE_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class MainActivity : BaseMvpActivity(), MainView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MAIN_PRESENTER")
    lateinit var presenter: MainActivityPresenter


    private val apolloClient: GraphQlClient by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        presenter.initFragments()

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            presenter.showFragmentByTag(SEARCH_FRAGMENT_TAG)
        }

//        apolloClient = setupApollo()

        apolloClient.getApolloClient().query(UsersQuery.builder()
                .build())
                .enqueue(object : ApolloCall.Callback<UsersQuery.Data>() {
                    override fun onFailure(e: ApolloException) {
                        toast("Error")
                    }

                    override fun onResponse(response: Response<UsersQuery.Data>) {
                        if (response.hasErrors())
                            response.errors()[0].message()?.let { toast(it) }
                        response.data()?.users()?.get(0)?.nickname()?.let { toast(it) }
                    }

                })
    }

//    private fun setupApollo(): ApolloClient {
//        val okHttp = OkHttpClient
//                .Builder()
//                .addInterceptor { chain ->
//                    val original = chain.request()
//                    val builder = original.newBuilder().method(original.method(),
//                            original.body())
//                    //                    builder.addHeader("Authorization"
//                    //                            , "Bearer " + BuildConfig.AUTH_TOKEN)
//                    chain.proceed(builder.build())
//                }
//                .build()
//        return ApolloClient.builder()
//                .serverUrl("http://142.93.98.160/graphql")
//                .okHttpClient(okHttp)
//                .build()
//    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView
            .OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_search -> {
                        presenter.showFragmentByTag(SEARCH_FRAGMENT_TAG)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_create -> {
                        presenter.showFragmentByTag(CREATE_EVENT_FRAGMENT_TAG)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_profile -> {
                        presenter.showFragmentByTag(PROFILE_FRAGMENT_TAG)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }

    override fun replaceFragmentAndMenu(fragment: BaseMvpFragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    override fun onAttachFragment(fragment: Fragment?) {
//        toolbar?.menu?.clear()
//        (fragment as BaseMvpFragment).getFragmentMenu()
//                ?.let { it1 -> toolbar?.inflateMenu(it1) }
        super.onAttachFragment(fragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        supportFragmentManager.findFragmentById(R.id.fragment_container)
                ?.let {
                    (it as BaseMvpFragment).getFragmentMenu()
                            ?.let { menuLayout -> menuInflater.inflate(menuLayout, menu) }
                }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_filter -> {
                    toast("filter")
                    true
                }
                R.id.action_create_event -> {
                    toast("createEvent")
                    true
                }
                else -> {
                    toast("Unknown item clicked")
                    false
                }
            }
}
