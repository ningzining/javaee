package one;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * @创建人: Zero
 * @创建时间: 2020/10/18
 * @描述
 */
public class Xml {
    SAXReader sax;
    File xmlFile;
    Document document;//获取document对象,如果文档无节点，则会抛出Exception提前结束
    Element root;//根节点

    Xml(String str) {
        sax = new SAXReader();//创建一个SAXReader对象
        xmlFile = new File(str);//根据指定的路径创建file对象
        try {
            document = sax.read(xmlFile);
            root = document.getRootElement();//获取根节点
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 对test.xml文件进行处理，实现遍历所有节点，并输出节点内容；
     */
    public void ergodic() {
        getNodes(root);//从根节点开始遍历所有节点
    }

    private void getNodes(Element node) {
        //当前节点的名称、文本内容和属性
        System.out.println(node.getName() + ": " + node.getTextTrim());//当前节点名称
        List<Attribute> listAttr = node.attributes();//当前节点的所有属性的list
        for (Attribute attr : listAttr) {//遍历当前节点的所有属性
            String name = attr.getName();//属性名称
            String value = attr.getValue();//属性的值
            System.out.println(name + "：" + value);
        }
        //递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();//所有一级子节点的list
        for (Element e : listElement) {//遍历所有一级子节点
            this.getNodes(e);//递归
        }
    }

    /**
     * 增加一个food节点，节点中包含相同的子节点；
     */
    public void addNode(String name,String price,String description,String calories) {
        Element root = document.getRootElement();//获取根节点
        Element food = root.addElement("food");
        food.addElement("name").setText(name);
        food.addElement("price").setText(price);
        food.addElement("description").setText(description);
        food.addElement("calories").setText(calories);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream("src/one/test.xml"), format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除增加的节点
     */
    public void removeNode() {
        Element root = document.getRootElement();//获取根节点
        List ecList = root.selectNodes("//food");
        Element food = (Element) root.elements("food").get(ecList.size() - 1);
        food.getParent().remove(food);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream("src/one/test.xml"), format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按节点的内容查询节点是否存在
     */
    public boolean quarry(String str) {
        int flag = 0;
        for (Iterator<Element> rootIter = root.elementIterator(); rootIter.hasNext(); ) {
            Element studentElt = rootIter.next();
            for (Iterator<Element> innerIter = studentElt.elementIterator(); innerIter.hasNext(); ) {
                Element innerElt = innerIter.next();
                String innerValue = innerElt.getStringValue();
                if (str.equals(innerValue)) {
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

}