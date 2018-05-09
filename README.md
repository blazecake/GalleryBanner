# GalleryBanner
Banner左右滑动，两边带Gallery效果

------

手动滑动效果：

![手动滑动效果](https://github.com/blazecake/GalleryBanner/blob/master/img/result1.gif)

自动滑动效果：

![自动滑动效果](https://github.com/blazecake/GalleryBanner/blob/master/img/result2.gif)

点击Banner效果：

![点击Banner效果](https://github.com/blazecake/GalleryBanner/blob/master/img/result3.gif)

------


下载体验：
[点击下载apk](https://www.pgyer.com/Zhzi)

扫一扫下载：

![扫一扫下载](https://github.com/blazecake/GalleryBanner/blob/master/img/apk.png)


------

代码使用：
1、 布局文件
	

```
<com.mz.gallerybanner.GalleryBannerView
        android:id="@+id/galleryBannerView"
        android:layout_width="match_parent"
        android:layout_height="160dp"
        android:layout_gravity="center"
        android:clipToPadding="false"
        android:paddingLeft="30dp"
        android:paddingRight="30dp"/>
```

2、代码设置

```
        galleryBannerView = findViewById(R.id.galleryBannerView);
        galleryBannerView.update(initData(), R.layout.item_banner, new MyAdapterCallback(), new GalleryBannerView.OnItemClickEvent<String>() {
            @Override
            public void onItemClick(View view, String o, int position) {
                Toast.makeText(MainActivity.this, String.format(getString(R.string.click_img), position), Toast.LENGTH_SHORT).show();
            }
        });
```

------

项目中添加：
root build.gradle
```
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
module build.gradle
```
	dependencies {
	        implementation 'com.github.blazecake:GalleryBanner:v1.0.0beta'
	}

```


