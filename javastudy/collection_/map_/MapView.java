/*
 * 四组map遍历方式
 */
package javastudy.collection_.map_;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示Map视图的获取和操作
 */
public class MapView {
    public static void main(String[] args) {
        /* TODO 集合框架不认为映射本身是一个集合，但是可以得到映射的视图
            键集       keySet()
            值集合     values()
            键/值对集  entrySet()
            可以在通过视图删除映射中元素，但是不能通过视图向映射中添加元素
         */
        Map<String, String> map = new HashMap<>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");

        //TODO 普遍使用，二次取值
        System.out.println("通过Map.keySet遍历key和value：");
        for (String key : map.keySet()) {
            System.out.println("key= " + key + " and value= " + map.get(key));
        }

        //TODO 推荐，使用entrySet()返回一个set集
        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //TODO 遍历值集合
        System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
        for (String v : map.values()) {
            System.out.println("value= " + v);
        }

        // TODO 使用forEach()，传入一个函数式表达式，底层使用的还是entrySet()方法
        map.forEach((key, value) -> {
            System.out.println("key= " + key + ", value= " + value);
        });

    }
}
