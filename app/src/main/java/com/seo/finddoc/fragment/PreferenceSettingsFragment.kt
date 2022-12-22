package com.seo.finddoc.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.seo.finddoc.MainActivity
import com.seo.finddoc.R
import com.seo.finddoc.bottom_navigation_view.BottomMypageFragment

class PreferenceSettingsFragment : Fragment()  {

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

    class SettingsFragment : PreferenceFragmentCompat(){
        //        private lateinit var preferenceManager: AppSettingPreferenceManager
        //        private lateinit var sp: SharedPreferences
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_preferences, rootKey)
            val autoLogin : SwitchPreferenceCompat? = findPreference("autologin")
            val medicalReminder : SwitchPreferenceCompat? = findPreference("medicalReminder")
//            preferenceManager = AppSettingPreferenceManager.getInstance(applicationContext)

/*            autoLogin?.setOnPreferenceChangeListener { preference, newValue ->
            }
            medicalReminder?.setOnPreferenceChangeListener { preference, newValue ->
            }*/
        }

    }

}