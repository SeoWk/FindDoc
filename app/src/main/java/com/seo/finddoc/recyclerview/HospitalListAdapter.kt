package com.seo.finddoc.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.data.HospitalListItem
import com.seo.finddoc.databinding.RecyclerviewHospitallistItemBinding

class HospitalListAdapter(
    private val hospitalList: MutableList<HospitalListItem>
): RecyclerView.Adapter<HospitalListAdapter.HospitalListHolder>() {

    inner class HospitalListHolder(val binding: RecyclerviewHospitallistItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalListHolder {
        val binding = RecyclerviewHospitallistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HospitalListHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalListHolder, position: Int) {
        val hospitalListItem = hospitalList[position]

        with(holder.binding) {
            hospitalTV.text = hospitalListItem.hospitalName
            departmentTV.text = hospitalListItem.medicalDepartment
            treatmentTV.text = hospitalListItem.medicalTreatment
            hoursTV.text = hospitalListItem.consultationHours
            distanceTV.text = hospitalListItem.distance
            dongTV.text = hospitalListItem.dongAddress
            hopitalInfoTV1.text = hospitalListItem.relInfo1
            hopitalInfoTV2.text = hospitalListItem.relInfo2
            hopitalInfoTV3.text = hospitalListItem.relInfo3
            hopitalInfoTV4.text = hospitalListItem.relInfo4
            hopitalInfoTV5.text = hospitalListItem.relInfo5
            hopitalInfoTV6.text = hospitalListItem.relInfo6
            hopitalInfoTV7.text = hospitalListItem.relInfo7
            hopitalInfoTV8.text = hospitalListItem.relInfo8
            hopitalInfoTV9.text = hospitalListItem.relInfo9
            hopitalInfoTV10.text = hospitalListItem.relInfo10
        }
    }

    override fun getItemCount() = hospitalList.size
}