package com.fcpunlimited.ubersport.view.main.create_game

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.GamesLiveDataContainer
import com.fcpunlimited.ubersport.struct.game.SportDto
import com.fcpunlimited.ubersport.utils.Constants.CHOOSE_GAME_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.main.create_game.dialog.DateDialogFragment
import kotlinx.android.synthetic.main.fragment_choose_sport.*
import kotlinx.android.synthetic.main.recycler_container.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class ChooseSportFragment : BaseMvpFragment(), DateDialogFragment.IDateSelector, ChooseSportView {

    private val gamesLiveDataContainer: GamesLiveDataContainer by inject()
    private val gameModel: GameModel by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "CHOOSE_SPORT_FRAGMENT_PRESENTER")
    lateinit var presenter: ChooseSportPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "CHOOSE_SPORT_FRAGMENT_PRESENTER")
    fun providePresenter() = ChooseSportPresenter(gameModel, gamesLiveDataContainer)

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(presenter)
        bt_date_picker.setOnClickListener { DateDialogFragment().show(childFragmentManager, "date_picker") }
        val adapter = CustomAdapter()
        lifecycle.addObserver(adapter)
        recycler.layoutManager = GridLayoutManager(context, 2)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)

        gamesLiveDataContainer.sportsData.observe(this, Observer {
            adapter.setData(it.map { sport -> SportDto(sport) }.toCollection(arrayListOf()))
        })
    }

    override fun dateSelected(date: String) {
        context?.toast(date)
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_choose_sport

    override fun getFragmentTag(): String = CHOOSE_GAME_FRAGMENT_TAG
}
