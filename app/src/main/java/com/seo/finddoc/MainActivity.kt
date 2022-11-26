package com.seo.finddoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.seo.finddoc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        with(binding) {
            filterRV.layoutManager = manager
            filterRV.adapter = FilterRecyclerViewAdapter(filterData())
        }
    }
    private fun filterData() = mutableListOf<FilterItem>().apply {
        add(FilterItem(R.drawable.ic_baseline_local_hospital_24, "병원"))
    }
}