package com.fcpunlimited.ubersport.view.main.create_game.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.View
import android.widget.DatePicker
import android.content.res.TypedArray
import java.text.SimpleDateFormat
import java.util.*


class DateDialogFragment : DialogPreference, DatePickerDialog.OnDateSetListener {

    private var lastDate = 0
    private var lastMonth = 0
    private var lastYear = 0
    private var dateval: String? = null
    private lateinit var picker: DatePicker
    private var mSummary: CharSequence? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setPositiveButtonText("Ok")
        setNegativeButtonText("Закрыть")
    }

    private var listener: IDateSelector? = null

    override fun onCreateDialogView(): View {
        picker = DatePicker(context)
        return picker
    }

    override fun onBindDialogView(v: View) {
        super.onBindDialogView(v)

        picker.updateDate(lastYear, lastMonth + 1, lastDate)
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        super.onDialogClosed(positiveResult)

        if (positiveResult) {
            lastYear = picker.year
            lastMonth = picker.month
            lastDate = picker.dayOfMonth

            val dateval = (lastYear.toString() + "-"
                    + lastMonth.toString() + "-"
                    + lastDate.toString())

            if (callChangeListener(dateval)) {
                persistString(dateval)
            }
        }
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? {
        return a.getString(index)
    }

    override fun onSetInitialValue(restoreValue: Boolean, defaultValue: Any?) {
        dateval = null

        if (restoreValue) {
            if (defaultValue == null) {
                val cal = Calendar.getInstance()
                val format1 = SimpleDateFormat("yyyy-MM-dd")
                val formatted = format1.format(cal.getTime())
                dateval = getPersistedString(formatted)
            } else {
                dateval = getPersistedString(defaultValue.toString())
            }
        } else {
            dateval = defaultValue!!.toString()
        }
        lastYear = dateval?.let { getYear(it).toInt() }!!
        lastMonth = getMonth(dateval!!).toInt()
        lastDate = getDate(dateval!!)
    }

    fun getYear(dateval: String): Int {
        val pieces = dateval.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.parseInt(pieces[0])
    }

    fun getMonth(dateval: String): Int {
        val pieces = dateval.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.parseInt(pieces[1])
    }

    fun getDate(dateval: String): Int {
        val pieces = dateval.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Integer.parseInt(pieces[2])
    }

    fun setText(text: String) {
        val wasBlocking = shouldDisableDependents()

        dateval = text

        persistString(text)

        val isBlocking = shouldDisableDependents()
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking)
        }
    }

    fun getText(): String? {
        return dateval
    }

    override fun getSummary(): CharSequence? {
        return mSummary
    }

    override fun setSummary(summary: CharSequence?) {
        if (summary == null && mSummary != null || summary != null && summary != mSummary) {
            mSummary = summary
            notifyChanged()
        }
    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        listener = when (parentFragment) {
////            parentFragment as ChooseSportFragment -> parentFragment as ChooseSportFragment
//            else -> null
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    interface IDateSelector {
        fun dateSelected(date: String)
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val calendar = Calendar.getInstance()
//        return DatePickerDialog(context!!, this,
//                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
//    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener?.dateSelected("Selected $dayOfMonth ${month + 1} $year")
    }
}