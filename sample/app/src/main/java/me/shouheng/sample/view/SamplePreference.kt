package me.shouheng.sample.view

import android.os.Bundle
import android.view.View
import me.shouheng.mvvm.base.BasePreferenceFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.sample.R
import me.shouheng.utils.app.ResUtils

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-02 13:30
 */
@FragmentConfiguration()
class SamplePreference : BasePreferenceFragment<EmptyViewModel>() {

    override fun getPreferencesResId(): Int = R.xml.preferences

    override fun doCreateView(savedInstanceState: Bundle?) {
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.setBackgroundColor(ResUtils.getColor(R.color.cold_theme_background))
    }
}