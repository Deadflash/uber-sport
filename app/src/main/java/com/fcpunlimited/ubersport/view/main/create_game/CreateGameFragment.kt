package com.fcpunlimited.ubersport.view.main.create_game

import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.preference.PreferenceFragmentCompat
import com.fcpunlimited.ubersport.R
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.preference.Preference
import com.fcpunlimited.ubersport.view.main.create_game.dialog.DateDialogFragment


class CreateGameFragment : PreferenceActivity() {

    companion object {
        const val CREATE_GAME_FRAGMENT_PRESENTER = "createGameFragmentPresenter"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.create_game_preferences)
        val dp = findPreference("date") as DateDialogFragment
        dp.setText("2014-08-02")
        dp.setSummary("2014-08-02")
        dp.setOnPreferenceChangeListener(object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
                //your code to change values.
                dp.setSummary(newValue as String)
                return true
            }
        })
    }

//    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        super.onCreate(savedInstanceState)
////        addPreferencesFromResource(R.xml.create_game_preferences)
//    }
}
