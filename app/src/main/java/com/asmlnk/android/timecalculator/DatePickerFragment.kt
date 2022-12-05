package com.asmlnk.android.timecalculator

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*
import java.util.Date


private const val ARG_DATE = "date"


@Suppress("DEPRECATION")
class DatePickerFragment: DialogFragment() {

    interface Callbacks {
        fun onDateSelectedStart(date: Date)
        fun onDateSelectedFinish(date: Date)
    }

    private var onDismissFunction: () -> Unit = {}
    fun setOnDismissFunction(block: () -> Unit) {
        onDismissFunction = block
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dateListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
            val resultDate: Date = GregorianCalendar(year, month, day).time
            targetFragment?.let { fragment ->
                when(targetRequestCode) {
                   1 -> (fragment as Callbacks).onDateSelectedStart(resultDate)
                   2 ->  (fragment as Callbacks).onDateSelectedFinish(resultDate)
                }
            }
        }

        val calendar = Calendar.getInstance()

        if (arguments != null) {
            val date = arguments?.getSerializable(ARG_DATE) as Date
            calendar.time = date
        }


        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)





        return DatePickerDialog(requireContext(),
                                dateListener,
                                initialYear,
                                initialMonth,
                                initialDay)
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissFunction()
        super.onDismiss(dialog)


    }




    companion object {
        fun newInstance(date: Date) : DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}