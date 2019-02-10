package com.fcpunlimited.ubersport.di

import com.fcpunlimited.ubersport.UsersQuery

interface UserModelCallBack {
    fun onOkUsersResponse(users: MutableList<UsersQuery.User>)

    fun onErrorResponse()
}