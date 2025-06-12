package com.example.dailyreminder.com.example.dailyreminder.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyreminder.R
import com.example.dailyreminder.model.Reminder

class ReminderAdapter(
    private val reminders: List<Reminder>,
    private val onItemClick: (Reminder) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvReminderTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvReminderDate)

        fun bind(reminder: Reminder) {
            tvTitle.text = reminder.title
            tvDate.text = reminder.date

            itemView.setOnClickListener {
                onItemClick(reminder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(reminders[position])
    }

    override fun getItemCount(): Int = reminders.size
}
