package com.mz.gallerybanner;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GalleryBannerView<String> galleryBannerView;
    private TextView tvIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIndex = findViewById(R.id.tv_index);

        galleryBannerView = findViewById(R.id.galleryBannerView);
        galleryBannerView.update(initData(), R.layout.item_banner, new MyAdapterCallback(), new GalleryBannerView.OnItemClickEvent<String>() {
            @Override
            public void onItemClick(View view, String o, int position) {
                Toast.makeText(MainActivity.this, String.format(getString(R.string.click_img), position), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryBannerView.startScroll();
            }
        });
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryBannerView.stopScroll();
            }
        });

        galleryBannerView.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvIndex.setText(String.format(getString(R.string.index), position));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        galleryBannerView.stopScroll();
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        list.add("http://pic.90sjimg.com/back_pic/00/00/52/00/d8055937f84f8fbd6597208fa56c755a.jpg");
        list.add("http://pic.90sjimg.com/back_pic/00/00/69/40/517f2945e9d3085411c263a88b236281.jpg");
        list.add("http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/01/38/d8d426b6d61e3a834147c625253a6898.jpg");
        return list;
    }

    class MyAdapterCallback implements GalleryBannerView.AdapterCallback<String> {

        @Override
        public void convert(View view, String s, int position) {
            ViewHolder viewHolder = new ViewHolder(view);
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.banner);
            Glide.with(view.getContext())
                    .load(s)
                    .apply(requestOptions)
                    .into(viewHolder.tvImg);
        }

        class ViewHolder {
            ImageView tvImg;

            ViewHolder(View view) {
                tvImg = view.findViewById(R.id.iv_img);
            }
        }
    }
}
