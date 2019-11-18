package me.shouheng.mvvm.comn;

import android.app.Application;
import android.support.annotation.NonNull;
import me.shouheng.mvvm.base.BaseViewModel;

/**
 * Empty view model with no business method.
 * If you have just want to use the data binding but the view model, use this
 * view model as a generic type for your activity.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 */
public class EmptyViewModel extends BaseViewModel {

    public EmptyViewModel(@NonNull Application application) {
        super(application);
    }
}
