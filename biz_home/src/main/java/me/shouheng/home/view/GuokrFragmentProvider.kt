package me.shouheng.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.vmlib.comn.ContainerActivity

/**
 * Guokr fragment provider.
 *
 * @Author wangshouheng
 * @Time 2022/12/7
 */
class GuokrFragmentProvider: ContainerActivity.FragmentProvider {
    override fun get(activity: ContainerActivity?, intentExtras: Bundle?): Fragment {
        return ARouter.getInstance().build("/guokr/entrance").navigation() as Fragment
    }
}
