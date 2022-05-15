package me.shouheng.sample.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import me.shouheng.sample.R
import me.shouheng.utils.ktx.colorOf
import me.shouheng.utils.ktx.drawableOf
import me.shouheng.utils.ktx.tint
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.BasePreferenceFragment
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2019-10-02 13:30
 */
@FragmentConfiguration
class SamplePreference : BasePreferenceFragment<EmptyViewModel>() {

    override fun getPreferencesResId(): Int = R.xml.preferences

    override fun doCreateView(savedInstanceState: Bundle?) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(colorOf(R.color.cold_theme_background))
        findPreference<Preference>("key_exit")?.setOnPreferenceClickListener {
            activity?.finish()
            true
        }
        findPreference<Preference>("key_setting_theme")?.apply {
            this.icon = drawableOf(R.drawable.eyepetizer_baseline_mail_24).tint(Color.BLACK)
        }
    }
}