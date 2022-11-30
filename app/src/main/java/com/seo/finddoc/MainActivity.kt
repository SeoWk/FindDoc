package com.seo.finddoc

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import com.seo.finddoc.adapter.FilterRecyclerViewAdapter
import com.seo.finddoc.common.LOCATION_PERMISSION_REQUEST_CODE
import com.seo.finddoc.common.PermissionCheck
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
        mapFragment!!.getMapAsync(this@MainActivity)
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
        with(binding.filterRV) {
            layoutManager = manager
            adapter = FilterRecyclerViewAdapter(filterData())
        }

    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0

        //지도 범위 제한 - 수정하기
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0
        //초기 위치 지정 - 최근 검색위치나 현재 위치로 수정하기
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.472418245111065, 126.89592797777232))
        naverMap.moveCamera(cameraUpdate)

        //마커 표시

        //현재 위치 잡기
        val uiSettings = naverMap.uiSettings.apply {
            isLocationButtonEnabled = true
        }
        locationSource = FusedLocationSource(this@MainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
    }
    private lateinit var permissionCheck: PermissionCheck
    override fun onResume() {
        super.onResume()
    }
    private fun permissionCheck() {
        permissionCheck = PermissionCheck(applicationContext,this@MainActivity)
        if (!permissionCheck.currentAppCheckPermission()) {
            permissionCheck.currentAppRequestPermission()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!locationSource.isActivated){
            naverMap.locationTrackingMode = LocationTrackingMode.None
            return
        }else{
            naverMap.locationTrackingMode = LocationTrackingMode.Follow

        }
    }

    private fun filterData() = mutableListOf<FilterItem>().apply {
        add(FilterItem(R.drawable.ic_baseline_local_hospital_24, "병원"))
    }
}