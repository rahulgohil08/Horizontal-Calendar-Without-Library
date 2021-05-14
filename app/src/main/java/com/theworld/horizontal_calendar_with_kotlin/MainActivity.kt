package com.theworld.horizontal_calendar_with_kotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.datepicker.MaterialDatePicker
import com.theworld.horizontal_calendar_with_kotlin.adapter.CalendarAdapter
import com.theworld.horizontal_calendar_with_kotlin.data.CalendarData
import com.theworld.horizontal_calendar_with_kotlin.databinding.ActivityMainBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CalendarAdapter.CalendarInterface {


    /*
    *   Purpose
    *
    *   Click Select Date -> Choose From Date Picker -> Watch the Show
    *
    * */


    companion object {
        private val TAG = "MainActivity"
    }


    private lateinit var binding: ActivityMainBinding

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)


    private val calendarAdapter = CalendarAdapter(this, arrayListOf())

    private val calendarList = ArrayList<CalendarData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        clickListener()
        getDates()

    }


    /*--------------------------- Init -----------------------------*/

    private fun init() {
        binding.apply {

            monthYearPicker.text = sdf.format(cal.time)
            calendarView.setHasFixedSize(true)
            calendarView.adapter = calendarAdapter

        }
    }

    /*----------------------------------------- Click Listener -------------------------------*/

    private fun clickListener() {
        binding.monthYearPicker.setOnClickListener {
            displayDatePicker()
        }
    }


    /*----------------------------------------- Display Date Picker -------------------------------*/

    private fun displayDatePicker() {

        val materialDateBuilder: MaterialDatePicker.Builder<Long> =
            MaterialDatePicker.Builder.datePicker()
        materialDateBuilder.setTitleText("Select Date")

        val materialDatePicker = materialDateBuilder.build()

        materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

        materialDatePicker.addOnPositiveButtonClickListener {

            try {

                Log.d(TAG, "displayDatePicker: $it")
                Log.d(TAG, "displayDatePicker: ${Date(it)}")


                binding.monthYearPicker.text = sdf.format(it)
                cal.time = Date(it)

                getDates()


                Log.d(TAG, "init: Date === ${sdf.format(cal.time)}")

            } catch (e: ParseException) {
                Log.d(TAG, "displayDatePicker: ${e.message}")
            }

        }

    }


    /*------------------------------ Get Dates of Month ------------------------------*/

    private fun getDates() {
        val dateList = ArrayList<CalendarData>() // For our Calendar Data Class
        val dates = ArrayList<Date>() // For Date


        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)


        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)


        //  dates = 0 < MAX DAYS = 28 (For FEB)
        //  dates = 1 < MAX DAYS = 28 (For FEB)
        //  dates = 2 < MAX DAYS = 28 (For FEB)
        //  .....

        while (dates.size < maxDaysInMonth) {

            dates.add(monthCalendar.time)
            dateList.add(CalendarData(monthCalendar.time))

            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)   // Increment Day By 1
        }

        calendarList.clear()
        calendarList.addAll(dateList)
        calendarAdapter.updateList(dateList)
    }


    /*------------------------------- Handle Interface -------------------------------*/

    override fun onSelect(calendarData: CalendarData, position: Int) {

        // You can get Selected date here....

        calendarList.forEachIndexed { index, calendarModel ->
            calendarModel.isSelected = index == position
        }

        calendarAdapter.updateList(calendarList)
    }
}