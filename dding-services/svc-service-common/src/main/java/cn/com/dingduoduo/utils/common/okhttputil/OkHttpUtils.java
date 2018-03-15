package cn.com.dingduoduo.utils.common.okhttputil;


import cn.com.dingduoduo.utils.common.okhttputil.builder.*;
import cn.com.dingduoduo.utils.common.okhttputil.callback.Callback;
import cn.com.dingduoduo.utils.common.okhttputil.callback.StringCallback;
import cn.com.dingduoduo.utils.common.okhttputil.cookie.CookieJarImpl;
import cn.com.dingduoduo.utils.common.okhttputil.cookie.store.MemoryCookieStore;
import cn.com.dingduoduo.utils.common.okhttputil.request.RequestCall;
import cn.com.dingduoduo.utils.common.okhttputil.utils.Platform;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils
{
    public static final long DEFAULT_MILLISECONDS = 10000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;

    public OkHttpUtils(OkHttpClient okHttpClient)
    {
        if (okHttpClient == null)
        {
            mOkHttpClient = new OkHttpClient();
        } else
        {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance()
    {
        return initClient(null);
    }


    public Executor getDelivery()
    {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }

    public static GetBuilder get()
    {
        return new GetBuilder();
    }

    public static PostStringBuilder postString()
    {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile()
    {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post()
    {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put()
    {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head()
    {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete()
    {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch()
    {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback)
    {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback()
        {
            public void onFailure(Call call, final IOException e)
            {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            public void onResponse(final Call call, final Response response)
            {
                try
                {
                    if (call.isCanceled())
                    {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id))
                    {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e)
                {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally
                {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id)
    {
        if (callback == null) return;

        mPlatform.execute(new Runnable()
        {
            public void run()
            {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id)
    {
        if (callback == null) return;
        mPlatform.execute(new Runnable()
        {
            public void run()
            {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }

    public static class METHOD
    {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
    
    public String fixUrl(String url,Object... args){
    	for(Object o :args){
    		
    		
    	}
    	
    	return null;
    }
    
    public static void loginCookie(){

        OkHttpClient okHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        CookieJarImpl cookieJarImpl = new CookieJarImpl(new MemoryCookieStore());
        builder.cookieJar(cookieJarImpl);
        okHttpClient = builder.build();
        initClient(okHttpClient);

    }


    public static void main(String args[]){

        String url = "http://so.3dmgame.com";

        OkHttpUtils
                .get()
                .url(url)
                .addParams("type","4")
                .addParams("keyword","龙")
                .addParams("auth","")
                .addParams("x","0")
                .addParams("y","0")
                .id(101)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println(response);
            }
        });

    }
    
}

