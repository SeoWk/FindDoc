package com.seo.finddoc.recyclerview

import android.view.LayoutInflater
import android.view.View
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

    interface OnItemClickListener {
        /**
         * 매개변수 자유롭게 설정
         */
        //조회
        fun onItemClick(view: View, item: SearchWord, position: Int)
        //삭제
        fun onDeleteClick(view: View, item: SearchWord, position: Int)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = RecyclerviewSearchedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class SearchViewHolder(private val binding: RecyclerviewSearchedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchWord) {
//            val searchedItem = list[position]
            with(binding) {
                searchedKewordTV.text = item.word
                searchedDateTV.text = SimpleDateFormat(
                    "MM/dd",
                    Locale.getDefault()).format(System.currentTimeMillis()
                )
                /**
                 * 해당 검색어로 조회 구현하기
                 */
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    root.setOnClickListener {
                        mListener?.onItemClick(it, item, position)
                    }

                    //이전 검색어 삭제
                    deleteIB.setOnClickListener {
                        mListener?.onDeleteClick(it, item, position)
                    }
                }

            }
        }

    }


    class SearchComparator : DiffUtil.ItemCallback<SearchWord>() {
        override fun areItemsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SearchWord, newItem: SearchWord): Boolean {
            return oldItem.id == newItem.id
        }
    }

}