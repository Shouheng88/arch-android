package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.mvvm.base.BasePreferenceFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.comn.EmptyViewModel
import me.shouheng.sample.R

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-02 13:30
 */
@FragmentConfiguration(preferencesResId = R.xml.preferences)
class SamplePreference : BasePreferenceFragment<EmptyViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
     }

}