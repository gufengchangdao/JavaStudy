package javastudy.thread_.asynchronous;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture类的演示
 * 组合完成Future
 */
public class CompletableFutureTest {
    /* TODO CompletableFuture
        1. CompletableFuture<T> 实现了 Future<T>, CompletionStage 两个接口。Future接口强调的是计算的结果，而CompletionStage描述了
        如何组合异步计算
        2. 与普通的Future不同，调用cancel方法时CompletableFuture的计算不会终止，反而cancel方法会抛一个CancellationException异常
        3. 对CompletableFuture<T>对象增加一个动作
            常用起手
            runAsync              -> void                       应用函数，结果为void
            supplyAsync           -> T                          对结果应用一个函数
            后续
            thenApply           T -> U                          对结果应用一个函数
            thenAccept          T -> void                       应用函数，结果为void
            thenCompose         T -> CompletableFunction<U>     对结果调用函数并执行返回的future
            异常
            thenRun              -> runnable                      执行Runnable，结果为void
            handle              (T, Throwable) -> U             处理结果或错误，生成一个新结果
            whenComplete        (T, Throwable) -> void          类似，结果为void
            exceptionally       Throwable -> void               如果抛出异常会调用函数处理
            时间
            completeOnTimeout   T,long,TimeUnit                 如果超时，生成给定值作为结果
            orTimeout           long, TimeUnit                  如果超时，生成一个TimeoutException异常
         4. 组合多个future的方法
            并发运行，组合结果
            thenCombine     CompletableFunction<U>,<T,U> -> V       执行两个动作并用给定函数组合结果
            thenAcceptBoth  CompletableFunction<U>,<T,U> -> void    类似，结果为void
            runAfterBoth    CompletableFunction<U>,Runnable         两个都完成后执行Runnable
            并发运行，接收最快的一个
            applyToEither   CompletableFunction<U>,T -> V           得到其中一个结果后(最快的那个),传入给定的函数
            acceptEither    CompletableFunction<U>,U -> void        类似，结果为void
            runAfterEither  CompletableFunction<?>,Runnable         其中一个完成后执行Runnable
            静态
            static allOf    CompletableFunction<?>, ...             所有给定的future都完成后完成，结果为void
            static anyOf    CompletableFunction<?>, ...             任意给定的future完成后则完成，结果为void
         5. 方法的返回值都是 CompletableFunction<T>，可以进行链式调用
     */
    /**
     * 案例一，我学习和妈妈去上班同时发生，随后我去网吧，随后我被妈妈发现了
     */
    @Test
    public void test01() {
        printInfo("早上起床");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            printInfo("妈妈去上班");
            sleep(2000);
            return "1";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            printInfo("我在家学习");
            sleep(1000);
            return "2";
        }), (a, b) -> { //线程b
            printInfo("妈妈走了2秒，我不藏了");
            return a + b + "3";
        }).thenCompose(action -> CompletableFuture.supplyAsync(() -> {
            printInfo("我开始出门去网吧,");
            sleep(1000);
            return action + "4";
        })).thenCompose(action -> CompletableFuture.supplyAsync(() -> {
            printInfo("但是被妈妈发现了");
            return action + "5";
        }));

        printInfo(completableFuture.join());
    }

    /**
     * 案例二，我坐公交车去网吧，700路公交车需要1秒到，800路公交车需要2秒到，我上了700路公交车但是中途撞树上了，我又重新叫了出租车
     */
    @Test
    public void test02() {
        System.out.println("我准备坐公交车去网吧");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            printInfo("700路公交车正在赶来...");
            sleep(1000);
            return "700路公交车";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            printInfo("800路公交车正在赶来...");
            sleep(2000);
            return "800路公交车";
        }), firstCome -> {
            if (firstCome.startsWith("700"))
                throw new RuntimeException("700路公交车撞树了");
            return firstCome;
        }).exceptionally(e -> {
            printInfo(e.getMessage());
            printInfo("我又重新叫了出租车");
            return "出租车";
        });
        // 上面的两种方法
        // 1. applyToEither() 与前面的任务一同执行，并将最先完成后的返回值交给Function对象的参数
        // 2. exceptionally() 当线程抛出异常的时候，会执行此方法

        System.out.println("我坐的是" + completableFuture.join());
    }

    /**
     * 无返回值的方法
     */
    @Test
    public void test03() {
        // 注意引用的泛型类型为Void类型，就表示能使用需要结果作为参数的方法
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> printInfo("实现Runnable接口，无返回值"));
        completableFuture.join(); // 需要手动开启

    }

    /**
     * 捕获异常
     */
    @Test
    public void test04() throws InterruptedException {
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("你好");
        }).whenComplete((s, e) -> { //whenComplete()有两个参数，一个是结果，一个是异常，没有就为null
            System.out.println("值为 " + s);
            System.out.println("异常为 " + e.getMessage());
        }).exceptionally(Throwable::getMessage); // 处理异常
        System.out.println(future.join()); //如果没有exceptionally()处理异常，这里使用join()就会抛出异常

    }

    public static void main(String[] args) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            while (true) ;
        });
        future.join();
        future.cancel(true);
    }

    private static void printInfo(String info) {
        System.out.println(Thread.currentThread().getName() + " : " + info);
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
