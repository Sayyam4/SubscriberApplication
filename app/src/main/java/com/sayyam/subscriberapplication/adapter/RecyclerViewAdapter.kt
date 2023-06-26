package com.sayyam.subscriberapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sayyam.subscriberapplication.R
import com.sayyam.subscriberapplication.data.model.Subscriber
import com.sayyam.subscriberapplication.databinding.ListItemBinding

class RecyclerViewAdapter(private val clickListener: (Subscriber) -> Unit): RecyclerView.Adapter<ViewHolder>() {
    private val subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subscribers[position], clickListener)
    }
    fun setList(subscriber: List<Subscriber>) {
        subscribers.clear()
        subscribers.addAll(subscriber)
    }

}

class ViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.textViewName.text = subscriber.name
        binding.textViewEmail.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}