package com.fcpunlimited.ubersport.view.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.login.LoginActivity
import com.fcpunlimited.ubersport.view.main.MainActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject


class Splash : BaseMvpActivity(), SplashView {

    private val userModel: UserModel by inject()
    private val gameModel: GameModel by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "SPLASH_PRESENTER")
    lateinit var splashPresenter: SplashPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "SPLASH_PRESENTER")
    fun providePresenter() = SplashPresenter(userModel, gameModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setAuthorized(isAuthorized: Boolean) {
        startActivity(Intent(this,
                if (isAuthorized) MainActivity::class.java else LoginActivity::class.java))
        hideKeyboard(this)
        finish()
    }

    override fun showMessage(message: String) {
        toast(message)
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
