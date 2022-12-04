package com.seo.finddoc

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.seo.finddoc.adapter.FilterRecyclerViewAdapter
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.data.FilterItem
import com.seo.finddoc.databinding.ActivityMainBinding
import com.seo.finddoc.fragment.BottomSheetListFragment

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            with(supportFragmentManager.beginTransaction()) {
                add(R.id.container_view, )
            }
        }

        //NaverMap 객체 얻어오기
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
        mapFragment!!.getMapAsync(this@MainActivity)
        //런타임 권한 처리
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        val filterAdapter = ArrayAdapter.createFromResource(
            this@MainActivity, R.array.filter_array_item, android.R.layout.simple_dropdown_item_1line
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
        val manager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)

        with(binding.filterRV) {
            layoutManager = manager
            adapter = FilterRecyclerViewAdapter(filterData())
        }

        //바텀 시트
        val bottomSheetListFragment = BottomSheetListFragment()
        bottomSheetListFragment.show(supportFragmentManager,bottomSheetListFragment.tag)

        with(binding.listButton) {
        }

    }

//퍼미션 체크
/*    private fun permissionCheck() {
        permissionCheck = AppPermissionCheck(applicationContext,this@MainActivity)
        if (!permissionCheck.currentAppCheckPermission()) {
            permissionCheck.currentAppRequestPermission()
        }
    }*/

    //권한확인 결과 - 수정하기
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            /**
             * 권한 거부시 위치 추적하지 않음
             */
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else{
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        //지도 범위 설정

        //마커 표시

        //위치 소스 지정
        naverMap.locationSource = locationSource
        //권한 확인

        //지도 중심 잡기
        naverMap.setContentPadding(0,0,0,0)
        //초기 카메라 위치 지정?? -> 최근 검색위치나 현재 위치로 수정하기
//        val position = CameraPosition(
//            LatLng(37.472418245111065, 126.89592797777232),
//            16.0
//        )
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.472418245111065, 126.89592797777232))
        naverMap.moveCamera(cameraUpdate)


        //map 컨트롤 재배치
        val uiSettings = naverMap.uiSettings.apply {
            isCompassEnabled = false
            isScaleBarEnabled = false
            isZoomControlEnabled = false
            //현위치 버튼 컨트롤 사용
            isLocationButtonEnabled = true
            isIndoorLevelPickerEnabled = true
        }
//        with(binding){
//            locationButton.map = naverMap
//        }
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
    private fun filterData() = mutableListOf<FilterItem>().apply {
        add(FilterItem(R.drawable.ic_baseline_local_hospital_24, "병원"))
    }
}