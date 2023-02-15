package com.seo.finddoc.bottom_navigation_view

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
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.common.LOCATION_PERMISSION_REQUEST_CODE
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.data.MedicalDTO
import com.seo.finddoc.databinding.BottomMainFragmentBinding
import com.seo.finddoc.json_entity.HospitalEntityRoot
import com.seo.finddoc.json_entity.HospitalItem
import com.seo.finddoc.recyclerview.HospitalAdapter
import com.seo.finddoc.recyclerview.SearchFragment
import com.seo.finddoc.rest_setting.FindDocRetrofitGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  프리퍼런스 수정 전까지 permissionCheck는 인스턴스 직접 생성
 *  권한 허용 여부 안 뜨면서 허가된 것 관계 있는지 체크
 *  약국 data 추가
 *  리사이클러뷰 아이템 잘림 현상
 * 겹칠 경우 선택하여 정보 볼 수 있게
 */
class BottomMainFragment : Fragment(), OnMapReadyCallback {
    private var _binding: BottomMainFragmentBinding? =null
    private val binding get() = _binding!!
    private lateinit var nMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var mContext : Context
    private lateinit var mainBottomSheet: LinearLayout
    private lateinit var bsEachPlace: LinearLayout
    private var fabIndex = 0
    private lateinit var mainBSBehavior: BottomSheetBehavior<View>
    private lateinit var infoBSBehavior: BottomSheetBehavior<View>
    private val hospitalAdapter by lazy{
        HospitalAdapter()
    }

    private val medicalDTO = MedicalDTO()

    private lateinit var result : ArrayList<HospitalItem>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomMainFragmentBinding.inflate(inflater, container, false)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissionLocation()
        } else {
            // 23 미만
            initNaverMapLocation()
        }

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

        /**
         * 병원/약국 구분하는 스피너 완성되면 filterbutton 관련 파일 삭제
         */
        val medicalTypeAdapter = ArrayAdapter.createFromResource(
            mContext,
            R.array.filter_category,
            android.R.layout.simple_dropdown_item_1line
        )

        with(binding.filterAT) {
            setAdapter(medicalTypeAdapter)
            setOnItemClickListener { adapterView, _, position, _ ->
                medicalDTO.ctg = adapterView.getItemAtPosition(position) as String
            }
        }

        //병원 또는 약국 목록
        initMainBottomSheet()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inquiryResult()
    }

    //병원 또는 약국 목록
    private fun initMainBottomSheet() {
        mainBottomSheet = binding.root.findViewById(R.id.bottomSheetLayout)

        val bottomSheetRV = mainBottomSheet.findViewById<RecyclerView>(R.id.bottomSheetRV)
        with(bottomSheetRV) {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            mainBSBehavior = from(mainBottomSheet)
//            filter(items)
            adapter = hospitalAdapter
        }

        mainBSBehavior.state = STATE_HALF_EXPANDED
        mainBSBehavior.addBottomSheetCallback(object  : BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_HIDDEN -> {
                        binding.fabList.visibility = View.VISIBLE
                    }
                    STATE_EXPANDED -> {
                        mainBSBehavior.peekHeight = 250
                    }
                    STATE_HALF_EXPANDED -> {
                    }
                    STATE_COLLAPSED -> {
                    }
                    STATE_DRAGGING -> {
                    }
                    STATE_SETTLING -> {
                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        with(binding) {
            //목록보기
            fabList.setOnClickListener {
                mainBSBehavior.state = STATE_EXPANDED
                convertFab(1)
            }
            //지도보기
            fabMap.setOnClickListener {
                mainBSBehavior.state = STATE_HIDDEN
                convertFab(0)
            }
        }

    }

    private fun convertFab(index: Int){
        // 0은 지도보기, 1은 목록보기
        fabIndex = index
        fabIndex += 1
        if (fabIndex > 1) {
            fabIndex = 0
        }
        when (fabIndex) {
            0 -> {
                binding.fabList.visibility = View.GONE
                binding.fabMap.visibility = View.VISIBLE
            }
            else -> {
                binding.fabList.visibility = View.VISIBLE
                binding.fabMap.visibility = View.GONE
            }
        }
    }

    private fun inquiryResult() {
        binding.progressbar.visibility = View.VISIBLE
        /*       val call : Call<HospitalEntityRoot>
                       = FindDocRetrofitGenerator.generateHospitalInstance().findHospitalInfo(
                   FindDocRetrofitGenerator.decodePublicDataServiceKey(),
                   10,
                   110000,
                   "json"
               )

               call.enqueue(object : Callback<HospitalEntityRoot> {
                   override fun onResponse(
                       call: Call<HospitalEntityRoot>,
                       response: Response<HospitalEntityRoot>
                   ){
                       if (response.isSuccessful) {
                           val hospitalRoot = response.body() as HospitalEntityRoot
       //                    Log.e("RESULT",hospitalRoot.response.body.items.item.toString())
                           val result = hospitalRoot.response.body.items.item
                           hospitalAdapter.submitList(result)
       //                    filter(hospitalRoot.response.body.items.item)
                       }
                   }
                   override fun onFailure(call: Call<HospitalEntityRoot>, t: Throwable) {
                       toastMessage("""실패 - ${t.printStackTrace()}""")
       //                Log.e("RESULT", t.toString())
                       t.printStackTrace()
                   }
               })*/

        CoroutineScope(Dispatchers.IO).launch{
            val response = FindDocRetrofitGenerator.generateHospitalInstance().findHospitalInfoByCoroutine(
                FindDocRetrofitGenerator.decodePublicDataServiceKey(),
                50,
                110000,
                "json"
            )

            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    binding.progressbar.visibility = View.GONE
                    val hospitalRoot = response.body() as HospitalEntityRoot
                    result = hospitalRoot.response.body.items.item
                    hospitalAdapter.submitList(result)
                    addMarker(result)
                } else {
                    Log.e("RESULT", response.code().toString())
                }
            }
        }
    }

    private val markerMaps = mutableMapOf<Marker, HospitalItem>()
    private fun addMarker(items: ArrayList<HospitalItem>){
        items.forEach {
            with(Marker()) {
                position = LatLng(it.YPos, it.XPos)
                icon = if (it.clCdNm == "약국") {
                    OverlayImage.fromResource(R.drawable.ic_marker_pharmacy)
                } else {
                    OverlayImage.fromResource(R.drawable.ic_marker_clinic)
                }

                //마커 아이콘 크기(wrap_content로)
                width = Marker.SIZE_AUTO
                height = Marker.SIZE_AUTO

                captionText = it.yadmNm
                captionRequestedWidth = 100
                captionTextSize = 12f
                captionMinZoom = 10.0
//                captionMaxZoom = 14.0

                //겹쳐도 무조건 표시
                isForceShowIcon = true

                //지도 심벌 겹칠 시 숨김
                isHideCollidedSymbols = true

                //원근감
                isIconPerspectiveEnabled = true

                setOnClickListener {
                    displayItemInfo(markerMaps[this]!!)
                    //마커에서 이벤트 소비
                    true
                }

                markerMaps[this]=it
                //마커를 지도에 추가
                map = nMap
            }
        }
    }

    private fun displayItemInfo(item: HospitalItem) {
        mainBottomSheet.visibility = View.GONE
        binding.fabList.visibility = View.GONE
        binding.fabMap.visibility = View.GONE

        bsEachPlace = binding.root.findViewById(R.id.bottomSheetEachPlaceLayout)
        bsEachPlace.visibility = View.VISIBLE

        with(bsEachPlace) {
            findViewById<TextView>(R.id.hospitalTV).text = item.yadmNm
            findViewById<TextView>(R.id.dongTV).text = item.addr
            findViewById<TextView>(R.id.telTV).text = item.telno
            findViewById<TextView>(R.id.hopitalInfoTV1).text = item.ykiho
            findViewById<TextView>(R.id.hopitalInfoTV2).text = item.YPos.toString()
        }

        infoBSBehavior = from(bsEachPlace)
        infoBSBehavior.state = STATE_EXPANDED
        infoBSBehavior.addBottomSheetCallback(object  : BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_HIDDEN -> {
                    }
                    STATE_EXPANDED -> {
                    }
                    STATE_HALF_EXPANDED -> {
                    }
                    STATE_COLLAPSED -> {
                    }
                    STATE_DRAGGING -> {
                    }
                    STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

    }

    //NaverMap 객체 불러오기
    private fun initNaverMapLocation() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    //TedPermissionListener
    private val permissionListener = object : PermissionListener {
        //이미 권한 있거나, 요청 허가됐을 때
        override fun onPermissionGranted() {
            initNaverMapLocation()
        }
        //권한 거부시
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            toastMessage("위치제공 허락을 해야 앱이 정상적으로 작동합니다")
            ActivityCompat.finishAffinity(activity as MainActivity)
        }
    }

    /**
     * 위치 권한 요청 작동 확인하기
     */
    private fun checkPermissionLocation(){
        TedPermission.create()
            .setRationaleTitle("권한 요청")
            .setDeniedMessage("권한을 허용해주세요")
            .setPermissionListener(permissionListener)
//            .setRationaleMessage("지도 사용을 위해 위치제공접근권한이 필요합니다.")
            //권한이 없을 때 아이얼로그
            .setPermissions(
                //요청할 권한들
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
        //위치권한 관련 요청
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
                Log.d("권한 거부", "권한 거부됨")
                nMap.locationTrackingMode = LocationTrackingMode.None
            } else{
                Log.d("권한 승인", "권한 승인됨")
                nMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //getMapAsync로 맵 활성화시 map의 기능 설정
    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        nMap = naverMap
        //위치 추적 사용
        naverMap.locationSource = locationSource

        /**
         * 사용자의 현 위치로 이동
         */
        //카메라 설정
        val cameraPosition = CameraPosition(
            LatLng(37.472676, 126.896030),
//            LatLng(37.625002, 127.076203),
            16.0
        )
        naverMap.cameraPosition = cameraPosition

        /**
         * 마커 테스트 - 병원, 권한 확인?
         */
/*        val marker = Marker().apply {
//        position =  LatLng(37.472676, 126.896030)
//        position =  LatLng(37.625002, 127.076203)
            position = LatLng(
                naverMap.cameraPosition.target.latitude,
                naverMap.cameraPosition.target.longitude
            )
            icon = OverlayImage.fromResource(R.drawable.ic_marker_clinic)
//        icon = OverlayImage.fromResource(R.drawable.ic_marker_pharmacy)
            //마커 아이콘 크기(wrap_content로)
            width = Marker.SIZE_AUTO
            height = Marker.SIZE_AUTO

            captionText = "병원명1"
            captionTextSize = 12f

            //겹쳐도 무조건 표시
            */
        /**
         * 겹칠 경우 선택하여 이벤트 구현
         *//*
            isForceShowIcon = true

            //지도 심벌 겹칠 시 숨김
            isHideCollidedSymbols = true

            //마커를 지도에 추가
            map = naverMap
        }*/

/*        marker.setOnClickListener {
//            infoWindow.open(marker)
            // 이벤트 소비
            true
        }*/

        val infoWindow = InfoWindow().apply {
            setOnClickListener {
                close()
                true
            }
        }

        /*
        //카메라 움직임
        naverMap.addOnCameraChangeListener  { reason, animated ->
            Log.i("NaverMap", "카메라 변경 - reson: $reason, animated: $animated")
        }

        // 카메라의 움직임 종료
        naverMap.addOnCameraIdleListener {

        }

        */

        /*
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
               }*/
        /**
         * 경로 표시하기
         */
        /*        val bounds = LatLngBounds.Builder()
            .include(LatLng(37.5640984, 126.9712268))
            .include(LatLng(37.5651279, 126.9767904))
            .include(LatLng(37.5625365, 126.9832241))
            .include(LatLng(37.5585305, 126.9809297))
            .include(LatLng(37.5590777, 126.974617))
            .build()*//*


        //위치 변경 이벤트
        naverMap.addOnLocationChangeListener { location ->
            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()
        }

        */

        //지도 클릭
        naverMap.setOnMapClickListener { _, latLng ->
            infoBSBehavior.state = STATE_HIDDEN
            /**
             * mainBSBehavior 숨기기
             */
        }


        //지도 중심 잡기 - UI 요소에 가려진 영역을 콘텐츠 패딩으로 지정
        naverMap.setContentPadding(0,0,0,0)

        //카메라 영역 제한 - 한반도
        naverMap.extent = LatLngBounds(
            LatLng(31.43, 122.37),
            LatLng(44.35, 132.0)
        )
        // 줌 레벨
        naverMap.minZoom = 5.0
        naverMap.maxZoom = 18.0

        //실내지도 활성화
        naverMap.isIndoorEnabled = true

        //주위 건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)

        //UI 설정 - map 컨트롤 활성화, 제스처
        val uiSettings = naverMap.uiSettings.apply {
            //나침반
            isCompassEnabled = false
            //축척바
            isScaleBarEnabled = false
            //줌
            //isZoomControlEnabled = false
            //실내지도 구역의 층
            isIndoorLevelPickerEnabled = true
            //현위치 컨트롤
            isLocationButtonEnabled = true
            //기울임 비활성화
            isTiltGesturesEnabled = false
            isStopGesturesEnabled = true

            //네이버 로고
//            logoGravity
//            logoMargin
//            isLogoClickEnabled = false
        }
    }

    /**
     * 사용자 현재 위치 받아오기
     */

    /**
     * 좌표 주소 변환
     */

    /**
     * 스피너 선택값과 분류코드, 종별코드, 진료과목코드 일치시키기
     */


    //    var currentLocation : Location?

    /**
     * 삭제예정 - bottom sheet 내용
     */
//    private fun pharmacyList() = mutableListOf<PharmacyListItem>().apply {
//        add(
//            PharmacyListItem("가 약국","약국","영업중",
//                "12:00 점심시간","430m","가산동")
//        )
//        add(
//            PharmacyListItem("나 약국","약국","영업중",
//                "12:00 점심시간","530m","가산동")
//        )
//        add(
//            PharmacyListItem("다 약국","약국","영업중",
//                "12:00 점심시간","630m","가산동")
//        )
//        add(
//            PharmacyListItem("라 약국","약국","영업중",
//                "12:00 점심시간","730m","가산동")
//        )
//        add(
//            PharmacyListItem("마 약국","약국","영업중",
//                "12:00 점심시간","830m","가산동")
//        )
//        add(
//            PharmacyListItem("바 약국","약국","영업중",
//                "12:00 점심시간","930m","가산동")
//        )
//}
    /**
     * 서울 중심지역 구하기  - 위도 평균, 경도 평균(총합/갯수),
    중앙에 레이아웃, style

    onviewCreated에서 호출
    invisible
     */
//    @MainThread
//    fun filter(items: ArrayList<HospitalItem>) : List<HospitalItem> {
//        return items.filter {
//            it.sidoCdNm == "서울"
//        }.toList()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

