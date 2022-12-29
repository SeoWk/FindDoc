package com.seo.finddoc.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seo.finddoc.data.PharmacyListItem
import com.seo.finddoc.databinding.RecyclerviewPharmacylistItemBinding

class PharmacyListAdapter(
    private val pharmacyList: MutableList<PharmacyListItem>
): RecyclerView.Adapter<PharmacyListAdapter.PharmacyListHolder>() {

    inner class PharmacyListHolder(val binding: RecyclerviewPharmacylistItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyListHolder {
        val binding = RecyclerviewPharmacylistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PharmacyListHolder(binding)
    }

    override fun onBindViewHolder(holder: PharmacyListHolder, position: Int) {
        val pharmacyListItem = pharmacyList[position]

        with(holder.binding) {
            pharmacyTV.text = pharmacyListItem.pharmacyName
            departmentTV.text = pharmacyListItem.medicalDepartment
            businessTV.text = pharmacyListItem.openforBusiness
            hoursTV.text = pharmacyListItem.businessHours
            distanceTV.text = pharmacyListItem.distance
            dongTV.text = pharmacyListItem.dongAddress
        }
    }

    override fun getItemCount() = pharmacyList.size

}