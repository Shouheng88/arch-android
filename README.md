# MVVMs：Android Jetpack MVVM 基础框架

![SLicense](https://img.shields.io/hexpm/l/plug.svg)
![Version](https://img.shields.io/maven-metadata/v/https/dl.bintray.com/easymark/Android/me/shouheng/mvvm/mvvm-core/maven-metadata.xml.svg)

该项目主要基于 Android Jetpack 中的 LiveData, ViewModel, Databinding, Lifecycle 等进行封装，提供了一个干净的框架来实现 Android 应用的快速开发。

## 1、功能和特性

作为一个 MVVM 框架，该项目的亮点在于：

- 在该项目中，我们提出了基于注解对 Activity 和 Fragment 等进行配置的概念，你可以直接通过注解来将配置您的 Activity 来简化你的代码。

- 我们对 ViewModel 进行了封装，你可以直接通过实现 ViewModel 的方法来感知 Activity 等的生命周期的调用，从而在各个生命周期里实现数据逻辑处理。
- 在 ViewModel 和 View 层之间封装了 Resource 对象来规范两者之间数据交互的格式。
- 在 ViewModel 中提供了一个 Hashmap 用来根据类型获取 LiveData，这样你甚至不需要手动创建 LiveData 就可以直接获取数据并进行监听，这使得你的代码更加简洁。
- **最重要的，整个项目不仅包含 MVVM 框架的封装，它由三部分组成：MVVM 封装，工具类库收集和封装，常用的 UI 类库组件化封装。** 这三个库都是开源并且单独维护，目的就在于分别维护来对三个模块分别优化并丰富其内容。
- 作为一个 MVVM 库，它的上手速度快，学习成本极低。

## 2、在项目中引用

在项目中接入我们的库是非常简单的。首先，在项目的 Gradle 中加入 jcenter仓库：

```gradle
repositories {
    jcenter()
}
```

然后在你的项目依赖中直接引用我们的库即可：

```gradle
implementation 'me.shouheng.mvvm:mvvm-core:latest-version'
```

然后，你需要在项目的自定义 Application 中对该类库进行初始化。你需要在两个地方调用 MVVMs 的静态方法。你无需担心它们的耗时问题，因为这里只是用来初始化 MVVM 中的一个全局的 Context。

```kotlin
class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 初始化 mvvms
        MVVMs.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        // 初始化 mvvms
        MVVMs.onCreate(this)
        // 工具类库个性化定制...
    }
```

另外，在我们的框架中引用了我们的开源的工具库 [Android-utils](https://github.com/Shouheng88/Android-utils)，因此，如果你需要对该工具类库进行个性化的定制，你可以在 MVVMs 初始化之后进行配置。【具体内容可以参考该工具库的项目说明】

## 3、在项目中使用 MVVMs

在把该框架应用到您的项目之前还是先来了解一下，我们都为您准备了哪些功能。

首先该项目的依赖关系是这样的：

```gradle
dependencies {
    implementation rootProject.ext.support['appcompat-v7']
    implementation rootProject.ext.libraries['EventBus']
    implementation rootProject.ext.libraries['AndroidEventBus']

    implementation rootProject.ext.libraries['umeng-common']
    implementation rootProject.ext.libraries['umeng-analytics']

    implementation rootProject.ext.libraries['okhttp']
    api rootProject.ext.libraries['utils']
    api rootProject.ext.libraries['compressor']
    implementation rootProject.ext.libraries['lifecycle-extensions']
    annotationProcessor rootProject.ext.libraries['lifecycle-compiler']
}
```

也就是说，我们为您提供了

- EventBus 的集成方案
- 友盟统计分析的集成方案
- 基于 OkHttp 的文件下载方案
- 另外两个必选的：工具类库和图片压缩框架的方案
- Android Jetpack 的集成方案

下面是这些方案的使用说明。

### 3.1 Android Jetpack 整合说明

使用 MVVMs 去定义一个 Activity 是下面这个样子：

```kotlin
@ActivityConfiguration(useEventBus = false, layoutResId = R.layout.activity_main,
    statuBarMode = StatusBarMode.LIGHT, statusBarColor = 0xffffff)
class MainActivity : CommonActivity<ActivityMainBinding, MainViewModel>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.startLoad()
    }

    private fun addSubscriptions() {
        vm.getObservable(String::class.java).observe(this, Observer {
            when(it!!.status) {
                Status.SUCCESS -> {ToastUtils.showShort(it.data)}
                Status.FAILED -> {ToastUtils.showShort(it.errorMessage)}
                Status.LOADING -> {/*temp do nothing*/}
                else -> { /*do nothing*/ }
            }
        })
    }

    private fun initViews() {
        val fragment = MainFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }
}
```

你可以通过注解来指定当前 Activity 的布局，是否使用 EventBus，状态栏的信息等。然后你需要在 CommonActivity 中传入两个类型，一个是 DataBinding 一个是 ViewModel. 在 CommonActivity 内部我们可以通过注解和泛型来获取布局和 ViewModel 的信息。所以，这样配置了之后你就可以直接通过 binding 和 vm 来分别获取 DataBinding 和 ViewModel 了。对于 CommonActivity，我们也在其中定义了一些常用的工具类方法，比如 Toast 和权限请求等，来方便你进行调用。

虽然布局与该框架无关，但是我强烈建议您按照下面的这样的方式去书写

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        tools:context=".view.MainActivity">

    <FrameLayout
            android:id="@+id/fragment_container"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

</layout>
```

即通过 layout 标签进行定义，同时我强烈建议你不要把 ViewModel 注入到 Layout 当中。这可能是一个很好的创意，但是实践上会存在一些问题：

1. 首先是编译的问题，直接把数据注入到 xml 当中可能会带来非常多的让人不愉快的异常。
2. 其次，IDE 对于注入的检查功能没有那么强大，使用注入的方式可能会带来不运行时错误。
3. 最后，Databinding 的功能并没有想象中的那么强大，它只能对数据进行简单的处理，如果数据过于复杂则不得不将部分逻辑置于 java 代码中。这就导致了一部分逻辑位于 xml 中，一部分逻辑位于 java 中。

对于 Fragment，你也可以按照上面这样去通过注解的方式进行定义，具体可以参考示例代码。

### 3.2 介绍下 ViewModel

正如上面介绍的那样，我们的 ViewModel 为你提供了一些便捷的方法用来“感知” View 的生命周期：

```java
public class BaseViewModel extends AndroidViewModel {

    private Map<Class, MutableLiveData> liveDataMap = new HashMap<>();

    private Map<Class, MutableLiveData> listLiveDataMap = new HashMap<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public <T> MutableLiveData<Resources<T>> getObservable(Class<T> dataType) {
        MutableLiveData<Resources<T>> liveData = liveDataMap.get(dataType);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveDataMap.put(dataType, liveData);
        }
        return liveData;
    }

    public <T> MutableLiveData<Resources<List<T>>> getListObservable(Class<T> dataType) {
        MutableLiveData<Resources<List<T>>> liveData = listLiveDataMap.get(dataType);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            listLiveDataMap.put(dataType, liveData);
        }
        return liveData;
    }

    public void onCreate(Bundle extras, Bundle savedInstanceState) {
        // default no implementation
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        // default no implementation
    }

    public void onDestroy() {
        // default no implementation
    }
}
```

此外，我们通过定义了 liveDataMap 和 listLiveDataMap 两个哈希表来帮助你简化获取 LiveData 实例的过程。比如，当你希望得到一个 String 类型的 LiveData 的时候，你可以通过下面的方法进行获取

```java
vm.getObservable(String::class.java)
```

在 ViewModel 内部，你通过上述方法来获取 LiveData 进行赋值，在 View 层同样通过上述方法来获取 LivaData 进行监听，这样就实现了 View 层和 ViewModel 的“连接”。另外，对于 List 类型的数据和普通的类型的数据，我们需要区别对待。比如当你希望得到 List<String> 类型的 LiveData 的时候，你应该调用 `vm.getListObservable(String::class.java)` 而不是 `vm.getObservable(String::class.java)`。这是因为 List<String> 类型会在运行时被擦除为 List，是无法区分其泛型的，因此我们单独定义了一个哈希表。

### 3.3 EventBus 的使用

对于 EventBus，你可以使用 greenrobot 的或者 androideventbus，两者有细微的区别。在我们的框架中，你是无需关注这些区别的。你可以通过注解中的 useEventBus 属性来指定当前的 View 需要使用 EventBus。然后你就可以直接通过

```java
Bus.get().post()
```

来发送消息。并通过注解 `@Subscribe` 来对事件进行监听：

```java
    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        // ....
    }
```

### 3.4 工具类库和图片压缩

这两个库是与该框架一起“捆绑”的。这两个类库的地址分别是：

1. https://github.com/Shouheng88/Compressor
2. https://github.com/Shouheng88/Android-utils

图片压缩框架整合了两种图片压缩方式来供你自由选择。与其他的框架不同，工具类库我们单独从框架中剥离了出来，以在后期不断对其进行丰富和优化。你可以通过参考这两个项目的说明来了解如何使用。

## 4、更新日志

- 版本 1.1.0：
    - 增加了基本的类库

## 5、关于作者

你可以通过访问下面的链接来获取作者的信息：

1. Github: https://github.com/Shouheng88
2. 掘金：https://juejin.im/user/585555e11b69e6006c907a2a
3. CSDN：https://blog.csdn.net/github_35186068

## License

```
Copyright (c) 2019 WngShhng.

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



