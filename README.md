![banner](/images/banner.jpg)
![Offical](/images/offical.png)

# MVVMs：Android MVVM 快速开发框架

<p align="center">
  <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="https://img.shields.io/hexpm/l/plug.svg" alt="License" />
  </a>
  <a href="https://bintray.com/beta/#/easymark/Android/mvvm-core?tab=overview">
    <img src="https://img.shields.io/maven-metadata/v/https/dl.bintray.com/easymark/Android/me/shouheng/mvvm/mvvm-core/maven-metadata.xml.svg" alt="Version" />
  </a>
  <a href="https://www.codacy.com/manual/Shouheng88/Android-MVVMs?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Shouheng88/Android-MVVMs&amp;utm_campaign=Badge_Grade">
    <img src="https://api.codacy.com/project/badge/Grade/412a91540f254721ac63757eeded9ba5" alt="Code Grade"/>
  </a>
  <a href="https://travis-ci.org/Shouheng88/Android-MVVMs">
    <img src="https://travis-ci.org/Shouheng88/Android-MVVMs.svg?branch=master" alt="Build"/>
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

该项目提供了基于 Android Jetpack 的 LiveData、ViewModel、Databinding 和 Lifecycle 的 Android MVVM 快速开发框架，用来帮助开发者快速开发 Android 应用。该项目通过注解和泛型降低了集成 Jetpack 的难度，对 ViewModel 和 View 层的数据交互格式进行了封装，同时提供了应用开发过程所必需的其他开发组件。

## 1、主要的功能和特性

- **基于注解对 Activity 和 Fragment 等进行配置**：相比于传统的赋值的方式，我们引入了注解解析，这样可以使各个功能类的配置信息一目了然。

- **对 ViewModel 进行了封装**：用户可以直接通过实现 ViewModel 的方法来感知 Activity 等的生命周期的调用，可以直接从我们提供的方法中读取传入到 View 层的 Bundle 信息，这样你就无需手动从 Bundle 中读取并赋值给 ViewModel 了。

- **规范了 ViewModel 和 View 层之间数据交互格式**：封装了 Resource 对象，并且提供了一系列的便捷操作的方法。

- **可以根据类型获取 LiveData 无需手动声明**：你可以通过我们封装的 ViewModel 的方法直接获取 LiveData 对象并进行监听，无需手动声明变量，这将使你的代码更加简洁。

- **提供了一个 Fragment 容器类**：一个支持自定义指令的 ContainerActivity 用来打开简单的 Fragment。

- **提供了 EventBus 封装**：不论你使用的是 EventBus 还是 AndroidEventBus，用我们封装的类和方法都可以轻松发送和接收消息，你要做的仅仅是引入对应的依赖而已。

- **上手快，学习成本低**：在该库和示例代码中，我们并没有提供 DataBinding 相关的逻辑。该库重点解决的是 ViewModel 和 View 层的交互问题。因此，你只需要知道 LiveData 是两者之间交互的桥梁即可，上手成本非常低！

- **提供了 Android 开发完整的解决方案**：该项目主要提供的是 MVVM 封装，此外，我们还提供了 Android 工具类，UI 类库组件化封装。这三个库都是开源并且单独维护，通过一系列标准化的类和方法来帮助开发者降低 Android 开发的难度。

## 2、在项目中引用

### 2.1 引入 jcenter

该库已经上传到了 jcenter 仓库。你需要在项目的 Gradle 中加入 jcenter 仓库：

```gradle
repositories {
    jcenter()
}
```

### 2.2 添加项目依赖

然后，在项目依赖中直接引用我们的库即可：

```gradle
implementation 'me.shouheng.mvvm:mvvm-core:latest-version'
```

### 2.3 对类库进行初始化

最后，在自定义 Application 中对 MVVMs 进行初始化。你只需要分别在 Application 的 `attachBaseContext()` 和 `onCreate()` 方法中调用 MVVMs 的对应方法即可。这样做只不过是为了我们在类库中方便调用全局的 Context，是不会给你的应用的性能带来额外的开销的。

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

## 3、各个功能模块的具体应用

### 3.1 项目依赖关系

首先，我们从项目的依赖上面来看一下，该库功能构成。

```groovy
compileOnly "org.greenrobot:eventbus:3.1.1"
compileOnly "org.simple:androideventbus:1.0.5.1"
compileOnly "com.umeng.umsdk:common:1.5.4"
compileOnly "com.umeng.umsdk:analytics:7.5.4"
compileOnly "me.shouheng.ui:uix-core:1.3.3"
api "me.shouheng.utils:utils-core:2.0"
api "me.shouheng.compressor:compressor:1.3.1"
```

也就是说，我们提供了对友盟、两种 EventBus 和 [AndroidUIX](https://github.com/Shouheng88/Android-uix) 组件库的支持。同时，提供了 [AndroidUtils](https://github.com/Shouheng88/Android-utils) 工具库和图片压缩工具库 [Compressor](https://github.com/Shouheng88/Compressor) 的实现。关于后面的两个库的使用，参考对应的文档即可，下文不再进行说明。

### 3.2 项目的组成结构

然后，我们再具体看下项目的包结构：

```
/--mvvm
    /--base     : 基础各个抽象的顶层类，自定义注解等
    /--bean     : ViewModel 和 View 的数据交互格式
    /--bus      : EventBus 的实现
    /--comn     : 一个空的 ViewModel 以及一个容器 Activity
    /--http     : 基于 okhttp 的下载工具类
    /--utils    : 项目内部使用的一些工具类
```

### 3.3 View ViewModel DataBindg 的整合示范

按照之前的配置方式对 MVVMs 配置之后就可以实现我们的业务代码了。首先，我们看下 View ViewModel DataBindg 如何整合到一起的。

```kotlin
// 基于注解对 Activity 进行配置
@ActivityConfiguration(
    // 使用 EventBus
    useEventBus = true,
    // 指定布局文件
    layoutResId = R.layout.activity_main,
    // 对状态栏配置
    statusBarConfiguration = StatusBarConfiguration(
        statusBarMode = StatusBarMode.DARK,
        statusBarColor = Color.BLACK
    ),
    // 对友盟进行配置
    umengConfiguration = UmengConfiguration(
        pageName = "MainActivity"
    )
)
class MainActivity : CommonActivity<ActivityMainBinding, MainViewModel>() {

    // 在这里实现自己的 ui 逻辑
    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.startLoad()
    }

    // 创建数据监听
    private fun addSubscriptions() {
        vm.getObservable(String::class.java).observe(this, Observer {
            // 根据数据状态进行业务处理
            when(it!!.status) {
                Status.SUCCESS -> {ToastUtils.showShort(it.data)}
                Status.FAILED -> {ToastUtils.showShort(it.errorMessage)}
                Status.LOADING -> {/*temp do nothing*/}
                else -> { /*do nothing*/ }
            }
        })
    }

    // 页面初始化
    private fun initViews() {
        val fragment = MainFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // 对 EvnetBus 抛出的事件进行处理
    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }
}

// ViewModel 的实现
class MainViewModel(application: Application) : BaseViewModel(application) {

    fun startLoad() {
        // 获取 LiveData 并通知数据给 ui 层
        getObservable(String::class.java).value = Resources.loading()
        ARouter.getInstance().navigation(MainDataService::class.java)
            ?.loadData(object : OnGetMainDataListener{
                override fun onGetData() {
                    // 通知结果数据给 ui 层
                    getObservable(String::class.java).value = Resources.loading()
                }
            })
    }
}
```

根据上述示例，我们可以作如下结论：

1. 我们通过注解来对 Activity 进行个性化配置，比如配置友盟、状态栏、布局和 EventBus 等。
2. 自定义 Activity 应该继承 CommonActivity 并且需要传入 DataBinding 和 ViewModel 两个实例。要得到 DataBinding，你将布局的根目录替换为 `<layout>` 标签即可。
3. 获取 LiveData 并对数据进行监听，根据数据的状态对业务逻辑进行处理。

### 3.4 从 ViewModel 中获取 LiveData

按照上述代码，我们 ViewModel 的实现看上去非常简洁，甚至没有声明任何全局变量就实现了跟 View 层的数据交换，这是怎么做到的呢？参考如下源代码：

```java
public class BaseViewModel extends AndroidViewModel {

    // 数据类型到 LiveData 的映射关系
    private Map<Class, MutableLiveData> liveDataMap = new HashMap<>();
    private Map<Class, SparseArray<MutableLiveData>> sparseIntArrayMap = new HashMap<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    // 这里获取 LiveData 的时候本质上是一个单例的操作
    public <T> MutableLiveData<Resources<T>> getObservable(Class<T> dataType) {
        MutableLiveData<Resources<T>> liveData = liveDataMap.get(dataType);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveDataMap.put(dataType, liveData);
        }
        return liveData;
    }

    public <T> MutableLiveData<Resources<T>> getObservable(Class<T> dataType, int flag) {
        SparseArray<MutableLiveData> array = sparseIntArrayMap.get(dataType);
        if (array == null) {
            array = new SparseArray<>();
            sparseIntArrayMap.put(dataType, array);
        }
        MutableLiveData<Resources<T>> liveData = array.get(flag);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            array.put(flag, liveData);
        }
        return liveData;
    }
}
```

这里的操作本质上是单例的运用，也就是从 Map 中获取指定类型对应的单例的 LiveData，获取不到就实例化一个并塞到 Map 中。

也许你已经注意到了这里是 Class 到 LiveData 的映射，如果我存在同一个 Class，而我希望它们映射到多个 LiveData 怎么办呢？比如，当我获取一个文章的标题和内容数据监听的时候，标题和内容都是 String 类型，我怎么知道发生变化的是文章的内容还是标题呢？为了解决这个问题，我们提供了 `getObservable(Class<T>)` 的重载方法 `getObservable(Class<T>, int)`。这里增加了一个 int 类型的 flag 用来作区分。比如，获取获取文章标题和内容监听的时候可以这么写：

```kotlin
// 声明两个 flag
val FLAG_ARTICLE_TITLE      = 0x0001
val FLAG_ARTICLE_CONTENT    = 0x0002

// 获取文章标题的数据监听
vm.getObservable(String::class.java, FLAG_ARTICLE_TITLE)....

// 获取文章内容的数据监听
vm.getObservable(String::class.java, FLAG_ARTICLE_CONTENT)....
```

## 4、关于

### 4.1 关于作者

你可以通过访问下面的链接来获取作者的信息：

1. Github: https://github.com/Shouheng88
2. 掘金：https://juejin.im/user/585555e11b69e6006c907a2a
3. CSDN：https://blog.csdn.net/github_35186068
4. 简书：https://www.jianshu.com/u/e1ad842673e2

### 4.2 关于项目

- [更新日志](CHANGELOG.md)

## 5、捐赠项目

我们致力于为广大开发者和个人开发者提供快速开发应用的解决方案。您可以通过下面的渠道来支持我们的项目，

<div style="display:flex;" id="target">
<img src="images/ali.jpg" width="25%" />
<img src="images/mm.png" style="margin-left:10px;" width="25%"/>
</div>

## License

```
Copyright (c) 2019-2020 CodeBrick.

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



