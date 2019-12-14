package concurrentprogramming.four;

import java.util.HashMap;

/**
 *
 * @author xq
 * @date 2019/12/14 00:46:55
 * @description 死循环死锁
 * @since v1
 */
public class HashMapDeadLock {
    private final HashMap<String, String> map = new HashMap<>();

    public void add(String key, String value) {
        this.map.put(key, value);
    }

    public static void main(String[] args) {
        final HashMapDeadLock hashMapDeadLock = new HashMapDeadLock();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < Integer.MAX_VALUE; i1++) {
                    hashMapDeadLock.add(String.valueOf(i1), String.valueOf(i1));
                }

            });
        }

    }
}
