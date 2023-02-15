package com.seo.finddoc.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.databinding.RecyclerviewHospitalItemBinding
import com.seo.finddoc.json_entity.HospitalItem

class HospitalAdapter(
//    private val hospitalItems: ArrayList<HospitalItem>
): ListAdapter<HospitalItem, HospitalAdapter.HospitalHolder>(HospitalComparator) {

    class HospitalHolder(private val binding: RecyclerviewHospitalItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: HospitalItem) {

            /**
             * 필요한 정보
             * - , 진료과목(요청변수이므로 따로 저장),
             * 현재 기준 진료여부, 영업 시간
             * 나와의 거리(1km; 미만은 m),
             * 기타(옵션; 전문의수, )
             */
            with(binding) {
                hospitalTV.text = item.yadmNm
//            departmentTV.text =
//            treatmentTV.text =
//            hoursTV.text =
//            distanceTV.text = item.distance
                dongTV.text = item.addr
                telTV.text = item.telno
//
                hopitalInfoTV1.text = item.ykiho
                hopitalInfoTV2.text = item.YPos.toString()
//            hopitalInfoTV3.text = item.XPos.toString()
//            hopitalInfoTV4.text = item.relInfo4
//            hopitalInfoTV5.text = item.relInfo5
//            hopitalInfoTV6.text = item.relInfo6
//            hopitalInfoTV7.text = item.relInfo7
//            hopitalInfoTV8.text = item.relInfo8
//            hopitalInfoTV9.text = item.relInfo9
//            hopitalInfoTV10.text = item.relInfo10
                /**
                 * 이벤트?
                 *  전화번호
                 *  각 아이템 클릭리스너
                 */
                root.setOnClickListener {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalHolder {
        val binding = RecyclerviewHospitalItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HospitalHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object{
        private val HospitalComparator =  object  : DiffUtil.ItemCallback<HospitalItem>() {
            override fun areItemsTheSame(oldItem: HospitalItem, newItem: HospitalItem): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: HospitalItem, newItem: HospitalItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}