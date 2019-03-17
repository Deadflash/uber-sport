package com.fcpunlimited.ubersport.view.main.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.struct.game.GameDto
import com.fcpunlimited.ubersport.struct.game.GameDtoDiffUtilCallback
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.toDp
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import com.fcpunlimited.ubersport.view.adapters.INavigation
import com.fcpunlimited.ubersport.view.description.DescriptionActivityParcel
import kotlinx.android.synthetic.main.swipe_refresh_recycler_container.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchMvpFragment : BaseMvpFragment(), SearchView, INavigation {

    private val gameModel: GameModel by inject()
    private val descriptionActivityParcel: DescriptionActivityParcel by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "SEARCH_PRESENTER")
    lateinit var presenter: SearchPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "SEARCH_PRESENTER")
    fun providePresenter() = SearchPresenter(gameModel)

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SearchMvpFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

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

        swipe_refresh.setProgressViewOffset(true, 0.toDp, 84.toDp)
        swipe_refresh.setOnRefreshListener {
            presenter.onSwipeRefresh()
        }

        val adapter = CustomAdapter()
        lifecycle.addObserver(adapter)

        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)

        presenter.getGameData().observe(this, Observer<List<GameDto>> {
            val gameDtoDiffUtilCallback =
                    GameDtoDiffUtilCallback(adapter.getData() as ArrayList<GameDto>, it)
            adapter.setData(it as ArrayList<IListItem>)
            DiffUtil.calculateDiff(gameDtoDiffUtilCallback).dispatchUpdatesTo(adapter)
        })
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun onSwipeRefresh(isRefreshing: Boolean) {
        swipe_refresh.isRefreshing = isRefreshing
    }

    override fun navigate(game: GamesQuery.Game) {
        descriptionActivityParcel.game = game
        Navigation.findNavController(recycler).navigate(R.id.action_searchMvpFragment_to_descriptionActivity)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_search

    override fun getFragmentTag(): String = SEARCH_FRAGMENT_TAG

    override fun getFragmentMenu(): Int = R.menu.search_menu
}
