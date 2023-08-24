package me.shouheng.guokr.api;

import io.realm.annotations.RealmModule;
import me.shouheng.guokr.api.bean.*;

/**
 * @Author wangshouheng
 * @Time 2022/10/4
 */
@RealmModule(library = true, classes = {
        GuokrNews.class,
        Result.class,
        Subject.class,
        GuokrAuthor.class,
        Avatar.class,
        ImageInfo.class,
        Minisite.class,
        Authors.class,
        AvatarX.class,
})
public class GuokrRealmModule {
}
