package com.fcpunlimited.ubersport.view.main.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.EventDto
import com.fcpunlimited.ubersport.struct.event.EventType
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.adapters.IListItem
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import kotlinx.android.synthetic.main.recycler_container.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchMvpFragment : BaseMvpFragment() {

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val events = arrayListOf<IListItem>(EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Alco Trash", 321, "address", EventType.ALCO_TRASH),
                EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Alco Trash", 321, "address", EventType.ALCO_TRASH),
                EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Football", 123L, "address", EventType.FOOTBALL))

        val adapter = CustomAdapter()
        adapter.add(events)

        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun getFragmentLayout(): Int = R.layout.recycler_container

    override fun getFragmentTag(): String = SEARCH_FRAGMENT_TAG

    override fun getFragmentMenu(): Int = R.menu.search_menu
}
