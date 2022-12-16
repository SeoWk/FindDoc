package com.seo.finddoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.seo.finddoc.common.AppSettingPreferenceManager

class PreferenceSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference_settings)
        supportFragmentManager.beginTransaction().replace(R.id.settings_container, SettingsFragment()).commit()
//        preferenceManager = AppSettingPreferenceManager.getInstance(this)
//        sp = PreferenceManager.getDefaultSharedPreferences(this)

    }
    inner class SettingsFragment : PreferenceFragmentCompat(){
        private lateinit var preferenceManager: AppSettingPreferenceManager
//        private lateinit var sp: SharedPreferences
        private var autoLogin : SwitchPreferenceCompat? = null
        private var medicalReminder : SwitchPreferenceCompat? = null


        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings_preferences, rootKey)
            if (rootKey == null) {
                autoLogin = findPreference("autologin")
                medicalReminder = findPreference("medicalReminder")
            }
            preferenceManager = AppSettingPreferenceManager.getInstance(applicationContext)

/*            autoLogin?.setOnPreferenceChangeListener { preference, newValue ->
            }
            medicalReminder?.setOnPreferenceChangeListener { preference, newValue ->
            }*/
        }

    }

}