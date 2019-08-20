package com.fcpunlimited.ubersport.view.main.search.description


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.struct.game.GameParticipantDiffUtilCallback
import com.fcpunlimited.ubersport.struct.game.GameParticipantsDto
import com.fcpunlimited.ubersport.utils.Constants.DATE_FORMAT
import com.fcpunlimited.ubersport.utils.Constants.DATE_HOUR_FORMAT
import com.fcpunlimited.ubersport.utils.Constants.DESCRIPTION_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.getSportIconByName
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import com.fcpunlimited.ubersport.view.main.MainActivity
import com.fcpunlimited.ubersport.view.main.search.IGameShare
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.container_game_header.*
import kotlinx.android.synthetic.main.fragment_description.*
import org.jetbrains.anko.image
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class DescriptionFragment : BaseMvpFragment(), DescriptionView, IExcludeParticipant, OnMapReadyCallback {

    companion object {
        const val DESCRIPTION_PRESENTER = "descriptionPresenter"
    }

    private val gameModel: GameModel by inject()
    private val userModel: UserModel by inject()
    var isGameOwner: Boolean = false

    @InjectPresenter(type = PresenterType.GLOBAL, tag = DESCRIPTION_PRESENTER)
    lateinit var presenter: DescriptionPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL, tag = DESCRIPTION_PRESENTER)
    fun providePresenter() = DescriptionPresenter(gameModel)

    private lateinit var mMap: GoogleMap
    private var gameConsumer: IGameShare.IGameConsumer? = null
    private val gameData: MutableLiveData<GameFragment> = MutableLiveData()
    private lateinit var adapter: CustomAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            context.apply {
                gameConsumer = this
            }
            gameData.value = gameConsumer?.consumeGame()
        }
    }

    override fun onDetach() {
        super.onDetach()
        gameConsumer = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CustomAdapter()
        lifecycle.addObserver(adapter)
        scrollView.fullScroll(ScrollView.FOCUS_UP)
        recycler.layoutManager = LinearLayoutManager(this@DescriptionFragment.context, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)
        gameData.observe(this, androidx.lifecycle.Observer<GameFragment> { game ->
            setupView(game)
            initMap()
            game.author()?.let { isGameOwner = it.id() == userModel.getUserId() }
        })
        bt_join_game.setOnClickListener {
            bt_join_game.isClickable = false
            gameData.value?.id()?.let { gameId -> presenter.joinGame(gameId) }
        }
        bt_leave_game.setOnClickListener {
            bt_leave_game.isClickable = false
            gameData.value?.id()?.let { gameId -> presenter.leaveGame(gameId) }
        }
    }

    private fun setupView(game: GameFragment) {
        game.author()?.apply {
            tv_author.text = nickname()
            tv_author_subtitle.text = "${nickname()}"

        }
        tv_game_title.text = game.name()
        tv_game_description.text = game.description()
        tv_game_time.text = SimpleDateFormat(DATE_HOUR_FORMAT, Locale.ROOT).format((game.dateEnd()
                ?: 0.0).minus(game.dateStart() ?: 0.0))
        tv_address.text = game.location()?.address()
        tv_date.text = SimpleDateFormat(DATE_FORMAT, Locale.ROOT)
                .format(game.dateStart()?.toLong())
        tv_description_participants_count.text = "${game.participants()?.size
                ?: 0}/${game.sport()?.participantCount()?.toInt() ?: 0}"
        game.participants()?.let {
            if (it.map { participant -> participant.id() }.contains(userModel.getUserId())) {
                bt_join_game.visibility = View.GONE
                bt_leave_game.visibility = View.VISIBLE
            } else {
                bt_leave_game.visibility = View.GONE
                bt_join_game.visibility = View.VISIBLE
            }
            adapter.apply {
                val participants = arrayListOf<GameParticipantsDto>()
                participants.addAll(it.map { participant -> GameParticipantsDto(participant) })
                if (game.sport()?.participantCount()?.toInt() ?: 0 > (it.size) && isGameOwner) {
                    participants.add(GameParticipantsDto(GameFragment.Participant("STUB",
                            "", null, null)))
                }

                val gameDtoDiffUtilCallback =
                        GameParticipantDiffUtilCallback(getData() as ArrayList<GameParticipantsDto>, participants)
                setData(participants as ArrayList<IListItem>)
                DiffUtil.calculateDiff(gameDtoDiffUtilCallback).dispatchUpdatesTo(this)
            }
        }
        game.sport()?.apply {
            context?.let { iv_sport_icon.image = ContextCompat.getDrawable(it, getSportIconByName(name()!!)) }
        }
    }

    override fun excludeParticipant(gameId: String, userId: String) {
        presenter.excludeParticipant(gameId, userId)
    }

    override fun joinedGame(game: GameFragment) {

        context?.runOnUiThread {
            gameData.value = game
            bt_join_game.isClickable = true
            bt_join_game.visibility = View.GONE
            bt_leave_game.visibility = View.VISIBLE
        }
    }

    override fun leavedGame(game: GameFragment) {
        context?.runOnUiThread {
            gameData.value = game
            bt_leave_game.isClickable = true
            bt_join_game.visibility = View.VISIBLE
            bt_leave_game.visibility = View.GONE
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
            val location = gameData.value?.location()
            location?.apply {
                val latlng = LatLng(coordinates()?.get(0) ?: 0.0, coordinates()?.get(1) ?: 0.0)
                addMarker(MarkerOptions().position(latlng).title(location.address()))
                moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11f))
            }
        }
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_description

    override fun getFragmentTag(): String = DESCRIPTION_FRAGMENT_TAG
}
