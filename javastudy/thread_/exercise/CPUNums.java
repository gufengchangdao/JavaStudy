package javastudy.thread_.exercise;

public class CPUNums {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("当前电脑的CPU数量：" + runtime.availableProcessors());
    }
}
