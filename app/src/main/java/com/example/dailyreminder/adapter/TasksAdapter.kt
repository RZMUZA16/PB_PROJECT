package com.example.dailyreminder.com.example.dailyreminder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyreminder.R
import com.example.dailyreminder.data.model.TasksItem

class TasksAdapter(
    private var items: List<TasksItem> = emptyList()
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView       = view.findViewById(R.id.tvTaskTitle)
        private val tvDesc: TextView        = view.findViewById(R.id.tvTaskDesc)
        private val tvDate: TextView        = view.findViewById(R.id.tvTaskDate)

        fun bind(item: TasksItem) {
            tvTitle.text = item.judul
            tvDesc.text  = item.keterangan
            tvDate.text  = item.tanggalTugas
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<TasksItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}