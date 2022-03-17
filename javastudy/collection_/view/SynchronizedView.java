package javastudy.collection_.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 同步视图，保证集合的线程安全
 */
public class SynchronizedView {
    public static void main(String[] args) {
        // 将集合转换成有同步访问方法的新集合对象，方法都加了synchronized，都上了锁o(￣ヘ￣o＃).
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<String, Integer>());

    }
}
