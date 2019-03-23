package com.fcpunlimited.ubersport.view.main.create_game

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack
import com.fcpunlimited.ubersport.struct.game.SportDto
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import kotlinx.android.synthetic.main.activity_create_game.*
import kotlinx.android.synthetic.main.recycler_container.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class CreateGameActivity : BaseMvpActivity() {

    private val gameContainer: GameContainer by inject()
    private val gameModel: GameModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        val sports = gameContainer.sportsData.value
        if (sports == null || sports.isEmpty()) {
            gameModel.getSports(object : HttpEmptyResponseCallBack {
                override fun onFailure(message: String) {
                    runOnUiThread { toast(message) }
                }
            })
        }

        gameContainer.sportsData.observe(this, Observer {
            val adapter = CustomAdapter()
            lifecycle.addObserver(adapter)
            adapter.setData(it.map { sport -> SportDto(sport) }.toCollection(arrayListOf()))

            recycler.layoutManager = GridLayoutManager(this, 2)
            recycler.adapter = adapter
            recycler.setHasFixedSize(true)
        })

        fab.setOnClickListener { finish() }
    }
}
