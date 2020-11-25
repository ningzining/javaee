package exOne;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @创建人: Zero
 * @创建时间: 2020/10/23
 * @描述
 */
public class Operation {
    SAXReader sax;
    File xmlFile;
    Document document;//获取document对象,如果文档无节点，则会抛出Exception提前结束
    Element root;//根节点

    Operation(String str) {
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
     * 遍历
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
            getNodes(e);//递归
        }
    }

    /**
     * 添加一个节点
     *
     * @param chanpin
     * @param jiage
     * @param shuliang
     * @param gongyings
     */
    public void addNode(String qingdan, String chanpin, String jiage, String shuliang, String gongyings) {
        Element yewuqingdan = root.addElement(qingdan);
        yewuqingdan.addElement("chanpin").setText(chanpin);
        yewuqingdan.addElement("jiage").setText(jiage);
        yewuqingdan.addElement("shuliang").setText(shuliang);
        yewuqingdan.addElement("gongyings").setText(gongyings);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream("src/exOne/testAdd.xml"), format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除最后一个节点
     */
    public void removeLastNode() {
        List ecList = root.selectNodes("//yewuqingdan");
        Element yewuqingdan = (Element) root.elements("yewuqingdan").get(ecList.size() - 1);
        yewuqingdan.getParent().remove(yewuqingdan);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream("src/exOne/testRe.xml"), format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int num, String strName,String update) {

        Element yewuqingdan = (Element) root.elements("yewuqingdan").get(num);
        Element str = (Element) yewuqingdan.element(strName);
        // 获取到miaoshu节点的父节点并删除miaoshu节点
        str.setText(update);

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream("src/exOne/testUp.xml"), format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
