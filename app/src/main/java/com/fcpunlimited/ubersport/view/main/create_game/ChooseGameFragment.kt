package com.fcpunlimited.ubersport.view.main.create_game

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.di.game.GameContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack
import com.fcpunlimited.ubersport.struct.game.SportDto
import com.fcpunlimited.ubersport.utils.Constants.CHOOSE_GAME_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import kotlinx.android.synthetic.main.fragment_choose_game.*
import kotlinx.android.synthetic.main.recycler_container.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChooseGameFragment : BaseMvpFragment(), DateDialogFragment.IDateSelector {

    private val gameContainer: GameContainer by inject()
    private val gameModel: GameModel by inject()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_date_picker.setOnClickListener { DateDialogFragment().show(childFragmentManager,"date_picker") }

        val sports = gameContainer.sportsData.value
        if (sports == null || sports.isEmpty()) {
            gameModel.getSports(object : HttpEmptyResponseCallBack {
                override fun onFailure(message: String) {
                    context?.runOnUiThread { toast(message) }
                }
            })
        }

        gameContainer.sportsData.observe(this, Observer {
            val adapter = CustomAdapter()
            lifecycle.addObserver(adapter)
            adapter.setData(it.map { sport -> SportDto(sport) }.toCollection(arrayListOf()))

            recycler.layoutManager = GridLayoutManager(context, 2)
            recycler.adapter = adapter
            recycler.setHasFixedSize(true)
        })
    }

    override fun dateSelected(date: String) {
        context?.toast(date)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_choose_game

    override fun getFragmentTag(): String = CHOOSE_GAME_FRAGMENT_TAG

    override fun getFragmentMenu(): Int? = null
}
