package com.seo.finddoc.fragment

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.annotation.UiThread
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.data.FilterData
import com.seo.finddoc.databinding.BottomMainFragmentBinding
import com.seo.finddoc.recyclerview.FilterItemDecoration
import com.seo.finddoc.recyclerview.FilterRecyclerViewAdapter

class BottomMainFragment : Fragment(),OnMapReadyCallback {
    private lateinit var binding: BottomMainFragmentBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private var isFabOpen = false
    private lateinit var mContext : Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomMainFragmentBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissionLocation()
        } else {
            initNaverMapLocation()
        }

        /**
         * 스피너 양식 변경
         */
        val filterCtgAdapter = ArrayAdapter.createFromResource(
            binding.root.context, R.array.filter_category, android.R.layout.simple_dropdown_item_1line
        )
        val filterSubjAdapter = ArrayAdapter.createFromResource(
            binding.root.context, R.array.filter_medical_subject, android.R.layout.simple_dropdown_item_1line
        )
        /**
         * 병원 선택시 진료과목 스피너
         */
        /*      with(binding.filterAT1) {
                  setAdapter(filterCtgAdapter)
         *//*         onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        }*//*
            setOnItemClickListener { adapterView, _, position, _ ->
                val ctg = adapterView.getItemAtPosition(position) as String
                if(ctg == "병원") {
                    binding.filterAT2Layout.isVisible = true
                    binding.filterAT2.setAdapter(filterSubjAdapter)
                }else{
                    binding.filterAT2Layout.isGone = true
                }
            }
        }*/

        with(binding.filterAT2) {
            setOnItemClickListener { adapterView, _, position, _ ->
                toastMessage(adapterView.getItemAtPosition(position) as String)
            }
        }

        //버튼 리사이클러 뷰
//        initRecycler(container!!.context)
        initRecycler(mContext)

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

//        val bottomSheetListFragment = BottomSheetListFragment.newInstance("병원")
//        bottomSheetListFragment.show(childFragmentManager,BottomSheetListFragment.TAG)
        /**
         * 바텀 시트 구현하기 - include 안되는 중
         */
        val bottomSheetLayout = binding.root.findViewById(R.id.bottomSheetLayout) as LinearLayout
        val behavior= BottomSheetBehavior.from(bottomSheetLayout)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        behavior.peekHeight = 280
        behavior.isHideable = true

        with(binding) {
            fabList.setOnClickListener {
                toggleFab()
                behavior.peekHeight = 280
                behavior.isHideable = true
            }
            fabMap.setOnClickListener {
                toggleFab()
            }
        }

        behavior.addBottomSheetCallback(object  : BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                with(binding){
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            /*                        fabMap.isVisible = true
                                                    fabMap.isFocusable = true*/
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun toggleFab(){
        with(binding) {
            // 지도 보기 클릭
            if (isFabOpen) {
                fabMap.isClickable = false
                fabMap.isGone = true
                fabList.isClickable = true
                fabList.isVisible = true

                // 목록보기 클릭
            } else {
                fabList.isClickable = false
                fabList.isGone = true
                //지도 보기 활성화
                fabMap.isClickable = true
                fabMap.isVisible = true
            }
            isFabOpen = !isFabOpen
        }
    }
    //NaverMap 객체 불러오기
    private fun initNaverMapLocation() {
/*        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment?
        mapFragment!!.getMapAsync(this)*/
    }

    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            initNaverMapLocation()
        }
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            toastMessage("위치제공 허락을 해야 앱이 정상적으로 작동합니다")
            (activity as MainActivity).finish()
        }
    }

    private fun checkPermissionLocation(){
        //위치권한 관련 요청- 위치 추적 기능
        TedPermission.create()
            .setRationaleTitle("위치권한 요청")
            .setPermissionListener(permissionListener)
            .setRationaleMessage("지도 사용을 위해 위치제공접근권한이 필요합니다.")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
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

    private fun initRecycler(context: Context) {
        val multiadapter = FilterRecyclerViewAdapter(context)
        with(binding.filterRV) {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = multiadapter
            addItemDecoration(FilterItemDecoration(10))
        }
        multiadapter.datas = mutableListOf<FilterData>().apply {
//            add(FilterData("전체",3))
            add(FilterData("병원",1))
            add(FilterData("진료중",2))
            add(FilterData( "기타",2))
        }

        multiadapter.setOnItemClickListener(
            object : FilterRecyclerViewAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    /**
                     * 작동불가
                     */
                }
            }
        )

        multiadapter.notifyDataSetChanged()
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        //위치 소스 지정
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        this.naverMap.locationSource = locationSource
        /**
         * 마커 표시(병원, 약국 구분), 권한 확인?
         */
        val marker = Marker()
        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_clinic_icon)
//        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_pharmacy_icon)
        marker.position = LatLng(
            naverMap.cameraPosition.target.latitude,
            naverMap.cameraPosition.target.longitude
        )
//        마커 아이콘 크기(wrap_content로)
        marker.width = Marker.SIZE_AUTO
        marker.height = Marker.SIZE_AUTO

        //겹쳐도 무조건 표시
        marker.isForceShowIcon = true

        marker.map = naverMap

        //카메라 움직임
        naverMap.addOnCameraChangeListener  { reason, animated ->
            Log.i("NaverMap", "카메라 변경 - reson: $reason, animated: $animated")
        }

        // 카메라의 움직임 종료
        naverMap.addOnCameraIdleListener {

        }
        //위치 변경 이벤트
        naverMap.addOnLocationChangeListener { location ->
/*            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()*/
        }
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