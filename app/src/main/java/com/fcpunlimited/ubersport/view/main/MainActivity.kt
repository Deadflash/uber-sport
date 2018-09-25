package com.fcpunlimited.ubersport.view.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.main.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        navigation.disableShiftMode()

        fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = SearchFragment.newInstance("", "")
            replaceFragment(fragment as SearchFragment)
        }
    }

//    @SuppressLint("RestrictedApi")
//    fun BottomNavigationView.disableShiftMode() {
//        val menuView = getChildAt(0) as BottomNavigationMenuView
//        try {
//            val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
//            shiftingMode.isAccessible = true
//            shiftingMode.setBoolean(menuView, false)
//            shiftingMode.isAccessible = false
//            for (i in 0 until menuView.childCount) {
//                val item = menuView.getChildAt(i) as BottomNavigationItemView
//                item.setShifting(false)
//                // set once again checked value, so view will be updated
//                item.setChecked(item.itemData.isChecked)
//            }
//        } catch (e: NoSuchFieldException) {
//            Log.e("BNV_HELPER", "Unable to get shift mode field", e)
//        } catch (e: IllegalStateException) {
//            Log.e("BNV_HELPER", "Unable to change value of shift mode", e)
//        }
//    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_create -> {
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_statistics -> {
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.navigation_profile -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
