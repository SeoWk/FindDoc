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
import com.seo.finddoc.data.MedicalDTO

const val multi_type1 = 1
const val multi_type2 = 2
const val multi_type3 = 3

class FilterButtonsAdapter(
    private val context: Context,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<MedicalDTO>()

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
        private val spinner: AutoCompleteTextView = view.findViewById(R.id.filterAT)
        /**
        스피너 디자인 수정하기
         */
        private val filterCtgAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.filter_category,
            android.R.layout.simple_dropdown_item_1line
        )

        fun bind(item: MedicalDTO) {
            with(spinner) {
                setAdapter(filterCtgAdapter)
                /**
                 * 스피너 선택값 반영 안되는 문제, DiffiUtil 적용?
                 */
//                setText(adapter.getItem(adapterPosition).toString(),false)

                setOnItemClickListener { adapterView, _, position, _ ->
                    val ctg = adapterView.getItemAtPosition(position) as String
                    val subject = MedicalDTO("전체",3)
                    if(ctg == "병원" && datas.none { it == subject }) {
                        datas.add(0,subject)

//                        notifyItemInserted(0)
                        notifyDataSetChanged()

                    }else if(ctg == "약국" && datas.any { it == subject }) {
                        datas.remove(subject)
//                        notifyItemRemoved(0)
                        notifyDataSetChanged()
                    }

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

        fun bind(item: MedicalDTO) {
            btn.text = item.ctg
        }
    }

    inner class ViewHolder3(view: View): RecyclerView.ViewHolder(view) {

        private val spinner: AutoCompleteTextView = view.findViewById(R.id.filterAT)

        private val filterSubjAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.filter_medical_subject,
            android.R.layout.simple_dropdown_item_1line
        )

        fun bind(item: MedicalDTO) {
            with(spinner) {
                setAdapter(filterSubjAdapter)
                setOnItemClickListener { adapterView, _, position, _ ->
                    val ctg = adapterView.getItemAtPosition(position) as String
//                    toastMessage(ctg)
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
            //multi_type2
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
                (holder as FilterButtonsAdapter.ViewHolder1).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            multi_type3 -> {
                (holder as FilterButtonsAdapter.ViewHolder3).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as FilterButtonsAdapter.ViewHolder2).bind(datas[position])
                holder.setIsRecyclable(false)
            }
        }

    }

    override fun getItemCount() = datas.size

/*    fun getItem(position: Int): FilterData {
        return datas.get(position)
    }*/

}


