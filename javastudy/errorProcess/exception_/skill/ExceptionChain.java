package javastudy.errorProcess.exception_.skill;

/**
 * 抛出异常更好的处理方法：异常链
 */
public class ExceptionChain {

    private static void room() throws Throwable {
        try {
            throw new NullPointerException("空指针");
        } catch (NullPointerException e) {
            //TODO 使用initCause()对异常进行包装，返回Throwable型异常，再使用getCause()获取原始的异常
            // 应用场景：在子系统中抛出高层异常，有时候会一层一层使用catch捕捉再抛出，异常的信息很有可能因为疏忽而改变，使用initCase()就可以
            // 避免丢失原始异常的细节
            throw new NullPointerException().initCause(e);
        }
    }

    public static void main(String[] args) {
        try {
            room();
        } catch (Throwable e) {
            e.getCause().printStackTrace();
        }
    }
}
