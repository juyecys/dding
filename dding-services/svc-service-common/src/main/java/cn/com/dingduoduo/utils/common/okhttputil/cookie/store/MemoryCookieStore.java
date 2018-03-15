package cn.com.dingduoduo.utils.common.okhttputil.cookie.store;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.*;

/**
 * Created by zhy on 16/3/10.
 */
public class MemoryCookieStore implements CookieStore
{
    private final HashMap<String, List<Cookie>> allCookies = new HashMap<String, List<Cookie>>();

    public void add(HttpUrl url, List<Cookie> cookies)
    {
        List<Cookie> oldCookies = allCookies.get(url.host());

        if (oldCookies != null)
        {
            Iterator<Cookie> itNew = cookies.iterator();
            Iterator<Cookie> itOld = oldCookies.iterator();
            while (itNew.hasNext())
            {
                String va = itNew.next().name();
                while (va != null && itOld.hasNext())
                {
                    String v = itOld.next().name();
                    if (v != null && va.equals(v))
                    {
                        itOld.remove();
                    }
                }
            }
            oldCookies.addAll(cookies);
        } else
        {
            allCookies.put(url.host(), cookies);
        }


    }

    public List<Cookie> get(HttpUrl uri)
    {
        List<Cookie> cookies = allCookies.get(uri.host());
        if (cookies == null)
        {
            cookies = new ArrayList<Cookie>();
            allCookies.put(uri.host(), cookies);
        }
        return cookies;

    }

    public boolean removeAll()
    {
        allCookies.clear();
        return true;
    }

    public List<Cookie> getCookies()
    {
        List<Cookie> cookies = new ArrayList<Cookie>();
        Set<String> httpUrls = allCookies.keySet();
        for (String url : httpUrls)
        {
            cookies.addAll(allCookies.get(url));
        }
        return cookies;
    }

    public boolean remove(HttpUrl uri, Cookie cookie)
    {
        List<Cookie> cookies = allCookies.get(uri.host());
        if (cookie != null)
        {
            return cookies.remove(cookie);
        }
        return false;
    }


}
