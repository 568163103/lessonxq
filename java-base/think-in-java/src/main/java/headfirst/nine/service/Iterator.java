package headfirst.nine.service;

/**
 * @author xq
 */
public interface Iterator {
    /**
     * 是否存在下一个对象
     *
     * @return
     */
    boolean hasNext();

    /**
     * 遍历下一个集合
     *
     * @return
     */
    Object next();
}
