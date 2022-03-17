package javastudy.collection_.set_;

import java.util.BitSet;

/**
 * 位集的使用
 */
public class BitSetTest {
    public static void main(String[] args) {
        // TODO 位集BitSet用于存储一个位序列，位集将位包装在字节里，使用位集要比使用Boolean对象的ArrayList要高效
        // 创建位集对象并指定初始容量
        BitSet bitSet = new BitSet(100);

        // 获得一个位，为0就是false，为1就是true
        boolean b = bitSet.get(10);

        // 设置一个位为1
        bitSet.set(10);

        // 清除一个位
        bitSet.clear(10);

        // 位集的逻辑长度，值为 1 + 位集的最高位
        System.out.println(bitSet.length()); //0

        // 位运算
        // 将位集设置为“5”
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(2);
        // 将另一个位集设置为“3”
        BitSet bitSet1 = new BitSet(100);
        bitSet1.set(0);
        bitSet1.set(1);
        // 与运算
        bitSet.and(bitSet1);
        System.out.println(bitSet); //{0, 1}
        // 或运算
        bitSet.or(bitSet1);
        // 异或运算
        bitSet.xor(bitSet1);
        // 对应另一个位集中设置为1的所有位，这个位集中相应的位清楚为0
        bitSet.andNot(bitSet1);

    }
}
