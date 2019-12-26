package concurrentprogramming.three;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link FightQueryTask} 实现Thread 业务
 *
 * @author xq
 * @date 2019/12/11 00:46:55
 * @description 查询航班集合
 * @since v1
 */
public class FightQueryExample {
    private static List<String> fightCompany = Arrays.asList("CSA", "CEA", "CHA");

    public static void main(String[] args) {
        List<String> results = search("SH", "BJ");
        System.out.println("==============result========================");
        results.forEach(System.out::println);

    }

    private static List<String> search(String original, String dest) {
        final List<String> result = new ArrayList<>();
        //创建航班信息的线程列表
        List<FightQueryTask> tasks = fightCompany.stream().map(f -> createSearchTask(f, original, dest)).collect(Collectors.toList());
        tasks.forEach(Thread::start);
        //当前线程调用join阻塞 main方法
        tasks.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        tasks.stream().map(FightQueryTask::get).forEach(result::addAll);
        return result;
    }

    private static FightQueryTask createSearchTask(String fight, String original, String dest) {
        return new FightQueryTask(fight, original, dest);
    }
}
