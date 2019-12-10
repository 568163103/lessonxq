package concurrentprogramming.three;

import java.util.List;

/**
 * @author xq
 * @date 2019/12/11 00:44:23
 * @description 查询航班集合
 * @since v1
 */
public interface FightQuery {
    /**
     * 获取集合
     * @return  String集合
     */
    List<String> get();
}
