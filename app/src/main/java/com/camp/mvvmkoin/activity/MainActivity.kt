package com.camp.mvvmkoin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.camp.mvvmkoin.R
import com.camp.mvvmkoin.adapter.MyRecyclerViewAdapter
import com.camp.mvvmkoin.model.Hits
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.camp.mvvmkoin.view.AppViewModel

class MainActivity : AppCompatActivity() {
    private val viewmodel: AppViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodel.getMutableLiveData()
        viewmodel.listOfHits.observe(this,{
            if (it.isNotEmpty() && it != null) {
                initRecyclerView(it)
            }
        })
    }

    fun initRecyclerView(list: List<Hits>) {
        findViewById<RecyclerView>(R.id.mainImage).apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            PagerSnapHelper().attachToRecyclerView(this)
            adapter = MyRecyclerViewAdapter( list)
        }
    }
}
