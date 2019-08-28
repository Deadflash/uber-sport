package com.fcpunlimited.ubersport.view.main.search.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameFilterContainer
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

class SportsFilterDialogFragment : BaseMvpFragment(), SportsFilterDialogView, ISportsFilter {

    companion object {
        const val SPORT_FILTER_DIALOG_PRESENTER = "sportFilterDialogPresenter"
    }

    private val gamesLiveDataContainer: GamesLiveDataContainer by inject()
    private val gameModel: GameModel by inject()
    private val filter: GameFilterContainer by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = SPORT_FILTER_DIALOG_PRESENTER)
    lateinit var presenter: SportsFilterDialogPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = SPORT_FILTER_DIALOG_PRESENTER)
    fun providePresenter() = SportsFilterDialogPresenter(gameModel, gamesLiveDataContainer)

    private var searchViewInterface: IUpdateFilterView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchViewInterface = targetFragment as SearchFragment
    }

    override fun onDetach() {
        super.onDetach()
        searchViewInterface = null
    }

//    override fun onResume() {
//        super.onResume()
//
//        val window = dialog.window
//        val size = Point()
//
//        val display = window!!.windowManager.defaultDisplay
//        display.getSize(size)
//
//        val width = size.x
//
//        window.setLayout((width * 0.75).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
//        window.setGravity(Gravity.CENTER)
//    }


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
//        dialog.setCanceledOnTouchOutside(false)
//        bt_dialog_ok.setOnClickListener {
//            dialog.dismiss()
//        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        presenter.getGames()
        searchViewInterface?.updateSportsFilterCount()
    }

    override fun addFilterSport(sportId: String) {
        filter.addUserFilterSportId(sportId)
    }

    override fun removeFilterSportId(sportId: String) {
        filter.removeUserFilterSportId(sportId)
    }

    override fun getFilteredSports(): Set<String> {
        return filter.getUserFilterSportIds()
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun getFragmentLayout(): Int = R.layout.dialog_fragment_sports_filter

    override fun getFragmentTag(): String = SPORT_FILTER_DIALOG_FRAGMENT_TAG
}