package com.sayyam.subscriberapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sayyam.subscriberapplication.adapter.RecyclerViewAdapter
import com.sayyam.subscriberapplication.data.model.Subscriber
import com.sayyam.subscriberapplication.data.model.SubscriberDatabase
import com.sayyam.subscriberapplication.data.repository.SubscriberRepository
import com.sayyam.subscriberapplication.databinding.ActivityMainBinding
import com.sayyam.subscriberapplication.viewmodel.SubscriberViewModel
import com.sayyam.subscriberapplication.viewmodel.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it, Toast.LENGTH_LONG).show()
            }
        })
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewSubscriber.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter({selectedItem: Subscriber ->listItemClicked(selectedItem)})
        binding.recyclerViewSubscriber.adapter = adapter
        displaySubscribersList()
    }
    private fun displaySubscribersList(){
        subscriberViewModel.getSavedSubscribers().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber){
        subscriberViewModel.initUpdateAndDelete(subscriber) }
}