package com.theworld.horizontal_calendar_with_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.theworld.horizontal_calendar_with_kotlin.R
import com.theworld.horizontal_calendar_with_kotlin.data.CalendarData
import com.theworld.horizontal_calendar_with_kotlin.databinding.RowCalendarDateBinding


/*
*   Note :-
*
*   RecyclerView List Adapter not working Here
*
*   It's show weird animation when submit list.
*
*   So, Old RecyclerView Adapter is used....
*
* */


class CalendarAdapter(
    private val calendarInterface: CalendarInterface,
    private val list: ArrayList<CalendarData>
) :
    RecyclerView.Adapter<CalendarAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            RowCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(calendarList: ArrayList<CalendarData>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }


    inner class MyViewHolder(private val binding: RowCalendarDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarDateModel: CalendarData) {


            val calendarDay = binding.tvCalendarDay
            val calendarDate = binding.tvCalendarDate
            val cardView = binding.root

            if (calendarDateModel.isSelected) {
                calendarDay.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                calendarDate.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.purple_500
                    )
                )
            } else {
                calendarDay.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    )
                )
                calendarDate.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    )
                )
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }

            calendarDay.text = calendarDateModel.calendarDay
            calendarDate.text = calendarDateModel.calendarDate
            cardView.setOnClickListener {
                calendarInterface.onSelect(calendarDateModel, adapterPosition)
            }
        }

    }


    interface CalendarInterface {
        fun onSelect(calendarData: CalendarData, position: Int)
    }

}
