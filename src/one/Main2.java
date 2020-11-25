package one;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;

/**
 * @创建人: Zero
 * @创建时间: 2020/10/18
 * @描述
 */
public class Main2 {
    public static void main(String[] args) {
        Json json=new Json();
//      创建json文件
        SAXReader sax = new SAXReader();//创建一个SAXReader对象
        File xmlFile = new File("src/one/test.xml");//根据指定的路径创建file对象
        Document document =null;
        Element root=null;
        try {
            document=sax.read(xmlFile);
            root = document.getRootElement();//获取根节点
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //遍历
        String path="src/one/json.json";
        String str=json.readJsonFile(path);
        JSONObject jsonObject=JSONObject.parseObject(str);
        json.readJson(jsonObject);

        //添加一个节点
        json.add(jsonObject);
        json.createJsonFile(jsonObject, "src/one/json.json");

        //删除最后一个节点
        json.remove(jsonObject);
        json.createJsonFile(jsonObject, "src/one/json.json");

        //查询
        if (json.readJson(jsonObject,"name","Homestyle Breakfast")){
            System.out.println("Yes");
        }else{
            System.out.println("No");
        }
    }
}
