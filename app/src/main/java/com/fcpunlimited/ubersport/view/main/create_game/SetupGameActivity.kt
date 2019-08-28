package com.fcpunlimited.ubersport.view.main.create_game

import android.os.Bundle
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_create_game.*

class SetupGameActivity : BaseMvpActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CreateGameFragment()).commit()
    }
}
