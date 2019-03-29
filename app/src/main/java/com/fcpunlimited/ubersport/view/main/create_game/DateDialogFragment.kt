package com.fcpunlimited.ubersport.view.main.create_game

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DateDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: IDateSelector? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = when (parentFragment) {
            parentFragment as ChooseGameFragment -> parentFragment as ChooseGameFragment
            else -> null
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface IDateSelector {
        fun dateSelected(date: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        return DatePickerDialog(context!!, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener?.dateSelected("Selected $dayOfMonth ${month + 1} $year")
    }
}