package javastudy.collection_.properties_;

import java.util.Properties;

/**
 * 属性映射的细节演示
 */
public class PropertiesTest02 {
    public static void main(String[] args) throws Exception {
        // TODO System.getProperties()获取所有系统属性，前提是应用有这个权限
        Properties systemProperties = System.getProperties();
        String userName = System.getProperty("user.name"); //获取指定值
        // 打印
        // for (Map.Entry<Object, Object> entry : systemProperties.entrySet()) {
        //     System.out.println(entry.getKey()+" : "+entry.getValue());
        // }

        Properties properties = new Properties();

        // TODO 添加元素和获取元素
        properties.put("state", "sleep"); //put方法也可以
        Object state = properties.get("state");
        // 由于历史遗留原因,Properties继承了HashTable<Object,Object>类，put()和get()都可以使用，但是put可放任意类型，get也只是得到Object
        // 类型，没有setProperty()这样的类型检查，所以最好不要使用

        //提供一个默认值
        String name = properties.getProperty("name", "");

        // TODO 二级映射，可以为每一个键值对提供默认值
        // 把所有默认值都放在一个二级属性映射中，并在主属性映射中提供这个二级映射
        properties.setProperty("name", "洒家");
        properties.setProperty("sex", "仙人");
        properties.setProperty("state", "鼎盛");

        Properties settings = new Properties(properties);
        settings.setProperty("name", "后黄花");

        // 使用迭代器只会遍历一个name，使用store()保存配置信息也不会保存二级映射中的信息
        // 但是可以使用getProperty()获取三个值
        System.out.println(settings.getProperty("sex")); //仙人
    }
}
