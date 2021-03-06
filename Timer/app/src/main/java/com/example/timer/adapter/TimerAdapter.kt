package com.example.timer.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.R
import com.example.timer.model.Timer

class TimerAdapter(
    private val context: Context,
    private val content: Array<Timer>
) : RecyclerView.Adapter<TimerAdapter.ItemViewHolder>()
{
    private var selectedPosition = -1

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.findViewById(R.id.text_title)
        val textDuration: TextView = view.findViewById(R.id.text_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount() = content.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = content[position]

        holder.textTitle.text = item.title
        holder.textDuration.text = item.duration.toString()

        holder.itemView.setBackgroundColor(if (selectedPosition == position) Color.LTGRAY
        else Color.TRANSPARENT)
    }

    fun setSelected(position: Int){
        Log.d("RRRRRRRRRRRRR", "new selected = $position")
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousSelectedPosition)
        notifyItemChanged(selectedPosition)
    }
}