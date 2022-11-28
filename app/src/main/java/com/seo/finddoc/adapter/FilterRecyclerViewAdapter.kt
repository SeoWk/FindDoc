package com.seo.finddoc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.FilterItem
import com.seo.finddoc.databinding.RecyclerviewFilterItemBinding

class FilterRecyclerViewAdapter(
    private val filterList: MutableList<FilterItem>
): RecyclerView.Adapter<FilterRecyclerViewAdapter.FilterViewHolder>() {

    inner class FilterViewHolder(val binding: RecyclerviewFilterItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = RecyclerviewFilterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filterItem = filterList[position]

        with(holder.binding) {
            filterBtn.setIconResource(filterItem.filterIcon)
            filterBtn.text = filterItem.filterName
            //on/off 색 전환
//            filterToggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener())
            //스피너 구현
        }
    }

    override fun getItemCount() = filterList.size
}