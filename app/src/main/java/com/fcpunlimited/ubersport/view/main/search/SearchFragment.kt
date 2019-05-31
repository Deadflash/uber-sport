package com.fcpunlimited.ubersport.view.main.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.SportsQuery
import com.fcpunlimited.ubersport.di.game.GameFilterContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.GamesLiveDataContainer
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.struct.game.GameDto
import com.fcpunlimited.ubersport.struct.game.GameDtoDiffUtilCallback
import com.fcpunlimited.ubersport.utils.Constants.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import com.fcpunlimited.ubersport.view.adapters.INavigation
import com.fcpunlimited.ubersport.view.main.MainActivity
import com.fcpunlimited.ubersport.view.main.search.dialog.SportsFilterDialogFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.swipe_refresh_recycler_container.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class SearchFragment : BaseMvpFragment(), SearchView, INavigation, IUpdateFilterView {

    companion object {
        const val SEARCH_PRESENTER = "searchPresenter"
    }

    private val gameModel: GameModel by inject()
    private val gamesLiveDataContainer: GamesLiveDataContainer by inject()
    private val filter: GameFilterContainer by inject()
    private var gameProvider: IGameShare.IGameProvider? = null

    @InjectPresenter(type = PresenterType.GLOBAL, tag = SEARCH_PRESENTER)
    lateinit var presenter: SearchPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = SEARCH_PRESENTER)
    fun providePresenter() = SearchPresenter(gameModel, gamesLiveDataContainer, filter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh.setOnRefreshListener {
            presenter.loadGames()
        }

        sports_layout.setOnClickListener {
            val dialog = SportsFilterDialogFragment()
            dialog.setTargetFragment(this, 1)
            dialog.show(fragmentManager, dialog.tag)
        }
        sorting_layout.setOnClickListener { context?.toast("Sorting filter") }
//        iv_filters.setOnClickListener { context?.toast("Other filters") }

        CustomAdapter().apply {
            lifecycle.addObserver(this)

            recycler.layoutManager = LinearLayoutManager(this@SearchFragment.context)
            recycler.adapter = this
            recycler.setHasFixedSize(true)

            gamesLiveDataContainer.gameData.observe(this@SearchFragment, Observer<List<GameDto>> {
                val gameDtoDiffUtilCallback =
                        GameDtoDiffUtilCallback(getData() as List<GameDto>, it)
                setData(it as ArrayList<IListItem>)
                DiffUtil.calculateDiff(gameDtoDiffUtilCallback).dispatchUpdatesTo(this)
            })
            gamesLiveDataContainer.sportsData.observe(this@SearchFragment, Observer<List<SportsQuery.Sport>> {
                presenter.updateUserSportFilterCount(it)
            })
        }
    }

    override fun showSportsFilterCount(filterSportCount: Int) {
//        context?.runOnUiThread {
            tv_sports_filter_count.text = "$filterSportCount видов"
//        }
    }

    override fun updateSportsFilterCount() {
        gamesLiveDataContainer.sportsData.value?.let {
            presenter.updateUserSportFilterCount(it)
        }
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun onSwipeRefresh(isRefreshing: Boolean) {
        swipe_refresh.isRefreshing = isRefreshing
    }

    override fun navigate(game: GameFragment) {
        gameProvider?.provideGame(game)
        findNavController().navigate(R.id.action_create_game_to_description_game)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity)
            gameProvider = context
    }

    override fun onDetach() {
        super.onDetach()
        gameProvider = null
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_search

    override fun getFragmentTag(): String = SEARCH_FRAGMENT_TAG

    override fun getFragmentMenu(): Int = R.menu.search_menu
}
