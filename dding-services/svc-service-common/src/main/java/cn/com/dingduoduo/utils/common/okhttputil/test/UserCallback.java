package cn.com.dingduoduo.utils.common.okhttputil.test;

import cn.com.dingduoduo.utils.common.okhttputil.callback.Callback;
import com.google.gson.Gson;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class UserCallback extends Callback<User>
{
    @Override
    public User parseNetworkResponse(Response response, int id) throws IOException
    {
        String string = response.body().string();
        User user = new Gson().fromJson(string, User.class);
        return user;
    }


}
