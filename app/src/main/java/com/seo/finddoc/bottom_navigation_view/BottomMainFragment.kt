package com.seo.finddoc.bottom_navigation_view

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
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
import com.seo.finddoc.recyclerview.FilterButtonsAdapter
import com.seo.finddoc.recyclerview.FilterItemDecoration
import com.seo.finddoc.recyclerview.SearchFragment

/**
 *  프리퍼런스 수정 전까지 permissionCheck는 인스턴스 직접 생성
 *  권한 허용 여부 안 뜨면서 허가된 것 관계 있는지 체크
 */
class BottomMainFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: BottomMainFragmentBinding
    private lateinit var nMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private var isFabOpen = true
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
            //거의 동작하지 않음
            initNaverMapLocation()
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
                    .replace(R.id.container, SearchFragment.newInstance("검색화면"))
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

        behavior.addBottomSheetCallback(object  : BottomSheetBehavior.BottomSheetCallback(){
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment?
        mapFragment!!.getMapAsync(this)
    }

    //TedPermissionListener
    private val permissionListener = object : PermissionListener {
        //이미 권한 있거나, 요청 허가됐을 때
        override fun onPermissionGranted() {
            toastMessage("위치 권한 확인됨")
            initNaverMapLocation()
        }
        //권한 거부시
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            toastMessage("위치제공 허락을 해야 앱이 정상적으로 작동합니다")
            ActivityCompat.finishAffinity(activity as MainActivity)
        }
    }

    private fun checkPermissionLocation(){
        //위치권한 관련 요청
        TedPermission.create()
            .setRationaleTitle("권한 요청")
            .setPermissionListener(permissionListener)
//            .setRationaleMessage("지도 사용을 위해 위치제공접근권한이 필요합니다.")
                //권한이 없을 때 아이얼로그
            .setDeniedMessage("권한을 허용해주세요")
            .setPermissions(
                //요청할 권한들
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
    }

    /**
     * 삭제 예정?
     */
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
            //권한 거부시
            if (!locationSource.isActivated) {
                //위치추적 모드 none
                nMap.locationTrackingMode = LocationTrackingMode.None
            } else{
                nMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initRecycler(context: Context) {
        val multiadapter = FilterButtonsAdapter(context)
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
            object : FilterButtonsAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    /**
                     * 작동불가
                     */
                }
            }
        )

        multiadapter.notifyDataSetChanged()
    }
    private fun setMarker(marker: Marker, lat: Double, lng: Double, num: Int) {
        with(marker) {
            icon = OverlayImage.fromResource(R.drawable.ic_marker_clinic_icon)
            position = LatLng(lat, lng)
/*
            //원근감
//                isIconPerspectiveEnabled = true

            width = Marker.SIZE_AUTO
            height = Marker.SIZE_AUTO

            isForceShowIcon = true

            tag = num

            val listener = Overlay.OnClickListener { overlay ->
                toastMessage("마커 클릭 됨 ${overlay.tag}")
                false
            }

            onClickListener = listener*/
            //마커를 지도에 추가
            map = nMap
        }
    }


    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.nMap = naverMap
/*
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        //위치 추적 사용
        this.naverMap.locationSource = locationSource

        *//**
         * 중복으로 제거 예정
         *//*
        //현재위치 표시를 위한 권한 확인
        AppPermissionCheck(mContext,activity as MainActivity).currentAppRequestPermissions()
        //카메라 설정
        val cameraPosition = CameraPosition(
            LatLng(37.472676, 126.896030),
            16.0
        )
        naverMap.cameraPosition = cameraPosition

        val marker = Marker()
        with(marker) {
            position =  LatLng(37.472676, 126.896030)
            icon = OverlayImage.fromResource(R.drawable.ic_marker_clinic_icon)
            map = naverMap
        }

        //카메라 움직임
        naverMap.addOnCameraChangeListener  { reason, animated ->
//            Log.i("NaverMap", "카메라 변경 - reson: $reason, animated: $animated")
        }

        // 카메라의 움직임 종료
        naverMap.addOnCameraIdleListener {

        }

        //건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);





        *//**
         * 마커 표시(병원, 약국 구분), 권한 확인?
         *//*

*//*
val executor: Executor =
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            // 백그라운드 스레드
            val markers = mutableListOf<Marker>()

            repeat(1000) {
                markers += Marker().apply {
                    position = ...
                    icon = ...
                    captionText = ...
                }
            }

            handler.post {
                // 메인 스레드
                markers.forEach { marker ->
                    marker.map = naverMap
                }
            }
        }*//*




        *//*       val marker = Marker()
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

               //마커를 지도에 추가
               marker.map = naverMap

               marker.setOnClickListener {
       //            Toast.makeText(this, "마커 1 클릭", Toast.LENGTH_SHORT).show()
                   // onMapClick 이벤트 전파
                   false
               }*//*

        *//**
         * 경로 표시하기
         *//*
*//*        val bounds = LatLngBounds.Builder()
            .include(LatLng(37.5640984, 126.9712268))
            .include(LatLng(37.5651279, 126.9767904))
            .include(LatLng(37.5625365, 126.9832241))
            .include(LatLng(37.5585305, 126.9809297))
            .include(LatLng(37.5590777, 126.974617))
            .build()*//*

        //지도 클릭 이벤트 - 좌표 전달
        naverMap.setOnMapClickListener { pointF, latLng ->
            *//*           Toast.makeText(this, "${latLng.latitude}, ${latLng.longitude}",
                           Toast.LENGTH_SHORT).show()*//*
        }




        //위치 변경 이벤트
        naverMap.addOnLocationChangeListener { location ->
*//*            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()*//*
        }
        //지도 중심 잡기 - UI 요소에 가려진 영역을 콘텐츠 패딩으로 지정
        this.naverMap.setContentPadding(0,0,0,0)


        //카메라 영역 제한 - 한반도
        this.naverMap.extent = LatLngBounds(
            LatLng(31.43, 122.37),
            LatLng(44.35, 132.0)
        )
        // + 줌 레벨
        this.naverMap.minZoom = 5.0
        this.naverMap.maxZoom = 18.0

        //실내지도 활성화
        naverMap.isIndoorEnabled = true


        //UI 설정 - map 컨트롤 활성화, 제스처
        val uiSettings = this.naverMap.uiSettings.apply {
            //나침반
            isCompassEnabled = false
            //축척바
            isScaleBarEnabled = false
            //줌버튼
            //isZoomControlEnabled = false
            //실내지도 구역의 층
            isIndoorLevelPickerEnabled = true
            *//**
             * 현위치 버튼 위치바꾸되 정상 동작하게 만들기
             *//*
            isLocationButtonEnabled = true
            //기울임 비활성화
            isTiltGesturesEnabled = false
            isStopGesturesEnabled = true

            //네이버 로고 조작
//            logoGravity
//            logoMargin
//            isLogoClickEnabled = false
        }*/
    }


    companion object{

//        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

        fun newInstance(title: String): Fragment {
            val fragment: Fragment = BottomMainFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }

}