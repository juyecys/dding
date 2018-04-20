package cn.com.dingduoduo.utils.common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by jeysine on 2018/4/19.
 */
public class XStreamUtil {

    private static XStream xStream;

    //JVM加载类时会执行这些静态的代码块，如果static代码块有多个，JVM将按照它们在类中出现的先后顺序依次执行它们，每个代码块只会被执行一次。
    static{
        xStream = new XStream(new DomDriver());
    }

    //xml转java对象
    public static Object xmlToBean(String xml){
        return xStream.fromXML(xml);
    }

    //java对象转xml
    public static String beanToXml(Object obj){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xStream.toXML(obj);
    }

    public static XStream getxStream() {
        return xStream;
    }
}