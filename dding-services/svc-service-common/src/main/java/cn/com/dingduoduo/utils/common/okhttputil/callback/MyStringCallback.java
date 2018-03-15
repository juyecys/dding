package cn.com.dingduoduo.utils.common.okhttputil.callback;
/**
 * Created by li on 2016/12/7.
 */

import okhttp3.Call;
import okhttp3.Request;

public class MyStringCallback extends StringCallback
{
    @Override
    public void onBefore(Request request, int id)
    {
        System.out.println("loading...");
    }

    @Override
    public void onAfter(int id)
    {
        System.out.println("Sample-okHttp");
    }

    @Override
    public void onError(Call call, Exception e, int id)
    {
        e.printStackTrace();
        System.out.println("onError:" + e.getMessage());
    }

    @Override
    public void onResponse(String response, int id)
    {
        System.out.println("onResponse:" + response);

        switch (id)
        {
            case 100:
                System.out.println("http");
                break;
            case 101:
                System.out.println("https");
                break;
        }
    }

    @Override
    public void inProgress(float progress, long total, int id)
    {
        System.out.println( "inProgress:" + progress);
    }
}