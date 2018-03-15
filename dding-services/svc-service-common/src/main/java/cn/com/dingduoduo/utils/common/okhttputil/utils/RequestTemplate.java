package cn.com.dingduoduo.utils.common.okhttputil.utils;

import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.utils.common.okhttputil.callback.*;
import cn.com.dingduoduo.utils.common.okhttputil.callback.Callback;
import cn.com.dingduoduo.utils.common.okhttputil.cookie.CookieJarImpl;
import cn.com.dingduoduo.utils.common.okhttputil.test.JsonGenericsSerializator;
import cn.com.dingduoduo.utils.common.okhttputil.test.ListUserCallback;
import cn.com.dingduoduo.utils.common.okhttputil.test.User;
import cn.com.dingduoduo.utils.common.okhttputil.type.TypeList;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by list on 2016/12/7. 请求模板类,提供一些常用的应用接口.
 */
public class RequestTemplate {

    public static void main(String args[])throws Exception{
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        getHtmlSync(url);
    }

    /**
     * 提供统一的响应出口
     * @param response
     * @return
     */
    public static String getResponseString(Response response) throws IOException{
        String result = null;
        if(response.isSuccessful()){
            result = response.body().string();
        }else{

        }
        response.close();
        return result;
    }

    public static String getHtmlSync(String url)throws Exception
    {
    	return getHtmlSync(url, null);
    }
    
    public static String getHtmlSync(String url,Map<String,String> paramMap)throws Exception
    {
        Response response =null;
    	if(paramMap!=null){
    		response	= OkHttpUtils.get().url(url).params(paramMap).build().execute();
    	}else{
    		response = OkHttpUtils.get().url(url).build().execute();
    	}
        return getResponseString(response);
    }

    public static void getHtmlAsync(String url)throws Exception{
        OkHttpUtils.get().url(url).id(100).build().execute(new MyStringCallback());
    }

    public static void getHtmlAsync(String url, Callback<?> callback)throws Exception
    {
        OkHttpUtils.get().url(url).id(100).build().execute(callback);
    }
    
    public static void getHtmlAsync(String url,Map<String,String> paramMap)throws Exception
    {
    	getHtmlAsync(url, paramMap, new MyStringCallback());
    }
    
    public static void getHtmlAsync(String url, Map<String,String> map,Callback<?> callback)throws Exception
    {
    	if(map!=null){
            OkHttpUtils.get().url(url).params(map).id(100).build().execute(callback);
    	}else{
            OkHttpUtils.get().url(url).id(100).build().execute(callback);
    	}
    }

    public static <T> void postStringSync(String url,String content)
    {
        OkHttpUtils.postString().url(url).mediaType(TypeList.JSON.getMediaType()).content(content).build().execute(new MyStringCallback());
    }

    public static void postStringAsync(String url,String content)
    {
        postStringAsync(url,content,new MyStringCallback());
    }

    public static <T> void postStringAsync(String url,String content,Callback<?> callback)
    {
        OkHttpUtils.postString().url(url).mediaType(TypeList.JSON.getMediaType()).content(content).build().execute(callback);
    }

    public static <T> void postString(String url,T entity)
    {
        postString(url,new Gson().toJson(entity));
    }

    public static String postFileSync(String url,File file)throws IOException
    {
        if (!file.exists())
        {
            System.out.println("文件不存在，请修改文件路径");
            return null;
        }
        Response response = OkHttpUtils.postFile().url(url).file(file).build().execute();
        return getResponseString(response);
    }


    public static void postFileAsync(String url,File file)
    {
        postFileAsync(url,file,new MyStringCallback());
    }

    public static void postFileAsync(String url,File file,Callback callback)
    {
        if (!file.exists())
        {
            System.out.println("文件不存在，请修改文件路径");
            return;
        }
        OkHttpUtils.postFile().url(url).file(file).build().execute(callback);
    }

    public static void getUser()
    {
        String url = "user!getUser";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("username", "hyman")//
                .addParams("password", "123")//
                .build()//
                .execute(new GenericsCallback<User>(new JsonGenericsSerializator())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onResponse(User response, int id)
                    {
                        System.out.println(response.username);
                    }
                });
    }


    public static void getUsers()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "zhy");
        String url = "user!getUsers";
        OkHttpUtils//
                .post()//
                .url(url)//
//                .params(params)//
                .build()//
                .execute(new ListUserCallback()//
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                    }

                    @Override
                    public void onResponse(List<User> response, int id)
                    {
                    }
                });
    }

    public static String getHttpsHtmlSync(String url)throws IOException
    {
        //String url = "https://kyfw.12306.cn/otn/";
    	return getHttpsHtmlSync(url, null);
    }
    
    public static String getHttpsHtmlSync(String url,Map<String,String> paramMap)throws IOException
    {
        //String url = "https://kyfw.12306.cn/otn/";
    	Response response = null;
    	if(paramMap==null){
    		response = OkHttpUtils.get().url(url).id(101).build().execute();
    	}else{
    		response = OkHttpUtils.get().url(url).params(paramMap).build().execute();
    	}
        return getResponseString(response);
    }
    
    public static void getHttpsHtmlAsync(String url)
    {
    	getHttpsHtmlAsync(url,null, new MyStringCallback());
    }
    
    public static void getHttpsHtmlAsync(String url,Callback callback)
    {
    	getHttpsHtmlAsync(url,null, callback);
    }

    public static void getHttpsHtmlAsync(String url,Map<String,String> paramMap)
    {
    	getHttpsHtmlAsync(url, paramMap, new MyStringCallback());
    }

    public static void getHttpsHtmlAsync(String url,Map<String,String> paramMap,Callback callback)
    {
    	if(paramMap==null){
            OkHttpUtils.get().url(url).id(101).build().execute(callback);
    	}else{
            OkHttpUtils.get().url(url).params(paramMap).id(101).build().execute(callback);
    	}
    }

    public static void uploadFile()
    {

        File file = new File("messenger_01.png");
        if (!file.exists())
        {
            System.out.println("文件不存在，请修改文件路径");
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        String url = "user!uploadFile";

        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }


    public static void multiFileUpload()
    {
        File file = new File("messenger_01.png");
        File file2 = new File("test1#.txt");
        if (!file.exists())
        {
            System.out.println("文件不存在，请修改文件路径");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        String url = "user!uploadFile";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }


    public static void downloadFile()
    {
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/okhttputils-2_4_1.jar?raw=true";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack("c://", "gson-2.2.1.jar")//
                {

                    @Override
                    public void onBefore(Request request, int id)
                    {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        System.out.println("inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        System.out.println("onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id)
                    {
                        System.out.println("onResponse :" + file.getAbsolutePath());
                    }
                });
    }

    public static void clearSession()
    {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl)
        {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
    }



}
