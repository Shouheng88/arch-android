package me.shouheng.eyepetizer;

import me.shouheng.mvvm.base.anno.ActivityConfiguration;
import me.shouheng.mvvm.base.anno.StatusBarConfiguration;
import me.shouheng.mvvm.base.anno.StatusBarMode;

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-01-05 11:50
 */
@ActivityConfiguration(
        statusBarConfiguration = @StatusBarConfiguration(
                statusBarMode = StatusBarMode.LIGHT
        )
)
public class SampleActivity {
}
