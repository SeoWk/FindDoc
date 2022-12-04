package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seo.finddoc.R
import com.seo.finddoc.adapter.HospitalListAdapter
import com.seo.finddoc.data.HospitalListItem
import com.seo.finddoc.databinding.BottomsheetListBinding
import com.seo.finddoc.databinding.RecyclerviewHospitallistItemBinding

class BottomSheetListFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance() = BottomSheetListFragment()
    }
    private var _binding: BottomsheetListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetListBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.bottomsheet_list,container,false)

        val manager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val divider  = DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
        with(binding.hospitalRV) {
            layoutManager = manager
            addItemDecoration(divider)
            adapter = HospitalListAdapter(hospitalList())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun hospitalList() = mutableListOf<HospitalListItem>().apply {
        add(
            HospitalListItem("홍길동의원","내과","진료중",
            "18:00 진료마감","900m","가산동",
        "정보1",
        "정보2",
        "정보3",
        "정보4",
        "정보5",
        "정보6",
        "정보7",
        "정보8",
        "정보9",
        "정보10",
        ))
        add(
            HospitalListItem("호호바의원","외과","진료중",
            "13:00 점심시간","500m","가산동",
        "정보1",
        "정보2",
        "정보3",
        "정보4",
        "정보5",
        "정보6",
        "정보7",
        "정보8",
        "정보9",
        "정보10",
        ))
        add(
            HospitalListItem("메차쿠차의원","치과","진료중",
            "12:00 점심시간","130m","가산동",
        "정보1",
        "정보2",
        "정보3",
        "정보4",
        "정보5",
        "정보6",
        "정보7",
        "정보8",
        "정보9",
        "정보10",
        ))
    }




}