<h1 align="center">Android VMLib: One Quick MVVM Library Based On Jetpack</h1>

<p align="center"><a href="README-CHINESE.md">中文</p>

<p align="center">
  <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="https://img.shields.io/hexpm/l/plug.svg" alt="License" />
  </a>
  <a href="https://bintray.com/beta/#/easymark/Android/vmlib-core?tab=overview">
    <img src="https://img.shields.io/maven-metadata/v/https/dl.bintray.com/easymark/Android/me/shouheng/vmlib/vmlib-core/maven-metadata.xml.svg" alt="Version" />
  </a>
  <a href="https://www.codacy.com/manual/Shouheng88/Android-MVVMs?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Shouheng88/Android-MVVMs&amp;utm_campaign=Badge_Grade">
    <img src="https://api.codacy.com/project/badge/Grade/412a91540f254721ac63757eeded9ba5" alt="Code Grade"/>
  </a>
  <a href="https://travis-ci.org/Shouheng88/Android-VMLib">
    <img src="https://travis-ci.org/Shouheng88/Android-VMLib.svg?branch=master" alt="Build"/>
  </a>
    <a href="https://developer.android.com/about/versions/android-4.2.html">
    <img src="https://img.shields.io/badge/API-17%2B-blue.svg?style=flat-square" alt="Min Sdk Version" />
  </a>
   <a href="https://github.com/Shouheng88">
    <img src="https://img.shields.io/badge/Author-CodeBrick-orange.svg?style=flat-square" alt="Author" />
  </a>
  <a target="_blank" href="https://shang.qq.com/wpa/qunwpa?idkey=2711a5fa2e3ecfbaae34bd2cf2c98a5b25dd7d5cc56a3928abee84ae7a984253">
    <img src="https://img.shields.io/badge/QQ%E7%BE%A4-1018235573-orange.svg?style=flat-square" alt="QQ Group" />
  </a>
</P>

The Jetpack itself is STRONG enough, however, if we could make full use of its feautres, we could make it stronger. This library put forward a new way to develop Android App by Jetpack, which will make it easier and simplier.

## 1. Use the library

### 1.1 Add jcenter first

```gradle
repositories { jcenter() }
```

### 1.2 Add dependecy

```gradle
implementation "me.shouheng.vmlib:vmlib-core:$latest-version"
```

If you need the downloader library based on OkHttp, try add the dependency below:

```gradle
implementation "me.shouheng.vmlib:vmlib-network:$latest-version"
```

### 1.3 Initialize the library

Call `VMLib.onCreate(this)` in your custom Application,

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        VMLib.onCreate(this)
    }
}
```

### 1.4 Add proguard rules

```
# Add the rule below if you are using one of the EventBus
-keep class org.greenrobot.eventbus.EventBus {*;}
-keep class org.simple.eventbus.EventBus {*;}
# Add the rule if you want to use umeng
-keep class com.umeng.analytics.MobclickAgent {*;}
# Add the rule if you are using Android-UIX
-keep class me.shouheng.uix.common.UIX {*;}
```

## 2. VMLib practice

### 2.1 Embrace the new way developing

In this library, we tried to hide the LivaData, so if you want to request one page data from network, your viewmodel might be like this,

```kotlin
// me.shouheng.eyepetizer.vm.EyepetizerViewModel#requestFirstPage
fun requestFirstPage() {
    // notify data loading
    setLoading(HomeBean::class.java)
    eyepetizerService.getFirstHomePage(null, object : OnGetHomeBeansListener {
        override fun onError(code: String, msg: String) {
            // notify load failed
            setFailed(HomeBean::class.java, code, msg)
        }

        override fun onGetHomeBean(homeBean: HomeBean) {
            // notify load succeed
            setSuccess(HomeBean::class.java, homeBean)
        }
    })
}
```

Then, in the activity, observe the data like below,

```kotlin
observe(HomeBean::class.java, {
    // on succeed
    val list = mutableListOf<Item>()
    it.data.issueList.forEach { issue ->
        issue.itemList.forEach { item ->
            if (item.data.cover != null
                && item.data.author != null
            ) list.add(item)
        }
    }
    adapter.addData(list)
}, { /* on failed */ loading = false }, { /* on loading */ })
```

This makes you logic simplier and more clean.

The magic is we used `Class+flag+Boolean` to generate and get a LiveData and then notify and listen its data. The `flag` was used when the Class type was same to distinguish the LiveData(s). The `Boolean` was used for one shot LiveData, which is used to avoid notified twice. Except the data type method like `observe()` we also provided `observeList()` to help the list data interaction.

Also, you can directly call `getLiveData()` from ViewModel and set value to it. Or, you can directly define LiveData by yourself. These are all feasible. The VMLib only provided one new way developing, it won't affect your old way.

### 2.2 The utils library

In this library, I added one utils library developed myself. See it at [Android-utils](https://github.com/Shouheng88/Android-utils). It provided many useful methods, witch can help you develop faster. Here, in VMLib, we only added the core library of Android-Utils, if you want to use its kotlin extensions, add the dependency to your project,

```gradle
implementation "me.shouheng.utils:utils-ktx:$latest-version"
```

## 3. About

- [Change Log](CHANGELOG.md)

## License

```
Copyright (c) 2019-2020 Jeff Wang.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



