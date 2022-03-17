## 错误情况

### 获取连接超时
我写了一个类，放有各种对数据库的操作，其中问题代码在
```java
class test{
    private Connection connection=null; //成员变量
    private static DataSource dataSource;
    //...
    public static ttt(){
        //....
        connection = dataSource.getConnection(); //这是很多方法中都有的获取连接
    }
}


```
错误原因：我在该类的一个方法中使用了另一个方法，另一个方法也会获取连接，把connection的值覆盖掉了，但是原来那个连接没有放回连接池，重复操作几次池子就满了
解决：把成员变量拿到方法里面，众多方法不要使用一个对象引用