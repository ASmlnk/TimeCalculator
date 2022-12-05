package com.asmlnk.android.timecalculator

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*
import java.util.Date

private const val ARG_TIME = "time"


@Suppress("DEPRECATION")
class TimePickerFragment: DialogFragment() {

    interface Callbacks {
        fun onTimeSelectedStart(time: Date)
        fun onTimeSelectedFinish(time: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay: Int, minute: Int ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            val resultTime: Date = calendar.time
            targetFragment?.let {
                when(targetRequestCode) {
                   3 -> (it as Callbacks).onTimeSelectedStart(resultTime)
                   4 -> (it as Callbacks).onTimeSelectedFinish(resultTime)
                }
            }


        }

        if (arguments != null) {
            val time = arguments?.getSerializable(ARG_TIME) as Date
            calendar.time = time
        }


        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireContext(),
            timeListener,
            hourOfDay,
            minute,
            true)
    }

    companion object {
        fun newInstance(time: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, time)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}