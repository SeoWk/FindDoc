package com.seo.finddoc

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.seo.finddoc.adapter.FilterRecyclerViewAdapter
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
        val filterAdapter = ArrayAdapter.createFromResource(
            this@MainActivity, R.array.filter_array_item, android.R.layout.simple_dropdown_item_1line
        )
        with(binding.filterAT) {
            setAdapter(filterAdapter)
/*            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = resources.getStringArray(R.array.filter_array_item)
                    when (position) {
                        0 -> {
                            toastMessage("""${item[0]}""")
                        }
                        1 -> {
                            toastMessage("""${item[1]}""")
                        }
                        else -> {
                            toastMessage("선택안함")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }*/
            setOnItemClickListener { adapterView, _, position, _ ->
                toastMessage(adapterView.getItemAtPosition(position) as String)
            }
        }
        with(binding.filterRV) {
            layoutManager = manager
            adapter = FilterRecyclerViewAdapter(filterData())
        }

    }
    private fun filterData() = mutableListOf<FilterItem>().apply {
        add(FilterItem(R.drawable.ic_baseline_local_hospital_24, "병원"))
    }
}