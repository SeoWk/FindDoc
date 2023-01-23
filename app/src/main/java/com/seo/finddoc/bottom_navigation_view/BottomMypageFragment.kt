package com.seo.finddoc.bottom_navigation_view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.common.PreferenceSettingsFragment
import com.seo.finddoc.common.toastMessage
import com.seo.finddoc.databinding.BottomMypageFragmentBinding

/**
 * 로그인 이벤트 구현하기
 */
class BottomMypageFragment  : Fragment(){
    private var _binding: BottomMypageFragmentBinding? = null
    private val binding get() = _binding!!
    companion object{
        fun newInstance(title: String): Fragment {
            val fragment: Fragment = BottomMypageFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomMypageFragmentBinding.inflate(inflater,container,false)

        //툴바 셋팅
        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.toolbar)
        val toolbar = activity.supportActionBar
        toolbar?.let{
            it.setDisplayShowTitleEnabled(false)    //기본 제목 제거
//            it.setDisplayShowCustomEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)  //홈(뒤로가기) 버튼 생성
            it.setHomeAsUpIndicator(R.drawable.ic_bell) //홈 기본 이미지 변경
        }

        //옵션 메뉴
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)

        //로그인 정보
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                toastMessage("토큰 정보 보기 실패")
            } else if (tokenInfo != null) {
                toastMessage("토큰 정보 보기 성공")
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.e("Callback", "$token")
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(activity, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(activity, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(activity, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(activity, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(activity, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(activity, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(activity, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(activity, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(activity, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Toast.makeText(activity, "로그인 성공", Toast.LENGTH_SHORT).show()
            }
        }

        //카카오 로그인
        binding.kakaoLoginButton.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(activity)){
                UserApiClient.instance.loginWithKakaoTalk(activity, callback = callback)

            }else{
                UserApiClient.instance.loginWithKakaoAccount(activity, callback = callback)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
        inflater.inflate(R.menu.menu_mypage_optionmenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                toastMessage("알림 페이지 만들기")
            }
            R.id.menu_settings -> {
                (activity as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, PreferenceSettingsFragment.newInstance("설정화면"))
                    .commit()
            }
            else -> throw IllegalStateException("")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loginButtonClicked(view: View){

    }
}