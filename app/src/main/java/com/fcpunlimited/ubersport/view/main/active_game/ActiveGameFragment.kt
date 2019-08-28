package com.fcpunlimited.ubersport.view.main.active_game

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.ActiveGamesLiveDataContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.struct.game.ActiveGamesDto
import com.fcpunlimited.ubersport.utils.Constants.ACTIVE_GAME_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import kotlinx.android.synthetic.main.fragment_active_game.*
import kotlinx.android.synthetic.main.swipe_refresh_recycler_container.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class ActiveGameFragment : BaseMvpFragment(), ActiveGameView {

    companion object {
        const val ACTIVE_GAME_PRESENTER = "activeGamePresenter"
    }

    private val gameModel: GameModel by inject()
    private val activeGamesLiveDataContainer: ActiveGamesLiveDataContainer by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ACTIVE_GAME_PRESENTER)
    lateinit var presenter: ActiveGamePresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = ACTIVE_GAME_PRESENTER)
    fun providePresenter() = ActiveGamePresenter(gameModel, activeGamesLiveDataContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setTitle(R.string.active_games)
        toolbar.setTitleTextColor(ContextCompat.getColor(context!!, R.color.white))

//        val adapter = CustomAdapter()
//        recycler.layoutManager = LinearLayoutManager(this.context)
//        recycler.adapter = adapter
//        recycler.setHasFixedSize(true)
//
//        activeGamesLiveDataContainer.activeGamesData.observe(this@ActiveGameFragment, Observer<List<ActiveGamesDto>> {
//            if (it.isNotEmpty())
//                tv_active_games_header.visibility = View.GONE
//
//            adapter.setData(it as ArrayList<IListItem>)
//            adapter.notifyDataSetChanged()
//        })
//
//        swipe_refresh.setOnRefreshListener {
//            presenter.onCreateProfileView()
//        }
    }

    override fun onSwipeRefresh(isRefreshing: Boolean) {
        swipe_refresh.isRefreshing = isRefreshing
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_active_game

    override fun getFragmentTag(): String = ACTIVE_GAME_FRAGMENT_TAG
}
