package javastudy.genericity_.excrcise;

/**
 * 通配符捕获
 */
public class SymbolCatch {
    public static void main(String[] args) {
        Point<Integer> point = new Point<>(123, 81);
        Point.swapHelper(point);
    }
}

class Point<T> {
    private T x;
    private T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public static <P> void swap(Point<?> p) {
        Object x = p.getX(); // 获取的只是Object类型的
        swapHelper(p);
    }

    /**
     * swapHelper方法的参数T能够捕获通配符，它不知道通配符指示哪种类型，但是这是一个明确的类型，T会指示那个类型
     * 很多带有 ? 的泛型都用到了通配符捕获
     * 但是编译器必须能够保证通配符表示单个可以确定的类型，像ArrayList<Point<T>>无法捕获ArrayList<Point<?>>，因为，集合里可以放任意类型
     */
    public static <T> void swapHelper(Point<T> p) {
        T temp = p.getX(); //获取到真正类型
        p.setX(p.getY());
        p.setY(temp);
    }


    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }


}
