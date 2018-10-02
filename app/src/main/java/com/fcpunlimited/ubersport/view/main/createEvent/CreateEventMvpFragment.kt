package com.fcpunlimited.ubersport.view.main.createEvent


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.CreateEventDto
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.CREATE_EVENT_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import kotlinx.android.synthetic.main.recycler_container.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateEventMvpFragment : BaseMvpFragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CreateEventMvpFragment().apply {
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val events = arrayListOf<IListItem>(CreateEventDto("Football", 1),
                CreateEventDto("Volleyball", 1),
                CreateEventDto("Tennis", 1),
                CreateEventDto("Paintball", 1),
                CreateEventDto("Cycling", 1))

        val adapter = CustomAdapter()
        adapter.add(events)

        recycler.layoutManager = GridLayoutManager(this.context, 3)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)
    }

    override fun getFragmentLayout(): Int = R.layout.recycler_container

    override fun getFragmentTag(): String = CREATE_EVENT_FRAGMENT_TAG

    override fun getFragmentMenu(): Int = R.menu.create_event_menu
}
