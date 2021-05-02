<h1 align="center">Android VMLib: One Quick MVVM Library Based On Jetpack</h1> 

<p align="center"><a href="README-CHINESE.md">中文</p>

<p align="center">
  <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="https://img.shields.io/hexpm/l/plug.svg" alt="License" />
  </a>
  <a href="https://bintray.com/beta/#/easymark/Android/vmlib-core?tab=overview">
    <img src="https://img.shields.io/maven-metadata/v/https/s01.oss.sonatype.org/service/local/repo_groups/public/content/com/github/Shouheng88/vmlib/maven-metadata.xml.svg" alt="Version" />
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

This project is designed for fast development. The library is mainly based on Jetpack LiveData and ViewModel. At the same time, this project can support DataBinding as well as ViewBinding integration. Also, it provided over 20 kinds of utils classes, companied with features of kotlin, make it easier to use. Anyway, this project can accelerate your development and save a lot of code for you.

## 1. Use the library

### 1.1 Add jcenter first

The library is published to jcenter. So, you can use jcenter to introduce this library in your project:

```gradle
repositories { mavenCentral() }
```

### 1.2 Add dependecy

```gradle
implementation "com.github.Shouheng88:vmlib:$latest-version"
```

If you need the downloader library based on OkHttp, try add the dependency below:

```gradle
implementation "com.github.Shouheng88:vmlib-network:$latest-version"
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

This option is only used to get a global Context, so we could use it everywhere. And it won't take too much time.

### 1.4 Add proguard rules

```
# Add the rule below if you are using one of the EventBus
-keep class org.greenrobot.eventbus.EventBus {*;}
-keep class org.simple.eventbus.EventBus {*;}

# Add the rule if you want to use umeng
-keep class com.umeng.analytics.MobclickAgent {*;}

# Add the rule if you are using Android-UIX
-keep class me.shouheng.uix.common.UIX {*;}

# Add the rule and replace 'package' of your own App package if you are using ViewBinding
-keep class package.databinding.** {
    public static * inflate(android.view.LayoutInflater);
}
```

## 2. VMLib best practice

### 2.1 Embrace new interaction

Normaly, we need to initialize a LiveData instance everywhere if we want to use it in our project. This will lead to too many global variables in your ViewModel. By simply design, we could make it easy and clean. For example. if you want to request one page data from network, your viewmodel might be like this below,

```kotlin
// me.shouheng.eyepetizer.vm.EyepetizerViewModel#requestFirstPage
fun requestFirstPage() {
    // notify data loading state
    setLoading(HomeBean::class.java)
    // do netowrk request
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

The magic is that we use the class type to locate a LiveData instance. Whenever we call `setXXX` in view model or `observe` in view layer, we use the Class type to get a LiveData instance from the so called LiveData Pool. So, that means, the Class is the unique identifies of LiveData.

Except Class, we can also use a bool variable and a integer to locate the LiveData. 

### 2.2 Use DataBinding and Viewbinding

VMLib allows you use DataBinding, ViewBinding or none of them. When you want to use DataBinding, you need to make a little change on your gradle:

```gradle
dataBinding {
    enabled true
}
```

and let your activity extend the CommonActivity. 

For larger project, DataBinding might cause too much time for gradle building. So, for large project, you can use ViewBinding instead. At this time, you need to change the gradle as:

```gradle
viewBinding {
    enabled true
}
```

and let your activity extend ViewBindingActivity.

Of course, if you want to use neither of them. What you need to do is just let your activity extend BaseActivity to use power provided by VMlib.

For fragment, VMLib provided similer abstract classes.

When let your activity or fragment extend base classes of VMLib, you have to specify a ViewModel type. For some pages, they are so simple that we don't need to define a ViewModel for it. In this case, we can use the empty view model provied by VMLib, EmptyViewModel.

### 2.3 Utils and utils-ktx

Different from other libraries, utils classes of VMLib was added by dependency of another project, [Android-Utils](https://github.com/Shouheng88/Android-utils). Android-Utils provided 20+ utils classes, including io, resources, image, animation and runtime permission etc. This is why VMLib is pwoerful. The utils classes of this library can meet almost all requirements of your daily development. You can get more information about Android-Utils by reading its document.

Except utils classes, VMLib also provided a utils kotlin extension based on Android-Utils. If you want to use the ktx library, you need to add the below dependency on your project,

```gradle
implementation "me.shouheng.utils:utils-ktx:$latest-version"
```

By configing above, you can save a lot code. For example, if you want to get a drawable, tint it and then display it in ImageView. One line is enough:

```kotlin
iv.icon = drawableOf(R.drawable.ic_add_circle).tint(Color.WHITE)
```

If you want to request runtime permission in Activity, what you need to do is only writing one line below,

```kotlin
checkStoragePermission {  /* got permission */ }
```

As for avoding continous click, you only need one line, 

```kotlin
btnRateIntro.onDebouncedClick { /* do something */ }
```

All in all, by VMLib you can significantly lower the difficulty of development.

### 2.4 Get result responsively

In VMLib, we refined logic of getting result from another Activity. In VMLib, you don't need to override `onActivityResult` method any more. You can use the `onResult` method and a integer for request code to get result:

```kotlin
onResult(0) { code, data ->
    if (code == Activity.RESULT_OK) {
        val ret = data?.getStringExtra("__result")
        L.d("Got result: $ret")
    }
}
```

This is in fact implemented by dispatching result and calling callback methods in BaseActivity. The goodness is that you don't need to override `onActivityResult`.

Except calling `onResult` method, you can also use the `start` method we provided and a callback to get result:

```kotlin
start(intent, 0) { resultCode, data ->
    if (resultCode == Activity.RESULT_OK) {
        val ret = data?.getStringExtra("__result")
        toast("Got result: $ret")
    }
}
```

By top level Activity, this is reather easy to achive and it won't cost too much code.

### 2.5 EventBus

VMLib support EventBus. Generally, for EventBus, you have two choices of [EvnetBus](https://github.com/greenrobot/EventBus) and [AndroidEventBus](https://github.com/hehonghui/AndroidEventBus). To use EventBus, what you need to do is to use `@ActivityConfiguration` to decorate your activity and let `useEventBus` filed of it be true.

```kotlin
@ActivityConfiguration(useEventBus = true)
class DebugActivity : CommonActivity<MainViewModel, ActivityDebugBinding>() {
    // ...
}
```

Then, you need to use `@Subscribe` to subscribe:

```kotlin
@Subscribe
fun onGetMessage(simpleEvent: SimpleEvent) {
    // do something ...
}
```

At last, you can use method of Bus to send a global message anywhere.

VMLib can handle details of EventBus which make your code simpler.

### 2.6 Wrapper classes: Resources and Status

In VMLib, interaction between ViewModel and View is achived by transfering data wrappered by Resources and Status. The Status is an enum, which have three values, means 'Succeed', 'Failed' and 'Loading' seperatlly. The Resources contains a filed of Status, and a `data` filed which represent the data. Also the Resources provided 5 additional fileds whose name start with `udf`. You can use them to carry some extra information.

The wrapper is able to use not only just between view model and view layer but also other circumstances, for example, getting result from kotlin coroutines. The goodness is that you can use one class type transfer three states.

### 2.7 Container Activity

In order to use Fragment, we provided ContainerActivity, a container for Fragment. It's rather to use. For example, If we want to use SampleFragment in ContainerActivity, set some arguments, animation direction etc. The code below is enough for you:

```kotlin
ContainerActivity.open(SampleFragment::class.java)
    .put(SampleFragment.ARGS_KEY_TEXT, stringOf(R.string.sample_main_argument_to_fragment))
    .put(ContainerActivity.KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_BACK)
    .withDirection(ActivityDirection.ANIMATE_FORWARD)
    .launch(context!!)
```

ContainerActivity can get fragment instance from fragment class and then display it in container. The logic is located in ContainerActivity, what you need to do is just implement your owen Fragment logic.

ContainerActivity is rather suitable for cases Fragment is simple. In such cases, you don't need to define Activity yourself, the ContainerActivity is enouth.

### 2.8 Compressor

So far, Compressor is the last part for VMLib.

This is an advanced and easy to use image compress library for Android, [Compressor](https://github.com/Shouheng88/Compressor). It support not only async but also sync API. Also, it support multiple types of input and ouput, which can meet most of your requirements. Get more about it by its document.

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



