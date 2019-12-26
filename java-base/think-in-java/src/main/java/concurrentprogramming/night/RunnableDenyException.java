package concurrentprogramming.night;

/**
 * @author xq
 * 装饰器模式 包装异常
 */
public class RunnableDenyException extends RuntimeException {
    public RunnableDenyException(String s) {
        super(s);
    }
}
