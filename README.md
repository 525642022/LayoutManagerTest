 终于学会 implementation 依赖了 感觉好简单
 gradle导入：

1.项目 的build.gralde中添加

	allprojects{

		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

2.添加依赖
dependencies {
	       implementation 'com.github.525642022:LayoutManagerTest:1.0'
	}
 
 
 实现效果
 
 ![实现效果](https://github.com/525642022/LayoutManagerTest/blob/master/1.gif)
 
 
 实现博客：https://www.jianshu.com/p/deab739dd3eb
 
  ![实现效果](https://github.com/525642022/LayoutManagerTest/blob/master/3s.gif)
 
 
 实现博客：https://www.jianshu.com/p/88e240311142
 
  ![实现效果](https://github.com/525642022/LayoutManagerTest/blob/master/5s.gif)
 
 
 实现博客：https://www.jianshu.com/p/c85fab22906c

