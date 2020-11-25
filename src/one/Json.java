package one;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;


/**
 * @创建人: Zero
 * @创建时间: 2020/10/18
 * @描述
 */
public class Json {
    /**
     * 转化XML文件为Json对象
     *
     * @param node
     * @param json
     */
    public void iterateNodes(Element node, JSONObject json) {
        //获取当前元素的名称
        String nodeName = node.getName();
        //判断已遍历的JSON中是否已经有了该元素的名称
        if (json.containsKey(nodeName)) {
            //该元素在同级下有多个
            Object Object = json.get(nodeName);
            JSONArray array = null;
            if (Object instanceof JSONArray) {
                array = (JSONArray) Object;
            } else {
                array = new JSONArray();
                array.add(Object);
            }
            //获取该元素下所有子元素
            List<Element> listElement = node.elements();
            if (listElement.isEmpty()) {
                //该元素无子元素，获取元素的值
                String nodeValue = node.getTextTrim();
                array.add(nodeValue);
                json.put(nodeName, array);
                return;
            }
            //有子元素
            JSONObject newJson = new JSONObject();
            //遍历所有子元素
            for (Element e : listElement) {
                //递归
                iterateNodes(e, newJson);
            }
            array.add(newJson);
            json.put(nodeName, array);
            return;
        }
        List<Element> listElement = node.elements();
        if (listElement.isEmpty()) {
            //该元素无子元素，获取元素的值
            String nodeValue = node.getTextTrim();
            json.put(nodeName, nodeValue);
            return;
        }
        //有子节点，新建一个JSONObject来存储该节点下子节点的值
        JSONObject object = new JSONObject();
        //遍历所有一级子节点
        for (Element e : listElement) {
            //递归
            iterateNodes(e, object);
        }
        json.put(nodeName, object);
        return;
    }

    /**
     * 转换为json格式并保存文件
     *
     * @param jsonData
     * @param filePath
     * @return
     */
    public boolean createJsonFile(Object jsonData, String filePath) {
        String content = JSON.toJSONString(jsonData, true);
        // 标记文件生成是否成功
        boolean flag = true;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(filePath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(content);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @return
     */
    public String readJsonFile(String filePath) {
        String jsonStr = "";
        File file = new File(filePath);
        try {
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 遍历json
     *
     * @param object
     */
    public void readJson(Object object) {
        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                Object o = entry.getValue();
                if (o instanceof String) {
                    System.out.println("key:" + entry.getKey() + " ，value:" + entry.getValue());
                } else {
                    readJson(o);
                }
            }
        }
        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                readJson(jsonArray.get(i));
            }
        }
    }

    /**
     * 增加节点
     *
     * @param jsObject
     */
    public void add(JSONObject jsObject) {
        JSONArray jsonArray = jsObject
                .getJSONObject("breakfast_menu")
                .getJSONArray("food");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "milk");
        jsonObject.put("price", "$10");
        jsonObject.put("description", "sweet");
        jsonObject.put("calories", "300");
        jsonArray.add(jsonObject);
    }

    /**
     * 删除节点
     *
     * @param jsonObject
     */
    public void remove(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject
                .getJSONObject("breakfast_menu")
                .getJSONArray("food");
        jsonArray.remove(jsonArray.size() - 1);
    }


    public boolean readJson(JSONObject jsonObject, String key, Object value) {
        JSONArray jsonArray = jsonObject
                .getJSONObject("breakfast_menu")
                .getJSONArray("food");
        for (int i = 0; i <jsonArray.size() ; i++) {
            if (jsonArray.getJSONObject(i).get(key).equals(value)){
                return true;
            }
        }
        return false;
    }

}
