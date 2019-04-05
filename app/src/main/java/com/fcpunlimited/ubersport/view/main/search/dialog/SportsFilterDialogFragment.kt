package com.fcpunlimited.ubersport.view.main.search.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.GamesLiveDataContainer
import com.fcpunlimited.ubersport.struct.game.SportFilterDto
import com.fcpunlimited.ubersport.utils.Constants.SPORT_FILTER_DIALOG_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.main.search.IUpdateFilterView
import com.fcpunlimited.ubersport.view.main.search.SearchFragment
import kotlinx.android.synthetic.main.dialog_fragment_sports_filter.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject


class SportsFilterDialogFragment : BaseMvpFragment(), SportsFilterDialogView {

    private val gamesLiveDataContainer: GamesLiveDataContainer by inject()
    private val gameModel: GameModel by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "SPORTS_FILTER_DIALOG_PRESENTER")
    lateinit var presenter: SportsFilterDialogPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "SPORTS_FILTER_DIALOG_PRESENTER")
    fun providePresenter() = SportsFilterDialogPresenter(gameModel, gamesLiveDataContainer)

    private var searchViewInterface: IUpdateFilterView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        searchViewInterface = targetFragment as SearchFragment
    }

    override fun onDetach() {
        super.onDetach()
        searchViewInterface = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_sports_filter, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(presenter)
        val adapter = CustomAdapter()
        lifecycle.addObserver(adapter)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)

        gamesLiveDataContainer.sportsData.observe(this, Observer {
            adapter.setData(it.map { sportDto -> SportFilterDto(sportDto) }.toCollection(arrayListOf()))
        })
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        bt_dialog_ok.setOnClickListener {
            presenter.getGames()
            searchViewInterface?.updateSportsFilter()
            dialog.dismiss()
        }
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun getFragmentLayout(): Int = R.layout.dialog_fragment_sports_filter

    override fun getFragmentTag(): String = SPORT_FILTER_DIALOG_FRAGMENT_TAG
}