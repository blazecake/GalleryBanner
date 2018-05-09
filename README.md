# GalleryBanner
Banner左右滑动，两边带Gallery效果

------

手动滑动效果：
![这里写图片描述](https://img-blog.csdn.net/20180509181534688?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RvbmdndWFuZ2h1aXlpbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

自动滑动效果：
![这里写图片描述](https://img-blog.csdn.net/20180509181602218?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RvbmdndWFuZ2h1aXlpbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

点击Banner效果：
![这里写图片描述](https://img-blog.csdn.net/20180509181625469?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RvbmdndWFuZ2h1aXlpbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

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


