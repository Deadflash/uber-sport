package com.fcpunlimited.ubersport.view.main.search.description


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.utils.Constants.DATE_FORMAT
import com.fcpunlimited.ubersport.utils.Constants.DATE_HOUR_FORMAT
import com.fcpunlimited.ubersport.utils.Constants.DESCRIPTION_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.getSportIconByName
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.main.MainActivity
import com.fcpunlimited.ubersport.view.main.search.IGameShare
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.container_game_header.*
import kotlinx.android.synthetic.main.fragment_description.*
import org.jetbrains.anko.image
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class FragmentDescription : BaseMvpFragment(), DescriptionView, OnMapReadyCallback {

    private val gameModel: GameModel by inject()
    private val userModel: UserModel by inject()

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "DESCRIPTION_PRESENTER")
    lateinit var presenter: DescriptionPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = "DESCRIPTION_PRESENTER")
    fun providePresenter() = DescriptionPresenter(gameModel)

    private lateinit var mMap: GoogleMap
    private var gameConsumer: IGameShare.IGameConsumer? = null
    private var game: GameFragment? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            context.apply {
                gameConsumer = this
            }
            game = gameConsumer?.consumeGame()
        }
    }

    override fun onDetach() {
        super.onDetach()
        gameConsumer = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollView.fullScroll(ScrollView.FOCUS_UP)
        game?.apply {
            setupView(this)
            initMap()
            bt_join_game.setOnClickListener {
                bt_join_game.isClickable = false
                presenter.joinGame(id())
            }
            bt_leave_game.setOnClickListener {
                bt_leave_game.isClickable = false
                presenter.leaveGame(id())
            }
        }
    }

    private fun setupView(game: GameFragment) {

        val avatarViews = arrayOf(iv_participant_1, iv_participant_2,
                iv_participant_3, iv_participant_4, iv_participant_5, iv_participant_plus)

        avatarViews.forEach { iv -> iv.visibility = View.GONE }

        game.apply {
            author()?.apply {
                tv_author.text = nickname()
                tv_author_subtitle.text = "${firstName()} ${lastName()}"

            }
            tv_game_title.text = name()
            tv_game_description.text = description()
            length()?.let { tv_game_time.text = SimpleDateFormat(DATE_HOUR_FORMAT, Locale.ROOT).format(it) }
            tv_address.text = location()?.address()
            tv_date.text = SimpleDateFormat(DATE_FORMAT, Locale.ROOT)
                    .format(dateStart().toLong())
            participants()?.apply {
                val participantsLimit = participantsLimit()?.toInt() ?: 0
                progressBar.max = participantsLimit
                progressBar.progress = size
                tv_participants_count.text = "$size/$participantsLimit"

                for (position in 0 until size) {
                    val avatarView = avatarViews[position]
                    avatarView.visibility = View.VISIBLE
                    Picasso.get().load(R.drawable.avatar)
                            .fit()
                            .error(R.color.colorAccent)
                            .into(avatarView)
                }
                if (map { participant -> participant.id() }.contains(userModel.getUserId())) {
                    bt_join_game.visibility = View.GONE
                    bt_leave_game.visibility = View.VISIBLE
                } else {
                    bt_leave_game.visibility = View.GONE
                    bt_join_game.visibility = View.VISIBLE
                }
            }
            sport()?.apply {
                context?.let { iv_sport_icon.image = ContextCompat.getDrawable(it, getSportIconByName(name())) }
            }
        }
    }

    override fun joinedGame(game: GameFragment) {

        context?.runOnUiThread {
            this@FragmentDescription.game = game
            bt_join_game.isClickable = true
            bt_join_game.visibility = View.GONE
            bt_leave_game.visibility = View.VISIBLE
            setupView(this@FragmentDescription.game!!)
        }
    }

    override fun leavedGame(game: GameFragment) {
        context?.runOnUiThread {
            this@FragmentDescription.game = game
            bt_leave_game.isClickable = true
            bt_join_game.visibility = View.VISIBLE
            bt_leave_game.visibility = View.GONE
            setupView(this@FragmentDescription.game!!)
        }
    }

    override fun showMessage(message: String) {
        context?.runOnUiThread {
            bt_join_game.isClickable = true
            bt_leave_game.isClickable = true
            toast(message)
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.apply {
            uiSettings.isScrollGesturesEnabled = false
            uiSettings.isZoomGesturesEnabled = false
            val location = game?.location()
            location?.apply {
                val latlng = LatLng(latitude(), longitude())
                addMarker(MarkerOptions().position(latlng).title(location.address()))
                moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11f))
            }
        }
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_description

    override fun getFragmentTag(): String = DESCRIPTION_FRAGMENT_TAG

    override fun getFragmentMenu(): Int? = null
}
