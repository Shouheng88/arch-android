package me.shouheng.eyepetizer.api;

import io.realm.annotations.RealmModule;
import me.shouheng.eyepetizer.api.bean.*;

/**
 * @Author wangshouheng
 * @Time 2022/10/4
 */
@RealmModule(library = true, classes = {
        HomeBean.class,
        Issue.class,
        Item.class,
        Data.class,
        PlayInfo.class,
        Url.class,
        Provider.class,
        WebUrl.class,
        Tag.class,
        Consumption.class,
        Cover.class,
        Author.class,
        Shield.class,
        Follow.class,
})
public class EyeRealmModule {
}
