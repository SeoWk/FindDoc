package com.seo.finddoc.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.data.SearchedData
import com.seo.finddoc.databinding.RecyclerviewSearchedItemBinding

class SearchedListAdapter(
    private var list :List<SearchedData>,
    //아이템 전달하기 위한
    val onClickDeleteIcon: (data: SearchedData) -> Unit
): RecyclerView.Adapter<SearchedListAdapter.SearchedViewHolder>() {

    inner class SearchedViewHolder(val binding: RecyclerviewSearchedItemBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedViewHolder {
        val binding = RecyclerviewSearchedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SearchedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchedViewHolder, position: Int) {
        val searchedItem = list[position]
        with(holder.binding) {
            searchedKewordTV.text = searchedItem.keyword
            searchedDateTV.text = searchedItem.date

            root.setOnClickListener{
//                onSearchedItemSelected(position)
            }

            deleteIB.setOnClickListener {
                onClickDeleteIcon(searchedItem)
//                onClickDeleteIcon.invoke(searchedItem)
            }


        }
    }

    //해당 항목 삭제
    fun deleteItem(position: Int) {
/*        Toast.makeText(TedPermissionProvider.context,"테스트중 $position 번째 항목", Toast.LENGTH_SHORT).show()
        searchHistoryList.removeAt(position)
        notifyItemRangeChanged(0,searchHistoryList.size)*/
    }

/*    fun notifyItemRangeChanged(list : MutableList<SearchedItem>){
        notifyItemRangeChanged(0,searchHistoryList.size)
    }*/

    private fun onSearchedItemSelected(position: Int) {
        /**
         * 해당 검색어로 조회 구현하기
         */
    }

    override fun getItemCount() = list.size


    fun setData(data: List<SearchedData>) {
        list = data
        notifyDataSetChanged()
    }

}