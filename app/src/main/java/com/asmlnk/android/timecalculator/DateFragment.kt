package com.asmlnk.android.timecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat

private  const val DIALOG_DATE = "DialogDate"
private const val DIALOG_TIME = "DialogTime"
private const val REQUEST_DATE_START = 1
private const val REQUEST_DATE_FINISH = 2
private const val REQUEST_TIME_START = 3
private const val REQUEST_TIME_FINISH = 4

@Suppress("DEPRECATION")
class DateFragment: Fragment(), DatePickerFragment.Callbacks, TimePickerFragment.Callbacks {

    private lateinit var outputWindow: TextView
    private lateinit var result: Button
    private lateinit var startDate: Button
    private lateinit var finishDate: Button


    private val dateDetailViewModel: DateDetailViewModel by lazy {
        ViewModelProvider(this) [DateDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date, container, false)

        result = view.findViewById(R.id.result) as Button
        outputWindow = view.findViewById(R.id.output_window) as TextView
        startDate = view.findViewById(R.id.start_date) as Button

        finishDate = view.findViewById(R.id.finish_date) as Button


        startDate.setOnClickListener {
                DatePickerFragment().apply {
                    setTargetFragment(this@DateFragment, REQUEST_DATE_START)
                    show(this@DateFragment.requireFragmentManager(), DIALOG_DATE)
                    setOnDismissFunction {
                        TimePickerFragment.newInstance(dateDetailViewModel.dateStart!!).apply {
                            setTargetFragment(this@DateFragment, REQUEST_TIME_START)
                            show(this@DateFragment.requireFragmentManager(), DIALOG_TIME)
                        }
                    }
                }
            }

        finishDate.setOnClickListener {
                DatePickerFragment().apply {
                    setTargetFragment(this@DateFragment, REQUEST_DATE_FINISH)
                    show(this@DateFragment.requireFragmentManager(), DIALOG_DATE)
                    setOnDismissFunction {
                        TimePickerFragment.newInstance(dateDetailViewModel.dateFinish!!).apply {
                            setTargetFragment(this@DateFragment, REQUEST_TIME_FINISH)
                            show(this@DateFragment.requireFragmentManager(), DIALOG_TIME)
                        }
                    }
                }
            }

        result.setOnClickListener {
            outputWindow.text = dateDetailViewModel.time()
        }

        return view
    }

    companion object {
        fun newInstance(): DateFragment {
            return DateFragment()
        }
    }

    override fun onDateSelectedStart(date: Date) {
        dateDetailViewModel.dateStart = date
        //val df = SimpleDateFormat("dd MMM yyyy").format(date)                //DateFormat.getDateInstance  (DateFormat.SHORT).format(dateStart)
       // startDate.text = df.toString()


    }

    override fun onTimeSelectedStart(time: Date) {
        dateDetailViewModel.dateStart = time
        val df = SimpleDateFormat("dd MMM yyyy HH : mm").format(time)
        startDate.text = df.toString()

    }

    override fun onDateSelectedFinish(date: Date) {
        dateDetailViewModel.dateFinish = date
       // val df = SimpleDateFormat("dd MMM yyyy").format(date)
       // finishDate.text = df.toString()
    }

    override fun onTimeSelectedFinish(time: Date) {
        dateDetailViewModel.dateFinish = time
        val df = SimpleDateFormat("dd MMM yyyy HH : mm").format(time)
        finishDate.text = df.toString()
    }
}

/*finishTime.setOnClickListener {
          if (dateDetailViewModel.dateFinish == null) {
              TimePickerFragment().apply {
                  setTargetFragment(this@DateFragment, REQUEST_TIME_FINISH)
                  show(this@DateFragment.requireFragmentManager(), DIALOG_TIME)
              }
          } else {
              TimePickerFragment.newInstance(dateDetailViewModel.dateFinish!!).apply {
              setTargetFragment(this@DateFragment, REQUEST_TIME_FINISH)
              show(this@DateFragment.requireFragmentManager(), DIALOG_TIME)
          }
              }
      }*/
/*startTime.setOnClickListener {
          if (dateDetailViewModel.dateStart == null) {
              TimePickerFragment().apply {
                  setTargetFragment(this@DateFragment, REQUEST_TIME_START)
                  show(this@DateFragment.requireFragmentManager(), DIALOG_TIME)
              }
          } else {
                  TimePickerFragment.newInstance(dateDetailViewModel.dateStart!!).apply {
                      setTargetFragment(this@DateFragment, REQUEST_TIME_START)
                      show(this@DateFragment.requireFragmentManager(), DIALOG_TIME)
              }
           }
       }*/