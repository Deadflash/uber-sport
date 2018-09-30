package com.fcpunlimited.ubersport.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpDelegate

open class BaseMvpActivity: AppCompatActivity(){

    private var mMvpDelegate: MvpDelegate<out BaseMvpActivity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        getMvpDelegate().onAttach()
    }

    override fun onResume() {
        super.onResume()

        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()

        getMvpDelegate().onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        getMvpDelegate().onDestroyView()

        if (isFinishing) {
            getMvpDelegate().onDestroy()
        }
    }

    private fun getMvpDelegate(): MvpDelegate<*> {
        if (mMvpDelegate == null) {
            mMvpDelegate = MvpDelegate<BaseMvpActivity>(this)
        }
        return mMvpDelegate as MvpDelegate<out BaseMvpActivity>
    }
}