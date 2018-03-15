package cn.com.dingduoduo.utils.common.okhttputil.test;

import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.utils.common.okhttputil.callback.FileCallBack;
import cn.com.dingduoduo.utils.common.okhttputil.callback.GenericsCallback;
import cn.com.dingduoduo.utils.common.okhttputil.callback.StringCallback;
import cn.com.dingduoduo.utils.common.okhttputil.cookie.CookieJarImpl;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.Request;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity 
{

    private String mBaseUrl = "http://192.168.31.242:8888/okHttpServer/";

    private static final String TAG = "MainActivity";



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



    public void getHtml()
    {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }


    public void postString()
    {
        String url = mBaseUrl + "user!postString";
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(new Gson().toJson(new User("zhy", "123")))
                .build()
                .execute(new MyStringCallback());

    }

    public void postFile()
    {
        File file = new File("messenger_01.png");
        if (!file.exists())
        {
        	System.out.println("文件不存在，请修改文件路径");
            return;
        }
        String url = mBaseUrl + "user!postFile";
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new MyStringCallback());


    }

    public void getUser()
    {
        String url = mBaseUrl + "user!getUser";
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


    public void getUsers()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "zhy");
        String url = mBaseUrl + "user!getUsers";
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


    public void getHttpsHtml()
    {
        String url = "https://kyfw.12306.cn/otn/";

//                "https://12
//        url =3.125.219.144:8443/mobileConnect/MobileConnect/authLogin.action?systemid=100009&mobile=13260284063&pipe=2&reqtime=1422986580048&ispin=2";
        OkHttpUtils
                .get()//
                .url(url)//
                .id(101)
                .build()//
                .execute(new MyStringCallback());

    }

    public void uploadFile()
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

        String url = mBaseUrl + "user!uploadFile";

        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }


    public void multiFileUpload()
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

        String url = mBaseUrl + "user!uploadFile";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }


    public void downloadFile()
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

    public void clearSession()
    {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl)
        {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
    }

}
