package com.seo.finddoc.common

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.seo.finddoc.presentation.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.presentation.fragment.BottomMypageFragment

class PreferenceSettingsFragment : Fragment() {
    /**
     * 설정화면 이벤트 등록하기
     */
    companion object{
        fun newInstance(title: String): Fragment {
            val fragment: Fragment = PreferenceSettingsFragment()
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
        val rootView = inflater.inflate(R.layout.preference_settings_fragment, container, false);

        //툴바 셋팅
        val activity = activity as MainActivity
        activity.setSupportActionBar(rootView.findViewById<Toolbar>(R.id.settings_toolbar))
        val toolbar = activity.supportActionBar
        toolbar?.let{
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
        }

        //옵션 메뉴
        setHasOptionsMenu(true)

        //설정화면
        childFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()

        return rootView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (activity as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, BottomMypageFragment.newInstance("마이페이지"))
                    .commit()
            }
            else -> throw IllegalStateException("")
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_preferences, rootKey)
            val autoLogin : SwitchPreferenceCompat? = findPreference("autologin")
            val medicalReminder : SwitchPreferenceCompat? = findPreference("medicalReminder")

            val idPreference: EditTextPreference? =  findPreference("id")

            //설정 항목에 이벤트 핸들러 지정
            idPreference?.setOnPreferenceClickListener { preference, ->
                Log.d("seo", "preference key : ${preference.key}")
                true
            }

            //이벤트가 발생한 Preference 객체와 바뀐 값을 가져옴
            idPreference?.setOnPreferenceChangeListener { preference, newValue ->
                Log.d("seo", "preference key : ${preference.key}, newValue : $newValue")
                true
            }



//            preferenceManager = AppSettingPreferenceManager.getInstance(applicationContext)

/*            autoLogin?.setOnPreferenceChangeListener { preference, newValue ->
            }
            medicalReminder?.setOnPreferenceChangeListener { preference, newValue ->
            }*/
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            TODO("Not yet implemented")
        }

        override fun onResume() {
            super.onResume()
        }

        override fun onPause() {
            super.onPause()
        }
    }

}