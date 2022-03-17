package javastudy.method_;

/**
 * compareTo()的设计
 */
public class CompareToDemo implements Comparable {

    @Override
    // TODO 如果存在一个能比较子类对象的通用算法， 就应该写在超类里面并且设置为final
    // public final int compareTo(Object o) {
    public int compareTo(Object o) {
        // 有时候可以先调用父类的比较，再调用子类的方法
        // if (!super.compare(o)) return false;

        // o为null时的处理
        if (o == null) throw new NullPointerException();
        // TODO 如果不同子类中的比较有不同的定义，那么不同类的对象之间的比较就设为非法的
        if (getClass() != o.getClass()) throw new ClassCastException();

        return 0;
    }
}
