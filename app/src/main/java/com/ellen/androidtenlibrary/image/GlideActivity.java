package com.ellen.androidtenlibrary.image;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ellen.androidtenlibrary.R;

/**
 * Glide用法 & 原理核心机制
 *
 * >>>源码解析:https://juejin.im/post/57df609767f3560056b03672
 *
 * ## 原理核心机制(组件生命周期监听 & 请求 & 缓存策略)
 *  1.用什么网络库进行加载成Bitmap呢？
 *
 *  2.传入的上下文有何区别？
 *    Glide可以传入以下上下文
 *      Activity
 *      FragmentActivity
 *      ApplicationContext
 *      Context
 *      Fragment
 *
 *  3.LruCache算法
 *
 *  4.Activity销毁如何及时回收图片资源
 *    其实是利用FragmentActivity中加入空Fragment对FragmentActivity的生命周期进行监听
 */
public class GlideActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        imageView = findViewById(R.id.iv);
        glide();
    }

    private void glide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)//加载之前显示的图片
                .override(200,200)//指定图片大小为宽200，高200
                .skipMemoryCache(true)//true->跳过内存缓存
                /**
                 * 以下指的是磁盘缓存
                 * DiskCacheStrategy.NONE： 表示不缓存任何内容。
                 * DiskCacheStrategy.DATA： 表示只缓存原始图片。
                 * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
                 * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
                 * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略
                 */
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                /**
                 * 以下对原有的Bitmap做处理
                 */
                .circleCrop()//圆形化裁剪图片
                .fitCenter()
                .centerCrop()
                .centerInside()

                .error(R.mipmap.ic_launcher)//加载错误时显示的图片
                .priority(Priority.HIGH)//指定优先级
                //.transform(...) -->对bitmap进行转化
        ;

        String imgUrl = "图片加载地址";
        Glide.with(this)//这里指定的上下文很重要，它关系到图片的生命周期
                /**
                 * asGif()-->加载gif
                 * asBitmap-->加载Bitmap
                 */
                .asBitmap()//以Bitmap方式加载
                .load(imgUrl)
                .apply(options)
                .into(imageView);

    }
}
