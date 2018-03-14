package com.kang.net;

import com.yanzhenjie.permission.ApLog;
import com.kang.base.AppConst;
import com.kang.cache.UserCache;
import com.kang.utils.Md5Utils;
import com.kang.utils.NetUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * @author ：kangjinling
 * 邮箱 ：401205099@qq.com
 * 功能描述 ：网络请求 base
 *
 */

public abstract class BaseApiRetrofit<T> {

    private final OkHttpClient mClient;

    public OkHttpClient getClient() {
        return mClient;
    }
    public BaseApiRetrofit() {
        /*================== common ==================*/
        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //这里可以选择拦截级别
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        //cache
//        File httpCacheDir = new File(MyApp.getContext().getCacheDir(), "response");
//        int cacheSize = 10 * 1024 * 1024;// 10 MiB
//        Cache cache = new Cache(httpCacheDir, cacheSize);
        //OkHttpClient
        mClient =  new OkHttpClient.Builder()
                .addInterceptor(REWRITE_HEADER_CONTROL_INTERCEPTOR)
//                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addNetworkInterceptor(new LoggingInterceptor())
                .connectTimeout(60,TimeUnit.SECONDS)// 请求超时10秒
                .readTimeout(60, TimeUnit.SECONDS).
                 writeTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(loggingInterceptor)//设置 Debug Log 模式
//                .cache(cache)
//                .cookieJar(cookieJar)
                .build();

    }


    //header配置
    Interceptor REWRITE_HEADER_CONTROL_INTERCEPTOR = chain -> {

        ApLog.e("app_token========"+new StringBuffer(NetUtils.getImei()).reverse());
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("app_token", NetUtils.getImei())
                .addHeader("app_imei", NetUtils.getImei())
                .addHeader("unique_token", UserCache.getToken())
                .addHeader("sign", Md5Utils.UpperCase(NetUtils.getImei()+ AppConst.HEADTOKEN))
                .build();

        return chain.proceed(request);
    };

    //cache配置
//    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
//
//        //通过 CacheControl 控制缓存数据
//        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//        cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
//        cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
//        CacheControl cacheControl = cacheBuilder.build();
//
//        //设置拦截器
//        Request request = chain.request();
//        if (!NetUtils.isConnectedAndToast( BaseApp.getContext())) {
//            request = request.newBuilder()
//                    .cacheControl(cacheControl)
//                    .build();
//        }
//        Response originalResponse = chain.proceed(request);
//        if (NetUtils.isConnectedAndToast(BaseApp.getContext())) {
//            int maxAge = 0;//read from cache
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public ,max-age=" + maxAge)
//                    .build();
//        } else {
//            int maxStale = 60 * 60 * 24 * 28;//tolerate 4-weeks stale
//            return originalResponse.newBuilder()
//                    .removeHeader("Prama")
//                    .header("Cache-Control", "poublic, only-if-cached, max-stale=" + maxStale)
//                    .build();
//        }
//    };

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
//            UIUtils.showToast(request.headers()+"" );
            long t1 = System.nanoTime();//请求发起的时间
            ApLog.e(String.format("发送请求 %s on %s%n%s",
                    request.url(),
                    chain.connection(),
                    request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            ApLog.e(String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));

            return response;
        }
    }

}
