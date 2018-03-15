package cn.com.dingduoduo.utils.common.okhttputil.builder;

import cn.com.dingduoduo.utils.common.okhttputil.request.GetRequest;
import cn.com.dingduoduo.utils.common.okhttputil.request.RequestCall;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by zhy on 15/12/14.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable
{
    @Override
    public RequestCall build()
    {
        if (params != null)
        {
            url = appendParams(url, params);
        }

        return new GetRequest(url, tag, params, headers,id).build();
    }

    protected String appendParams(String url, Map<String, String> params)
    {
        if (url == null || params == null || params.isEmpty())
        {
            return url;
        }
//        Uri.Builder builder = Uri.parse(url).buildUpon();
//        Set<String> keys = params.keySet();
//        Iterator<String> iterator = keys.iterator();
//        while (iterator.hasNext())
//        {
//            String key = iterator.next();
//            builder.appendQueryParameter(key, params.get(key));
//        }
        if(url.indexOf("?")<=0){
        	url += "?"+getParamsStr(params);
        }else{
        	url += "&"+getParamsStr(params);
        }
        return url;
    }


    public GetBuilder params(Map<String, String> params)
    {
        this.params = params;
        return this;
    }

    public GetBuilder addParams(String key, String val)
    {
        if (this.params == null)
        {
            params = new LinkedHashMap<String, String>();
        }
        params.put(key, val);
        return this;
    }
    
	/**
	 * 获取参数字符串
	 * @param params
	 * @return
	 */
	private static String getParamsStr(Map<String,String> params){
		StringBuffer sb=new StringBuffer();
		if(params==null)return "";
		if(params.size()<=0)return "";
		for(Entry<String,String> entry:params.entrySet()){
			String value=entry.getValue();
			if(StringUtils.isEmpty(value))value="";
			sb.append(entry.getKey()).append("=").append(value).append("&");
		}
		return StringUtils.removeEnd(sb.toString(), "&");
	}


}
