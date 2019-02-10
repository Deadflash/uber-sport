package com.fcpunlimited.ubersport.view.main.search

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.UsersQuery
import com.fcpunlimited.ubersport.di.UserModelCallBack
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.view.main.MainActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

@InjectViewState
class SearchPresenter(val userModel: UserModel) : MvpPresenter<SearchView>(), UserModelCallBack {

    fun onSwipeRefresh() {
        viewState.onSwipeRefresh(true)
        doAsync {
            Thread.sleep(1000)
            userModel.getUsers(this@SearchPresenter)
            uiThread {
                viewState.onSwipeRefresh(false)
            }
        }
    }

    override fun onOkUsersResponse(users: MutableList<UsersQuery.User>) {
        println(users.size)
        viewState.test(users.size.toString())
    }

    override fun onErrorResponse() {
        println("error")
        viewState.test("error")
    }
}