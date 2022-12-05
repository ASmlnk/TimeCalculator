package com.asmlnk.android.timecalculator

import androidx.lifecycle.ViewModel
import java.util.Date

class DateDetailViewModel: ViewModel() {

      var dateStart: Date? = null
      var dateFinish: Date? = null

    fun time (): String {
        if (dateStart != null && dateFinish != null) {
            val time = dateFinish!!.time / 60000 - dateStart!!.time / 60000
            val minutes = (time) % 60
            val hours = time / (60)
            return "$hours : $minutes"
        } else {
            return "${R.string.output_window}"
        }
    }
}