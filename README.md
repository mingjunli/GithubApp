# GithubApp
A Github Client App with MVP architecture use Dagger2, RxJava, Retrofit, Okhttp

## 简介
1. 一个Github的Android移动平台客户端;
2. 使用MVP架构;
3. 充分利用优秀的第三方开源库, 体验快速搭建项目;
4. 引入Dagger2做依赖注入来解耦;
5. Rx响应式编程体验;
6. 使用[标准Github Api](https://developer.github.com/v3/);
7. 使用Retrofit+OkHttp作为RESTful客户端实现;
8. ...待补充

## 开发过程

会不定更新一些开发过程的blog, 例如MVP架构的搭建过程, Dagger2的注入讲解等.
目前已更新文档:

* [MVP架构实现的Github客户端(1-准备工作)](http://www.jianshu.com/p/13175ff425ee)
* [MVP架构实现的Github客户端(2-搭建项目框架)](http://www.jianshu.com/p/69a9bbca1ef3)
* [MVP架构实现的Github客户端(3-功能实现)](http://www.jianshu.com/p/a1d8add4fb29)
* [MVP架构实现的Github客户端(4-加入网络缓存)](http://www.jianshu.com/p/faa46bbe8a2e)
* 待续

## 功能

1. 每日Trending仓库列表, 按照语言分类
    * Java
    * Python
    * Objective-C
    * Swift
    * HTML
    * Shell
2. 主流分类的Top 30 Stars的仓库列表
    * Android
    * iOS
    * Python
    * Web
    * PHP
3. 仓库搜索
    * 关键字+语言 搜索
4. 个人账户相关
    * 使用Github账户认证登录
    * 个人信息获取并展示
    * 个人的仓库列表
    * 个人的Starred仓库列表
5. 仓库操作
    * Star一个仓库
    * unStar一个仓库

## 第三方库

* [Retrofit(包含OkHttp)](https://github.com/square/retrofit)
* [OkHttp logging interceptor](https://github.com/square/okhttp/wiki/Interceptors)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxBinding](https://github.com/JakeWharton/RxBinding)
* [ButterKnift](https://github.com/JakeWharton/butterknife)
* [Dagger2](https://github.com/google/dagger)
* [Logger](https://github.com/orhanobut/logger)
* [Glide](https://github.com/bumptech/glide)
* [glide-transformations](https://github.com/wasabeef/glide-transformations)
* [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* [BottomBar](https://github.com/roughike/BottomBar)
* [spots-dialog](https://github.com/d-max/spots-dialog)
* [RichText](https://github.com/zzhoujay/RichText)
* [material](https://github.com/rey5137/material)
* [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)
* [RecyclerView-FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
* [FloatingActionButton](https://github.com/Clans/FloatingActionButton)
* [AppIntro](https://github.com/PaoloRotolo/AppIntro)
* [AwesomeSplash](https://github.com/ViksaaSkool/AwesomeSplash)
* [AndroidViewAnimations](https://github.com/daimajia/AndroidViewAnimations)
* [PagerSlidingTabStrip](https://github.com/astuetz/PagerSlidingTabStrip)
* [FlycoLabelView](https://github.com/H07000223/FlycoLabelView)

> 功能会不断增加, 完善, 大家如果有兴趣也可以fork, 提PR, 或是提出功能需求等.
> Email:[anly.pear@gmail.com](anly.pear@gmail.com)或是[简信](http://www.jianshu.com/users/bc1dacc65fae)都可以

