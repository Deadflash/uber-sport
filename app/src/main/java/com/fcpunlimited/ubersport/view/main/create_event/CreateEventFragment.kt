package com.fcpunlimited.ubersport.view.main.create_event


import android.os.Bundle
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.BaseFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val CREATE_EVENT_FRAGMENT = "createEventFragment"

class CreateEventFragment : BaseFragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CreateEventFragment().apply {
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

    override fun getFragmentLayout(): Int = R.layout.fragment_create_event

    override fun getFragmentTag(): String = CREATE_EVENT_FRAGMENT
}
