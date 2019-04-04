package com.fcpunlimited.ubersport.view.main.profile


import android.os.Bundle
import android.view.View
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.utils.Constants.PROFILE_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileMvpFragment : BaseMvpFragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ProfileMvpFragment().apply {
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
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_profile

    override fun getFragmentTag(): String = PROFILE_FRAGMENT_TAG
}
