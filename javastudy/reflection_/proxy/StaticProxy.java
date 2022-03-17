package javastudy.reflection_.proxy;

/*
TODO 静态代理
1. 优缺点：
    优点：
        (1) 实现简单，容易理解
    缺点：
        (2) 目标类增加后，代理类也会需要成倍增加，代理类数量过多
        (3) 接口中功能增加或修改后，会影响所有实现接口的代理类、目标类

2. 步骤：
    + 创建一个公共接口
    + 创建目标类和代理类，都实现接口，代理类中实现的方法会调用目标类中对应的方法并进行增强
    + 创建代理类对象并调用接口方法

3. 代理类完成的功能：
    (1) 目标类中方法的调用
    (2) 功能增强
 */

/**
 * 演示静态代理
 */
public class StaticProxy {
    public static void main(String[] args) {
        Shop main = new Shop(new Manufacturer());
        System.out.println(main.sell(1)); //通过商家(代理)买一个U盘
    }
}

//接口，存放目标方法
interface UsbSell {
    /**
     * 卖U盘功能
     *
     * @param mount 要购买的U盘数量
     * @return 总价格
     */
    float sell(int mount);
}

// 厂商 --- 目标类
class Manufacturer implements UsbSell {

    public float sell(int mount) {
        return mount * 85f; //原价
    }

}

// 商家 --- 代理类
class Shop implements UsbSell {
    UsbSell manufacturer;

    public Shop(UsbSell manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float sell(int mount) {
        // 代理类调用目标方法
        float price = manufacturer.sell(mount);
        // ---- 增强功能 ----
        System.out.println("商家给你购物优惠卷");
        price += 25;
        // -----------------
        return price;
    }

}
