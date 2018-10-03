package com.fcpunlimited.ubersport.view.login

import android.content.Intent
import android.os.Bundle
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.singleTop

class LoginActivity : BaseMvpActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_password.error = " test error"

        bt_login.onClick {
            startActivity(Intent(this@LoginActivity,
                    MainActivity::class.java).singleTop())
            finish()
        }
    }
}
