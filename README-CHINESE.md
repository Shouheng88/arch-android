![Banner](https://github.com/CostCost/Resources/blob/master/github/xbanner.jpg?raw=true)

<h1 align="center">Android VMLib: 基于 Jetpack 的 MVVM 框架</h1>

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
    <img src="https://img.shields.io/badge/Author-Shouheng-orange.svg?style=flat-square" alt="Author" />
  </a>
  <a target="_blank" href="https://shang.qq.com/wpa/qunwpa?idkey=2711a5fa2e3ecfbaae34bd2cf2c98a5b25dd7d5cc56a3928abee84ae7a984253">
    <img src="https://img.shields.io/badge/QQ%E7%BE%A4-1018235573-orange.svg?style=flat-square" alt="QQ Group" />
  </a>
</P>

该项目为敏捷开发而设计，基于 Jetpack 的 LiveData 和 ViewModel 设计了基于类型的响应式监听机制，对 Databinding 和 ViewBinding 提供了支持，同时提供了 20+ 工具类支持，搭配 Kotlin 特性，大大降低开发难度，能为你节省大量的代码。

## 1、在项目中引用

### 1.1 引入 jcenter

该库已经上传到了 jcenter 仓库。你需要在项目的 Gradle 中加入 jcenter 仓库：

```gradle
repositories { jcenter() }
```

### 1.2 添加项目依赖

然后，在项目依赖中直接引用我们的库即可：

```gradle
implementation "me.shouheng.vmlib:vmlib-core:$latest-version"
```

如果需要使用基于 OkHttp 的文件下载模块，可以引入下面的依赖：

```gradle
implementation "me.shouheng.vmlib:vmlib-network:$latest-version"
```

### 1.3 对类库进行初始化

最后，你只需要在 Application 的 `onCreate()` 方法中调用 VMLib 的对应方法即可。这样做是为了能够在类库中方便地使用全局的 Context，不会给你的应用性能带来额外的开销的。

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化 mvvms
        VMLib.onCreate(this)
        // 工具类库个性化定制...
    }
}
```

### 1.4 混淆规则

```
# 使用了以下两个 EventBus 之一的话，加入对应规则
-keep class org.greenrobot.eventbus.EventBus {*;}
-keep class org.simple.eventbus.EventBus {*;}

# 使用了友盟统计的话，加入以下规则
-keep class com.umeng.analytics.MobclickAgent {*;}

# 使用了 uix 的时候加入以下规则
-keep class me.shouheng.uix.common.UIX {*;}

# 如果使用了 ViewBinding 则需要添加以下规则，并将包名替换为自己应用的包名
-keep class 包名.databinding.** {
    public static * inflate(android.view.LayoutInflater);
}
```

## 2、VMLib 项目实践

### 2.1 拥抱新的交互方式

如果不对 LiveData 进行封装，那么每个数据都需要定义一个对应的 LiveData 实例。这样在 ViewModel 中会存在大量的全局变量。在本库中，我们对此做了优化。以下是一个使用异步操作从网络中请求数据的示例。在 ViewModel 中，我们只需要像下面这样调用：

```kotlin
// me.shouheng.eyepetizer.vm.EyepetizerViewModel#requestFirstPage
fun requestFirstPage() {
    // 通知 ui 数据加载状态
    setLoading(HomeBean::class.java)
    // 执行网络请求
    eyepetizerService.getFirstHomePage(null, object : OnGetHomeBeansListener {
        override fun onError(code: String, msg: String) {
            // 通知 ui 加载失败
            setFailed(HomeBean::class.java, code, msg)
        }

        override fun onGetHomeBean(homeBean: HomeBean) {
            // 通知 ui 加载成功
            setSuccess(HomeBean::class.java, homeBean)
        }
    })
}
```

在 ui 层，我们像下面这样对数据进行监听，

```kotlin
observe(HomeBean::class.java, {
    // 成功的回调 ...
}, { 
    // 失败时的回调 ...
}, { 
    // 加载中的回调 ...
})
```

按照上这样开发，将使得你的代码更加简洁明了。

这样的魔力在于，我们通过 Class 来唯一地定位一个 LiveData. 当在 ViewModel 中使用 `setXXX` 方法或者在 View 层使用 `observe` 方法的时候，我们会根据 Class 从 ViewModel 的 “LiveData 池” 中根据 Class 类型获取一个 LiveData 实例。也就是说，Class 类型是 LiveData 的唯一标识。

除了 Class 类型，我们还可以通过一个 Boolean 类型和一个 Interger 类型的字段辅助定位 LiveData. 这一点我们会在“注意事项”中说明。

### 2.2 使用 DataBinding 和 ViewBinding

VMLib 对 DataBinding、ViewBinding 或者两者都不使用的情况提供了支持。当你想要在项目中使用 DataBinding 的时候，你只需要通过在 Gradle 中做如下配置:

```gradle
dataBinding {
    enabled true
}
```

并让你的 Activity 继承 CommonActivity 即可。

对于较大的项目，DataBinding 会使用较多的时间在编译期间进行处理。因此，对于较大的项目，推荐使用 ViewBinding，即只通过 Binding 对象获取控件而不进行数据绑定。此时，你只需要在 Gradle 配置中启用 ViewBinding：

```gradle
viewBinding {
    enabled true
}
```

并让你的 Activity 继承 ViewBindingActivity 即可。

当然，如果你既不打算使用 DataBinding 也不打算使用 ViewBinding，你可以直接让 Activity 继承 BaseActivity 来利用 VMLib 提供的各种功能。

对于 Fragment，VMLib 提供了类似的、对应的抽象基类，可以根据自己的配置进行选择。

当你通过继承 VMLib 提供的 Activity 和 Fragment 的时候，都会要求你指定一个 ViewModel. 有些时候我们的页面非常简单，根本不值得为其单独定义一个 ViewModel. 此时，你就可以使用 VMLib 为你提供的、默认的 EmptyViewModel. 是不是很贴心呢？

### 2.3 工具类及其拓展类支持

跟其他的框架不同，VMLib 中内置的工具类是通过引用另一个项目来完成的，即 [Android-Utils](https://github.com/Shouheng88/Android-utils). 该库提供了 22 个独立的工具类，涉及从 IO、资源读取、图像处理、动画到运行时权限获取等各种功能。这是 VMLib 强大的原因之一，该库提供的工具方法可以你日常开发的绝大部分需求。你可以通过该项目的说明文件来了解该工具类库所提供的所有能力。

除了基础的工具类，VMLib 还提供了工具类基于 Kotlin 的拓展。如果你需要使用这个拓展库，需要在项目中添加如下依赖：

```gradle
implementation "me.shouheng.utils:utils-ktx:$latest-version"
```

通过以上配置，可以大大减少你的开发的代码量。比如获取 Drawable 着色之后并将其赋值给 ImageView 只需要下面一行代码就可以搞定：

```kotlin
iv.icon = drawableOf(R.drawable.ic_add_circle).tint(Color.WHITE)
```

而在 Activity 内请求存储权限的时候也只需要下面一行代码就可以完成：

```kotlin
checkStoragePermission {  /* 添加请求到权限之后的逻辑 */ }
```

而对于防止控件连续点击，只需要下面这样一行代码即可完成：

```kotlin
btnRateIntro.onDebouncedClick { /* do something */ }
```

诸如此来的例子还有很多。总之你可以通过 VMLib 大大降低自己开发的难度。

### 2.4 Result 的响应式设计

在 VMLib 中，我们对 Activity 获取结果的 onActivityResult 进行了调整。你可以使用与过去不同的方式来获取另一个 Activity 返回的结果，而无需覆写 onActivityResult 方法。你可以通过 `onResult` 方法并传入一个整型的 Request Code 作为参数来获取返回的结果：

```kotlin
onResult(0) { code, data ->
    if (code == Activity.RESULT_OK) {
        val ret = data?.getStringExtra("__result")
        L.d("Got result: $ret")
    }
}
```

这实际上就是在 BaseActivity 中根据传入的 RequestCode 做了分发和回调。不过，好处就是你无需再主动覆写 onActivityResult 了。

除了使用 `onResult` 进行监听，你还可以使用我们提供的 `start` 方法。请求启动 Activity 的同时指定对结果的处理逻辑：

```kotlin
start(intent, 0) { resultCode, data ->
    if (resultCode == Activity.RESULT_OK) {
        val ret = data?.getStringExtra("__result")
        toast("Got result: $ret")
    }
}
```

通过定义顶层的 Activity，这些逻辑实现起来非常简单，并不需要太多的代码量，可以放心使用。

### 2.5 EventBus 的应用

VMLib 对 EventBus 的应用也提供了支持。对于 EventBus，一般来说，你有 [EvnetBus](https://github.com/greenrobot/EventBus) 和 [AndroidEventBus](https://github.com/hehonghui/AndroidEventBus) 两个选择。不论你使用哪个 EventBus，在 VMLib 中都可以对其提供支持。VMLib 对两者提供了统一的实现。要应用 EventBus，你只需要在自己的 Activity 上面使用 `@ActivityConfiguration` 注解并指定 useEventBus 为 true 即可：

```kotlin
@ActivityConfiguration(useEventBus = true)
class DebugActivity : CommonActivity<MainViewModel, ActivityDebugBinding>() {
    // ...
}
```

然后，你只需要通过 `@Subscribe` 注解进行监听，

```kotlin
@Subscribe
fun onGetMessage(simpleEvent: SimpleEvent) {
    // do something ...
}
```

并使用 VMLib 提供的 Bus 类发送消息即可：

```kotlin
Bus.get().post(SimpleEvent("MSG#00001"))
```

VMLib 会根据你的代码环境中的配置处理各种细节。

### 2.6 交互类设计：Resources 和 Status

在 VMLib 中，ViewModel 和 View 层之间的交互本质上是通过包装类来包装两者之间传递的信息的。包装类由 Resources 和 Status 组成。其中 Status 是一个枚举，共三个字段，分别表示“成功”、“失败”和“加载中”状态。而 Resources，包含了 Status，一个泛型的数据 data 字段，外加 5 个以 udf 开头的预备字段用来添加一些额外的信息。

交互类是一个交互对象的封装，除了用在 ViewModel 和 View 层之间，你还可以将其用在其他场景中，比如从 Kotlin 协程中获取返回结果。这个包装类的好处就在于，它可以将逻辑的三种状态通过一个对象返回过来。

### 2.7 容器 Activity：ContainerActivity

为了在项目中应用 Fragment，我们提供了 ContainerActivity. 顾名思义是一个 Fragment 的容器 Activity. 它的使用非常简单。比如，我们希望在 ContainerActivity 中打开 SampleFragment 这个 Fragment. 并且为其指定一些输入参数，指定动画的方向，那么我们只需要按照下面这样的链式调用即可：

```kotlin
ContainerActivity.open(SampleFragment::class.java)
    .put(SampleFragment.ARGS_KEY_TEXT, stringOf(R.string.sample_main_argument_to_fragment))
    .put(ContainerActivity.KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_BACK)
    .withDirection(ActivityDirection.ANIMATE_FORWARD)
    .launch(context!!)
```

ContainerActivity 可以根据打开的 Fragment 类型获取 Fragment 实例并传入参数和展示。这些逻辑都包装到了 ContainerActivity 中，而你只需要实现自己的 Fragment 即可。

ContainerActivity 非常适用于简单的 Fragment，你无需为其单独定义一个 Activity，此来逻辑可以统统交给 ContainerActivity 来完成。

### 2.8 图片压缩：Compressor

目前来说，VMLib 的最后一块拼图。

这是一个非常好用的 Android 图片压缩框架，其地址是 [Compressor](https://github.com/Shouheng88/Compressor). 其不仅可以支持异步 API 也支持同步调用。此外，还支持多种类型的参数和输出，可以满足绝大部分应用场景。可以通过该项目的地址来了解更多。

## 3、关于

### 3.1 关于作者

你可以通过访问下面的链接来获取作者的信息：

1. Github: https://github.com/Shouheng88
2. 掘金：https://juejin.im/user/585555e11b69e6006c907a2a
3. CSDN：https://blog.csdn.net/github_35186068
4. 简书：https://www.jianshu.com/u/e1ad842673e2

### 3.2 关于项目

- [更新日志](CHANGELOG.md)
- [项目背景](BACKGROUND.md)

## 4、捐赠项目

您可以通过下面的渠道来支持我们的项目，

<div style="display:flex;" id="target">
<img src="images/ali.jpg" width="25%" />
<img src="images/mm.png" style="margin-left:10px;" width="25%"/>
</div>

## License

```
Copyright (c) 2019-2020 Shouheng Wang.

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



