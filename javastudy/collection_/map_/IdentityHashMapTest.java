package javastudy.collection_.map_;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Objects;

/**
 * 标识散列映射
 */
public class IdentityHashMapTest {
    public static void main(String[] args) {
        // TODO IdentityHashMap中键的散列值不是用键的hashCode()计算的，而是调用System.identityHashCode()计算的，这个方法是根据内存地
        //  址计算散列码是所使用的，并且在比较两个对象时，IdentityHashMap使用的是 == 而不是equals()，所以重写键的这两个方法也用不上啊
        //  在串行化时该类可用来追踪哪些对象已经遍历过
        IdentityHashMap<Test, Integer> hashMap = new IdentityHashMap<>(); //调用的是System.identityHashCode()方法
        // HashMap<Test,Integer> hashMap = new HashMap<>(); //会调用Test的hashCode()方法
        hashMap.put(new Test("帝君"),1);
        hashMap.put(new Test("帝君"),2);
        System.identityHashCode("123");
    }

    private static class Test{
        String name;

        public Test(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Test test = (Test) o;
            return Objects.equals(name, test.name);
        }

        @Override
        public int hashCode() {
            System.out.println("hashCode()被调用");
            return Objects.hash(name);
        }
    }
}
