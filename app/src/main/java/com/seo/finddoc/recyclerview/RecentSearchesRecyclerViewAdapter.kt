package com.seo.finddoc.recyclerview

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.data.RecentSearchItem
import com.seo.finddoc.databinding.RecyclerviewRecentSearchItemBinding

class RecentSearchesRecyclerViewAdapter(
    private val recentSearches: MutableList<RecentSearchItem>
): RecyclerView.Adapter<RecentSearchesRecyclerViewAdapter.RecentSearchesViewHolder>() {

    inner class  RecentSearchesViewHolder(val binding: RecyclerviewRecentSearchItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchesViewHolder {
        val binding = RecyclerviewRecentSearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  RecentSearchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentSearchesViewHolder, position: Int) {
        val searchedItem = recentSearches[position]

        with(holder.binding) {
            searchedKewordTV.text = searchedItem.keyword
            searchedDateTV.text = searchedItem.date
            deleteIB.setOnClickListener{
                onSearchedItemDeleteClicked(position)
            }
            recentSearchItem.setOnClickListener{
                onSearchedItemSelected(position)
            }

        }
    }

    private fun onSearchedItemDeleteClicked(position: Int) {
        Log.d(TAG, "최근 검색어 삭제 버튼 클릭 : $position + 번째 항목")
        //해당 항목 삭제
        this.recentSearches.removeAt(position)
    }

    private fun onSearchedItemSelected(position: Int) {
        Log.d(TAG, "최근 검색한 항목 클릭 : $position + 번째 항목")

    }

    override fun getItemCount() = recentSearches.size
}