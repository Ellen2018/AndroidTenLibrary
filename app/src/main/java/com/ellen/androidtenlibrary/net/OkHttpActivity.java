package com.ellen.androidtenlibrary.net;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ellen.androidtenlibrary.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp使用 & 核心原理机制
 *
 * >>>原理详情:https://juejin.im/entry/57c1119fc4c97100618112ff
 * >>>原理图查看res下OkHttp原理图
 *
 * ## 使用:
 *
 * OkHttpClient
 * Request
 * Call
 *
 * ## 核心原理机制:
 *
 * 1.同步 & 异步(execute & enqueue)
 *
 *   同步请求时，当前线程处于阻塞状态，也就是说它无法放在主线程进行请求
 *
 *   异步请求时，采用回调Callback的方式,其实内部是使用线程池开启线程进行请求，因此Callback的调用也会发生在子线程中
 *
 * 2.Dispatch进行分发(其目的是分发到对应的同步队列 & 异步队列)
 *
 *     如何分发？
 *     同步:runningSyncCalls,所有的同步请求RealCall都会放入这个队列
 *
 *     异步:被二次封装为AsyncCall，然后添加到readyAsyncCalls队列，当某个Call进行请求时，它就会被加入runningAsyncCalls。
 *
 *
 * 3.拦截器链调用(
 *     处理重试,重定向[RetryAndFollowUpInterceptor],
 *     Request(用户->网络请求),Response(网络请求->用户)[BridgeInterceptor],
 *     缓存[CacheInterceptor],
 *     链接池[ConnectInterceptor],
 *     请求[CallServerInterceptor]
 * )
 *
 * 具体拦截器的代码可查看 RealCall #getResponseWithInterceptorChain方法
 *
 * 下面我们来看看5中内部拦截器 & 应用拦截器 & 网络拦截器
 *
 * 执行顺序:
 * 应用拦截器(0个或者多个)
 * RetryAndFollowUpInterceptor
 * BridgeInterceptor
 * CacheInterceptor
 * ConnectInterceptor
 * 网络拦截器(0个或者多个)
 * CallServerInterceptor
 *
 * 对于每个拦截器的作用解释
 *
 *
 * ## 高级用法
 *
 * Socket通信(https://www.cnblogs.com/WUXIAOCHANG/p/11004119.html)
 * WebSocket(https://blog.csdn.net/fomin_zhu/article/details/85990363)
 * 断点续传(https://blog.csdn.net/cfy137000/article/details/54838608)
 * 文件上传 & 下载(https://www.cnblogs.com/whoislcj/p/5529827.html)
 *
 */
public class OkHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        return null;
                    }
                })//应用拦截器(运行于5种内部拦截器之前)
                .addNetworkInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        return null;
                    }
                })//网络拦截器(运行于4种内部拦截器,CallServerInterceptor之前)
                .build();
        Request request = new Request.Builder().build();
        Call call = okHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });

    }
}