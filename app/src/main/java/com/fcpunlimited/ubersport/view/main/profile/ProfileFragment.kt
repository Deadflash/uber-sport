package com.fcpunlimited.ubersport.view.main.profile


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.ActiveGamesLiveDataContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.struct.game.ActiveGameDto
import com.fcpunlimited.ubersport.utils.Constants.PROFILE_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import com.fcpunlimited.ubersport.view.main.search.description.DescriptionPresenter
import kotlinx.android.synthetic.main.swipe_refresh_recycler_container.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject


class ProfileMvpFragment : BaseMvpFragment(), ProfileView {

    private val gameModel: GameModel by inject()
    private val activeGamesLiveDataContainer: ActiveGamesLiveDataContainer by inject()

    companion object {
        const val PROFILE_PRESENTER = "profilePresenter"
    }

    @InjectPresenter(type = PresenterType.GLOBAL, tag = PROFILE_PRESENTER)
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = PROFILE_PRESENTER)
    fun providePresenter() = ProfilePresenter(gameModel, activeGamesLiveDataContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CustomAdapter()
        recycler.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)

        activeGamesLiveDataContainer.activeGamesData.observe(this@ProfileMvpFragment, Observer<List<ActiveGameDto>> {
            adapter.setData(it as ArrayList<IListItem>)
            adapter.notifyDataSetChanged()
        })
    }

    override fun showActiveGames() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread { toast(message) }
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_profile

    override fun getFragmentTag(): String = PROFILE_FRAGMENT_TAG
}
