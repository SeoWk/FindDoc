package com.seo.finddoc.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.R
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.data.FilterData


const val multi_type1 = 1
const val multi_type2 = 2
const val multi_type3 = 3

class FilterRecyclerViewAdapter(
    private val context: Context,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<FilterData>()

    // 리스너 객체 참조를 저장하는 변수
    private lateinit var mListener: OnItemClickListener

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return datas[position].type
    }

    inner class ViewHolder1(view: View): RecyclerView.ViewHolder(view) {
        private var flag = true
        private val spinner: AutoCompleteTextView = view.findViewById(R.id.filterAT)
        private val filterCtgAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.filter_category,
            android.R.layout.simple_dropdown_item_1line
        )

        fun bind(item: FilterData) {
            with(spinner) {
                setAdapter(filterCtgAdapter)
                setOnItemClickListener { adapterView, _, position, _ ->
                    val ctg = adapterView.getItemAtPosition(position) as String
                    val subject = FilterData("전체",3)
                    if(flag) {
                        toastMessage(ctg)
                        /**
                         * "병원 "선택일 때만 type3 추가시키고 "약국"일 땐 삭제 및 추가하지 않기
                         * flag , notify- 수정
                         */
                        if(ctg == "병원") {
                            datas.add(0,subject)
                            notifyItemInserted(0)
                            flag = true
                        }
                    }else {
                        if(ctg == "약국") {
                            datas.remove(subject)
                            notifyItemRemoved(0)
                        }
                    }
                    notifyItemChanged(0)
                    flag = !flag
                }
                setOnClickListener{
                    val position = adapterPosition
                    //삭제된 경우를 제외
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(position) ;
                        }
                    }
                }
            }
        }

    }

    inner class ViewHolder2(view: View): RecyclerView.ViewHolder(view) {

        private val btn: Button = view.findViewById(R.id.filterBtn)

        fun bind(item: FilterData) {
            btn.text = item.name
        }
    }

    inner class ViewHolder3(view: View): RecyclerView.ViewHolder(view) {

        private val spinner: AutoCompleteTextView = view.findViewById(R.id.filterAT)

        private val filterSubjAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.filter_medical_subject,
            android.R.layout.simple_dropdown_item_1line
        )

        fun bind(item: FilterData) {
            with(spinner) {
                setAdapter(filterSubjAdapter)
                setOnItemClickListener { adapterView, _, position, _ ->
                    val ctg = adapterView.getItemAtPosition(position) as String
                    toastMessage(ctg)
                }
                setOnClickListener{
                    val position = adapterPosition
                    //삭제된 경우를 제외
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(position) ;
                        }
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (viewType) {
            multi_type1 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_filter_item1,
                    parent,
                    false
                )
                ViewHolder1(view)
            }
            multi_type3 -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_filter_item1,
                    parent,
                    false
                )
                ViewHolder3(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_filter_item2,
                    parent,
                    false
                )
                ViewHolder2(view)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(datas[position].type) {
            multi_type1 -> {
                (holder as FilterRecyclerViewAdapter.ViewHolder1).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            multi_type3 -> {
                (holder as FilterRecyclerViewAdapter.ViewHolder3).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as FilterRecyclerViewAdapter.ViewHolder2).bind(datas[position])
                holder.setIsRecyclable(false)
            }
        }

    }

    override fun getItemCount() = datas.size

/*    fun getItem(position: Int): FilterData {
        return datas.get(position)
    }*/

}