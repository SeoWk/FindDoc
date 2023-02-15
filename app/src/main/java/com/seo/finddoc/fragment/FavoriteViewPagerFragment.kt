package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seo.finddoc.data.HospitalListItem
import com.seo.finddoc.data.PharmacyListItem
import com.seo.finddoc.databinding.ViewpagerItemBinding
import com.seo.finddoc.recyclerview.PharmacyListAdapter

class FavoriteViewPagerFragment : Fragment() {
    private var _binding: ViewpagerItemBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(dept: String?): FavoriteViewPagerFragment {
            val fragment = FavoriteViewPagerFragment()
            with(Bundle()){
                putString("department", dept)
                fragment.arguments = this
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewpagerItemBinding.inflate(inflater, container, false)

        val manager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val divider  = DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
        val bundle = arguments

        with(binding.favoriteRV) {
            layoutManager = manager
            addItemDecoration(divider)

            val dept = bundle?.getString("department")
            when (dept) {
                /**
                 * 하드코딩 -> Rest 받기로 수정
                 */
//                "병원" -> adapter = HospitalAdapter(hospitalList())
                "약국" -> adapter = PharmacyListAdapter(pharmacyList())
                else -> throw IllegalStateException("Unexpected value: " + dept)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 삭제예정
     */
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
    /**
     * 삭제예정
     */
    fun pharmacyList() = mutableListOf<PharmacyListItem>().apply {
        add(
            PharmacyListItem("가 약국","약국","영업중",
                "12:00 점심시간","430m","가산동")
        )
        add(
            PharmacyListItem("나 약국","약국","영업중",
                "12:00 점심시간","530m","가산동")
        )
        add(
            PharmacyListItem("다 약국","약국","영업중",
                "12:00 점심시간","630m","가산동")
        )
        add(
            PharmacyListItem("라 약국","약국","영업중",
                "12:00 점심시간","730m","가산동")
        )
        add(
            PharmacyListItem("마 약국","약국","영업중",
                "12:00 점심시간","830m","가산동")
        )
        add(
            PharmacyListItem("바 약국","약국","영업중",
                "12:00 점심시간","930m","가산동")
        )
    }
}