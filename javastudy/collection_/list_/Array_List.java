package javastudy.collection_.list_;

/**
 * 模拟数组 + 链表结构
 * hashMap底层是数组 + 链表 + 红黑树
 */
public class Array_List {
    public static void main(String[] args) {

        //初始化数组
        Node[] table=new Node[16];

        //创建节点
        table[2]=new Node("XiaoBai",null);
        table[3]=new Node(true,null);

        //创建节点挂载到索引为2的节点上
        Node xiaoHei= new Node("XiaoHei",null);
        table[2].next=xiaoHei;
        xiaoHei.next=new Node("WO",null);   //挂载到xiaoHei的后面
    }
}

class Node{
    private Object item;
    public Node next;

    public Node(Object item, Node next) {
        this.item = item;
        this.next = next;
    }
}
