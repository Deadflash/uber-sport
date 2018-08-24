package com.fcpunlimited.ubersport.utils

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log

class BottomNavHelper {

    companion object {
        @SuppressLint("RestrictedApi")
        fun disableShiftMode(view: BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                val shiftMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftMode.isAccessible = true
                shiftMode.setBoolean(menuView, false)
                shiftMode.isAccessible = false
                for (i: Int in 0 until menuView.childCount) {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShiftingMode(false)
                    item.setChecked(item.itemData.isChecked)
                }
            } catch (e: NoSuchFieldException) {
                Log.e("BNVHelper", "Unable to get shift mode field", e)
            } catch (e: IllegalAccessException) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e)
            }
        }
    }
}