package javastudy.keyword_;

public class BreakDemo {
    public static void main(String[] args) {
        // break可以跳出多重嵌套循环，就不用为各层循环添加一些额外的条件
        // 执行带标签的break会跳转到带标签的语句块末尾
        // 还可以应用到块语句上
        read_data:
        for (int i = 0; i < 10; i++) {
            System.out.println("外层循环进行 " + i);
            for (int j = 0; j < 10; j++) {
                System.out.println("内层循环进行 " + j);
                if (j == 5) break read_data;
            }
        }

        label:
        {
            // ...
            if (true) {
                break label;
            }
        }

    }
}
