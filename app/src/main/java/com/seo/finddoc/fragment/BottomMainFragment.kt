package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.adapter.FilterRecyclerViewAdapter
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.data.FilterItem
import com.seo.finddoc.databinding.BottomMainFragmentBinding

class BottomMainFragment : Fragment(),OnMapReadyCallback {
    private lateinit var binding: BottomMainFragmentBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomMainFragmentBinding.inflate(inflater, container, false)
        //NaverMap 객체 불러오기
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment?
        mapFragment!!.getMapAsync(this)
        //위치권한 관련 요청- 위치 추적 기능
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        val filterAdapter = ArrayAdapter.createFromResource(
            binding.root.context, R.array.filter_array_item, android.R.layout.simple_dropdown_item_1line
        )


        with(binding.filterAT) {
            setAdapter(filterAdapter)
/*            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = resources.getStringArray(R.array.filter_array_item)
                    when (position) {
                        0 -> {
                            toastMessage("""${item[0]}""")
                        }
                        1 -> {
                            toastMessage("""${item[1]}""")
                        }
                        else -> {
                            toastMessage("선택안함")
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }*/
            setOnItemClickListener { adapterView, _, position, _ ->
                toastMessage(adapterView.getItemAtPosition(position) as String)
            }
        }
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        with(binding.filterRV) {
            layoutManager = manager
            adapter = FilterRecyclerViewAdapter(filterData())
        }

        /**
         *  바텀 시트 수정하기
         */
        val bottomSheetListFragment = BottomSheetListFragment.newInstance("병원")
        bottomSheetListFragment.show(childFragmentManager,bottomSheetListFragment.tag)

        //검색화면으로 이동
        val activity = activity as MainActivity

        with(binding.searchBar) {
            setOnClickListener {
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container,SearchFragment.newInstance("검색화면"))
                    .commit()
            }
        }

        with(binding.listButton) {
        }

        return binding.root
    }
    private fun initNaverMapLocation(){
/*        //NaverMap 객체 불러오기
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment?
        mapFragment!!.getMapAsync(this)*/
    }

    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            initNaverMapLocation()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            TODO("Not yet implemented")
        }
    }
    private fun checkPermissionLocation() {
/*        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setRationaleMessage("지도 사용을 위해 위치제공접근권한이 필요합니다.")
            .setPermissions(
            ).check()*/
    }

//퍼미션 체크
/*    private fun permissionCheck() {
        permissionCheck = AppPermissionCheck(applicationContext,this@MainActivity)
        if (!permissionCheck.currentAppCheckPermission()) {
            permissionCheck.currentAppRequestPermission()
        }
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            //권한 거부시 위치 추적하지 않음
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else{
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun filterData() = mutableListOf<FilterItem>().apply {
        add(FilterItem(R.drawable.ic_baseline_local_hospital_24, "병원"))
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        //위치 소스 지정
        this.naverMap.locationSource = locationSource
        /**
         * 마커 표시 , 권한 확인?
         */


        //지도 중심 잡기 - UI 요소에 가려진 영역을 콘텐츠 패딩으로 지정
        this.naverMap.setContentPadding(0,0,0,0)


        //카메라 영역 제한 - 한반도
        this.naverMap.extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))
        // + 줌 레벨
        this.naverMap.minZoom = 5.0
        this.naverMap.maxZoom = 18.0

        //UI 설정 - map 컨트롤 활성화, 제스처
        val uiSettings = this.naverMap.uiSettings.apply {
            isCompassEnabled = false
            //isScaleBarEnabled = false
            //isZoomControlEnabled = false
            isIndoorLevelPickerEnabled = true
            /**
             * 현위치 버튼 위치바꾸되 정상 동작하게 만들기
             */
            isLocationButtonEnabled = true
            //기울임 비활성화
            isTiltGesturesEnabled = false
            isStopGesturesEnabled = true
        }
    }

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        fun newInstance(title: String): Fragment {
            val fragment: Fragment = BottomMainFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }

}