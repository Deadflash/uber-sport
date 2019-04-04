package com.fcpunlimited.ubersport.view.main.search.dialog

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
import com.fcpunlimited.ubersport.di.game.GameContainer
import com.fcpunlimited.ubersport.di.game.GameFilterContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack
import com.fcpunlimited.ubersport.struct.game.SportFilterDto
import com.fcpunlimited.ubersport.view.BaseDialogFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import kotlinx.android.synthetic.main.fragment_sports_filter_dialog.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject


class SportsFilterDialogFragment : BaseDialogFragment(), SportsFilterDialogView {

    private val filter: GameFilterContainer by inject()
    private val gameContainer: GameContainer by inject()
    private val gameModel: GameModel by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "SPORTS_FILTER_DIALOG_PRESENTER")
    lateinit var presenter: SportsFilterDialogPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "SPORTS_FILTER_DIALOG_PRESENTER")
    fun providePresenter() = SportsFilterDialogPresenter(gameModel)

    fun getFilter() = filter.getFilter()
    fun getFilterBuilder() = filter.getFilterBuilderL()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sports_filter_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sports = gameContainer.sportsData.value
        if (sports == null || sports.isEmpty()) {
            gameModel.getSports(object : HttpEmptyResponseCallBack {
                override fun onFailure(message: String) {
                    context?.runOnUiThread { toast(message) }
                }
            })
        }
        val adapter = CustomAdapter()
        lifecycle.addObserver(adapter)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)

        gameContainer.sportsData.observe(this, Observer {
            adapter.setData(it.map { sport -> SportFilterDto(sport) }.toCollection(arrayListOf()))
        })

        bt_dialog_ok.setOnClickListener {
            presenter.getGames()
            dialog.dismiss()
        }
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_sports_filter_dialog

    override fun getFragmentTag(): String = "sport_filter_dialog_fragment"
}