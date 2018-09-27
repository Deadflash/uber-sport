package com.fcpunlimited.ubersport.view.description.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.user.ParticipantDto
import com.fcpunlimited.ubersport.utils.adapter.IListItem
import com.fcpunlimited.ubersport.view.adapters.ParticipantAdapter
import kotlinx.android.synthetic.main.fragment_description_participants_page.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DescriptionParticipantsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DescriptionParticipantsFragment().apply {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_description_participants_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val participants = arrayListOf<IListItem>(ParticipantDto("pahom", "", "about how i playing football"),
                ParticipantDto("pahom", "", "about how i playing football"),
                ParticipantDto("pahom", "", "about how i playing football"),
                ParticipantDto("pahom", "", "about how i playing football"),
                ParticipantDto("pahom", "", "about how i playing football"),
                ParticipantDto("pahom", "", "about how i playing football"),
                ParticipantDto("pahom", "", "about how i playing football"))
        val adapter = ParticipantAdapter()
        adapter.add(participants)
        participants_recycler.layoutManager = LinearLayoutManager(this.context)
        participants_recycler.adapter = adapter
        participants_recycler.setHasFixedSize(true)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
