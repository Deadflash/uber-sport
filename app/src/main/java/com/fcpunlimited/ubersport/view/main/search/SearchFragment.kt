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
import com.fcpunlimited.ubersport.di.game.GameContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.struct.game.GameDto
import com.fcpunlimited.ubersport.struct.game.GameDtoDiffUtilCallback
import com.fcpunlimited.ubersport.utils.Constants.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.toDp
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import com.fcpunlimited.ubersport.view.adapters.INavigation
import com.fcpunlimited.ubersport.view.main.MainActivity
import kotlinx.android.synthetic.main.swipe_refresh_recycler_container.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : BaseMvpFragment(), SearchView, INavigation {

    private val gameModel: GameModel by inject()
    private val gameContainer: GameContainer by inject()
    private var gameProvider: IGameShare.IGameProvider? = null

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "SEARCH_PRESENTER")
    lateinit var presenter: SearchPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "SEARCH_PRESENTER")
    fun providePresenter() = SearchPresenter(gameModel, gameContainer)

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        lifecycle.addObserver(presenter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh.apply {
            setProgressViewOffset(true, 0.toDp, 84.toDp)
            setOnRefreshListener {
                presenter.loadGames()
            }
        }

        CustomAdapter().apply {
            lifecycle.addObserver(this)

            recycler.layoutManager = LinearLayoutManager(this@SearchFragment.context)
            recycler.adapter = this
            recycler.setHasFixedSize(true)

            gameContainer.gameData.observe(this@SearchFragment, Observer<List<GameDto>> {
                val gameDtoDiffUtilCallback =
                        GameDtoDiffUtilCallback(getData() as ArrayList<GameDto>, it)
                setData(it as ArrayList<IListItem>)
                DiffUtil.calculateDiff(gameDtoDiffUtilCallback).dispatchUpdatesTo(this)
            })
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
