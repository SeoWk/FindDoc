package com.seo.finddoc.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.databinding.RecyclerviewSearchedItemBinding
import com.seo.finddoc.room.SearchWord
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter(
/*    private var list :List<SearchWord>,
    //아이템 전달하기 위한
 */
//    val onClickDeleteIcon: (item: SearchWord) -> Unit
): ListAdapter<SearchWord, SearchAdapter.SearchViewHolder>(SearchComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class SearchViewHolder(private val binding: RecyclerviewSearchedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchWord) {
//            val searchedItem = list[position]
            with(binding) {
                searchedKewordTV.text = item.word
                searchedDateTV.text = SimpleDateFormat(
                    "MM/dd",
                    Locale.getDefault()
                ).format(System.currentTimeMillis())

                root.setOnClickListener {
//                onSearchedItemSelected(position)
                }

                deleteIB.setOnClickListener {
                    /**
                     * ViewModel delete로 연결
                     */
                    item.id
//                    onClickDeleteIcon(item)
//                onClickDeleteIcon.invoke(searchedItem)
                }

                /**
                 * 해당 검색어로 조회 구현하기
                 */

            }
        }

        companion object {
            fun create(parent: ViewGroup): SearchViewHolder {
                val binding = RecyclerviewSearchedItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SearchViewHolder(binding)
            }

        }
    }

    /**
     * 삭제예정
     */
/*    fun setData(data: List<SearchWord>) {
        list = data
        notifyDataSetChanged()
    }*/

    class SearchComparator : DiffUtil.ItemCallback<SearchWord>() {
        override fun areItemsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
            return oldItem.id == newItem.id
        }
    }

}