/*
package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.seo.finddoc.R
import com.seo.finddoc.data.HospitalListItem
import com.seo.finddoc.data.PharmacyListItem
import com.seo.finddoc.databinding.BottomSheetLayoutBinding
import com.seo.finddoc.recyclerview.HospitalListAdapter
import com.seo.finddoc.recyclerview.PharmacyListAdapter

class BottomSheetListFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!
    private  lateinit var parent_fab : ExtendedFloatingActionButton

    companion object {
        const val TAG = "BottomSheetListFragment"
        fun newInstance(dept: String?): BottomSheetListFragment {
            val fragment = BottomSheetListFragment()
            with(Bundle()){
                putString("department", dept)
                fragment.arguments = this
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.bottomsheet_list,container,false)
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)

        val manager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val divider  = DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
        val bundle = arguments

        with(binding.bottomSheetRV) {
            layoutManager = manager
            addItemDecoration(divider)
//            adapter = HospitalListAdapter(hospitalList())

            */
/**
 *             병원/약국 나뉘면 주석 해제
 */
/*


            val dept = bundle?.getString("department")
            when (dept) {
                "병원" -> adapter = HospitalListAdapter(hospitalList())
                "약국" -> adapter = PharmacyListAdapter(pharmacyList())
                else -> throw IllegalStateException("Unexpected value: " + dept)
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

        behavior.addBottomSheetCallback(object  : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                with(binding){
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            dismiss()
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            */
/*                        fabMap.isVisible = true
                                                    fabMap.isFocusable = true*//*

                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                        }
                    }
                }

            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        //재 생성시 성 유지
        behavior.saveFlags
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

    private fun pharmacyList() = mutableListOf<PharmacyListItem>().apply {
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

    fun setParentFab(fab : ExtendedFloatingActionButton) {
        this.parent_fab = fab
    }
}
*/
