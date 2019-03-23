package com.fcpunlimited.ubersport.view.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.main.create_game.CreateGameActivity
import com.fcpunlimited.ubersport.view.main.search.IGameShare
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseMvpActivity(), MainView, IGameShare.IGameProvider, IGameShare.IGameConsumer {


    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MAIN_PRESENTER")
    lateinit var presenter: MainActivityPresenter

    private var currentNavController: LiveData<NavController>? = null

    private var game: GameFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(presenter)

        val navGraphIds = listOf(R.navigation.search_nav_graph,
                R.navigation.create_game_nav_graph, R.navigation.profile_nav_graph)

        val controller = navigation.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.main_nav_host_fragment,
                intent = intent
        )

        currentNavController = controller
        fab.setOnClickListener {startActivity(Intent(this,
                CreateGameActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))}
    }

    override fun provideGame(game: GameFragment) {
        this.game = game
    }

    override fun consumeGame(): GameFragment? = game

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    /**
     * Overriding popBackStack is necessary in this case if the app is started from the deep link.
     */
    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }
}
