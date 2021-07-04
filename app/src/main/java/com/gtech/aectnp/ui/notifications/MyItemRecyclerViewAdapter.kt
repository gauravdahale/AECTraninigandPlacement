package com.gtech.aectnp.ui.notifications

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.gtech.aectnp.databinding.FragmentNotificationsBinding
import com.gtech.aectnp.ui.notifications.placeholder.NotificationModel

import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [NotificationModel].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<NotificationModel>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNotificationsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        holder.content.text =item.content
        holder.date.setText(item.timestamp.toString())
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentNotificationsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.title
        val content: TextView = binding.content
        val date: TextView = binding.date
        override fun toString(): String {
            return super.toString() + " '" + content.text + "'"
        }
    }

}