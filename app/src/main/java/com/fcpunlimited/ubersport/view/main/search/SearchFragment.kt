package com.fcpunlimited.ubersport.view.main.search

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.EventDto
import com.fcpunlimited.ubersport.struct.event.EventType
import com.fcpunlimited.ubersport.view.BaseFragment
import com.fcpunlimited.ubersport.view.adapters.SearchRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val SEARCH_FRAGMENT_TAG = "searchFragment"

class SearchFragment : BaseFragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SearchFragment().apply {
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

        val events = Arrays.asList(EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Alco Trash", 321, "address", EventType.ALCO_TRASH),
                EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Alco Trash", 321, "address", EventType.ALCO_TRASH),
                EventDto("Football", 123L, "address", EventType.FOOTBALL),
                EventDto("Football", 123L, "address", EventType.FOOTBALL))

        search_recycler.layoutManager = LinearLayoutManager(this.context)
        search_recycler.adapter = SearchRecyclerAdapter(events)
        search_recycler.setHasFixedSize(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_search

    override fun getFragmentTag(): String = SEARCH_FRAGMENT_TAG
}
