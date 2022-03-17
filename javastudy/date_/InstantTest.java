package javastudy.date_;

import java.time.Duration;
import java.time.Instant;

/**
 * 演示Instant类的使用
 */
public class InstantTest {
    public static void main(String[] args) throws InterruptedException {
        Instant startTime = Instant.now(); //时间戳
        Thread.sleep(1000);
        Instant endTime = Instant.now();

        // 将时间间隔转为毫秒
        System.out.println(Duration.between(startTime, endTime).toMillis());

    }
}
