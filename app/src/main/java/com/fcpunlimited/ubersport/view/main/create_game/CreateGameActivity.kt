package com.fcpunlimited.ubersport.view.main.create_game

import android.os.Bundle
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_create_game.*

class CreateGameActivity : BaseMvpActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        fab.setOnClickListener { (finish()) }
    }
}
