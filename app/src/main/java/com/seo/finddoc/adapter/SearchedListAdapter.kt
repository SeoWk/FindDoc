package com.seo.finddoc.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.data.SearchedItem
import com.seo.finddoc.databinding.RecyclerviewRecentSearchItemBinding

class SearchedListAdapter(
    private val searchHistoryList: ArrayList<SearchedItem>
): RecyclerView.Adapter<SearchedListAdapter.SearchedViewHolder>() {

    inner class SearchedViewHolder(val binding: RecyclerviewRecentSearchItemBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedViewHolder {
        val binding = RecyclerviewRecentSearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchedViewHolder, position: Int) {
        val searchedItem = searchHistoryList[position]

        with(holder.binding) {
            searchedKewordTV.text = searchedItem.keyword
            searchedDateTV.text = searchedItem.date

            root.setOnClickListener{
                onSearchedItemSelected(position)
            }

        }
    }

/*    fun onSearchedItemDeleteClicked(position: Int) {
        Log.d(TAG, "최근 검색어 삭제 버튼 클릭 : $position + 번째 항목")
        //해당 항목 삭제
        searchHistoryList.removeAt(position)
        notifyItemRangeChanged(0,searchHistoryList.size)
    }*/

    fun notifyItemRangeChanged(list : ArrayList<SearchedItem>){
        notifyItemRangeChanged(0,searchHistoryList.size)
    }

    private fun onSearchedItemSelected(position: Int) {
        Log.d(TAG, "최근 검색한 항목 클릭 : $position + 번째 항목")
        /**
         * 해당 검색어로 조회 구현하기
         */
    }

    override fun getItemCount() = searchHistoryList.size
}