package javastudy.thread_.threadsecuritycollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 演示并发散列映射的批操作
 */
public class ConcurrentHashMapBatch {
    private static final ConcurrentHashMap<String, Integer> creature = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        /* TODO 批操作
            1. 并发散列映射的批操作，即使在有其他线程在处理映射，这些操作也能安全的执行。批操作会遍历映射，处理遍历过程中找到的元素
            2. 有3种不同的操作：
                search  为每个键或值应用一个函数，直到函数生成一个非null的结果(就会停止遍历)，返回这个函数的结果
                reduce  组合所有键或值，需要提供一个累加函数
                forEach 为所有键或值应用一个函数
            3. 每个操作有4个版本
                处理键
                处理值
                处理键和值
                处理Map.Entry对象
            4. 对于上述操作，需要指定一个参数化阈值，如果映射包含的元素多于这个阈值，就会并行完成批操作。如果希望批操作在一个线程中完成，可以设
            为Long.MAX_VALUE，如果希望尽可能多的线程，就设为 1。
         */
        init();
        // todo 如果函数返回不为null就结束遍历，如果所有函数都返回null则返回null
        // 输出遍历到的第一只羊
        Object firstSheep = creature.search(1, (k, v) -> { // TODO: 阈值设为1，具体开多少线程，得看源码
            if (k.endsWith("羊羊")) return k;
            return null;
        });
        System.out.println(firstSheep); //沸羊羊

        // todo forEach()有两种形式，一种是映射条目都应用于一个消费者函数
        // 输出所有角色
        creature.forEach(1, (k, v) -> System.out.println(k + " -> " + v));
        // todo 另一种是中间添加一个转换器函数，该函数的返回值会传递给消费者函数。转换器函数如果返回null的话，这个值就会被跳过
        // 输出所有狼的分数
        creature.forEach(1, (k, v) -> k.contains("狼") ? v : null, System.out::println);

        // todo reduce()用一个累加函数组合其输入。如果所有映射为空，或者所有条目被过滤掉的话就会返回null。
        //  如果只有一个键值对，则直接返回转换结果，不进行累加器
        // 求所有角色分数之和
        Integer allScore = creature.reduceValues(Long.MAX_VALUE, Integer::sum);
        // 50分以上的角色分数之和
        Integer allScore2 = creature.reduceValues(Long.MAX_VALUE, v -> v >= 50 ? v : null, Integer::sum);
        // todo 还可以设置结果的输出类型。第二个参数为类型的转换器，需要把Integer对象类型转为基本类型long
        //  第三个参数为默认值，当出现上面没有结果返回null的时候这个方法返回默认值。注意返回值是与默认值累加，所以默认值必须得是零元素
        long allScore3 = creature.reduceValuesToLong(1, Integer::longValue, 1, Long::sum);
    }

    private static void init() {
        creature.put("喜羊羊", 100);
        creature.put("懒羊羊", 40);
        creature.put("沸羊羊", 80);
        creature.put("村长", 90);
        creature.put("美羊羊", 95);
        creature.put("灰太狼", 99);
        creature.put("红太狼", 85);
        creature.put("小灰灰", 75);
        creature.put("白眼狼", 100);
    }
}
