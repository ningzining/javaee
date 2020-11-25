package one;
/**
 * @创建人: Zero
 * @创建时间: 2020/10/18
 * @描述
 */

public class Main {
    public static void main(String[] args) {
        Xml xml=new Xml("src/one/test.xml");
        //遍历xml文件
        xml.ergodic();
        //增加一个food节点
        xml.addNode("milk","100","sweet","500");
        //移除刚添加的节点
        xml.removeNode();
        //查询是否存在这个内容
        if (xml.quarry("French Toast")){
            System.out.println("查找成功");
        }else{
            System.out.println("查找失败");
        }
    }
}