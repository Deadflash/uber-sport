package com.fcpunlimited.ubersport.utils

import android.content.res.Resources

val Int.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

enum class SportType{
    Football, Basketball, Volleyball, Paintball, Tennis, Bicycle
}