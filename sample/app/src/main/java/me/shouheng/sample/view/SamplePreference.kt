package me.shouheng.sample.view

import android.os.Bundle
import android.view.View
import me.shouheng.sample.R
import me.shouheng.utils.ktx.colorOf
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.BasePreferenceFragment
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * @author <a href="mailto:shouheng2020@gmail.com">WngShhng</a>
 * @version 2019-10-02 13:30
 */
@FragmentConfiguration
class SamplePreference : BasePreferenceFragment<EmptyViewModel>() {

    override fun getPreferencesResId(): Int = R.xml.preferences

    override fun doCreateView(savedInstanceState: Bundle?) {}

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.setBackgroundColor(colorOf(R.color.cold_theme_background))
        findPreference("key_exit").setOnPreferenceClickListener {
            activity.finish()
            true
        }
    }
}