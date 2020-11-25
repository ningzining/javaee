package exOne;

import javax.swing.*;

/**
 * @创建人: Zero
 * @创建时间: 2020/10/23
 * @描述
 */
public class Main {
    public static void main(String[] args) {
        Operation operation=new Operation("src/exOne/test.xml");
        //遍历操作
        operation.ergodic();
        //添加节点
        operation.addNode("yewuqingdan","牛奶","100","24","中国");
        //删除节点
        Operation operation1Re=new Operation("src/exOne/testAdd.xml");
        operation1Re.removeLastNode();
        //修改节点
        Operation operationUp=new Operation("src/exOne/test.xml");
        operationUp.update(0,"jiage","30");
    }
}
