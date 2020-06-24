package com.ellen.androidtenlibrary.net;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Retrofit用法 & 原理核心机制
 *
 * ## 使用
 *
 * 1.声明接口
 * 2.new 一个Retrofit对象
 * 3.create(接口.class)得到代理对象
 * 4.调用相应的方法进行网路请求
 *
 * ## 原理核心机制
 *
 * Retrofit的核心在于动态代理 & 结合各种设计模式 完成的架构
 *
 *
 */
public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
