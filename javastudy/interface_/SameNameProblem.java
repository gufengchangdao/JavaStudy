package javastudy.interface_;

/**
 * 解决默认方法冲突
 */
// public class SameNameProblem extends Parent implements Inter01{
// TODO 继承了某个接口以后，如果超类里面实现了接口方法，子类便不用再强制实现
public class SameNameProblem implements Inter01, Inter02 {
    public static void main(String[] args) {
        SameNameProblem main = new SameNameProblem();
        main.move();
    }

    /*TODO 接口冲突
     * 如果一个类实现了两个接口，都有一个相同的同名和参数类型一样的方法且其中有一个是默认方法，那么该类中就要求覆盖这个方法来解决冲突(不管另一个方法是不是
     * 默认的)
     */
    @Override
    public void move() {
        // 使用另一个接口中默认的方法
        Inter01.super.move();

        System.out.println("实现的方法");
    }
}

interface Inter01 {
    // void move();
    default void move() {
        System.out.println("接口中的方法");
    }

}

interface Inter02 {
    void move();
}

/* TODO 类优先规则
 *  如果接口中提供了默认方法，而超类中又有同名和参数类型一样的方法，那么只会调用类中的方法而忽视接口的默认方法
 *  本着这个原则，千万不能用接口的默认方法去重写Object中的方法，因为写了也没用
 */
class Parent {
    public void move() {
        System.out.println("飞呀！");
    }
}
