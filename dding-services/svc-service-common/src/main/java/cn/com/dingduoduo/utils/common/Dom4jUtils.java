package cn.com.dingduoduo.utils.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by jeysine on 2018/1/24.
 */
public class Dom4jUtils {


    public static HashMap<String, Object> readXml(InputStream inputStream) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element node = document.getRootElement();

        return listNodes(node);
    }

    /**
     * 遍历节点
     * @param node
     */
    private static HashMap<String, Object> listNodes(Element node) {
        HashMap<String, Object> dataMap = new HashMap<>();
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();
            dataMap.put(e.getName(), e.getText());
        }
        return dataMap;
    }

}
