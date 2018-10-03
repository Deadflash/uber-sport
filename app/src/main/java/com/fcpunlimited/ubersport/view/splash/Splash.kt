package com.fcpunlimited.ubersport.view.splash

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.login.LoginActivity
import com.fcpunlimited.ubersport.view.main.MainActivity
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


class Splash : BaseMvpActivity(), SplashView {

    @InjectPresenter
    lateinit var splashPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate().onAttach()
    }

    override fun setAuthorized(isAuthorized: Boolean) {
        startActivity(Intent(this,
                if (isAuthorized) MainActivity::class.java else LoginActivity::class.java))
        hideKeyboard(this)
        finish()
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
