package primer.q6.collatzProblem;

import cn.dattyrabbit.programerArithmeticTopic.primer.q6.collatzProblem.CollatzProblem;

/**
 * "（改版）考拉兹猜想"测试
 *
 * @version V1.0
 * @Package: primer.q6.collatzProblem
 * @author: 丁奕
 * @date: 2020-09-29 23:11
 **/
public class CollatzProblemTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CollatzProblem collatzProblem = new CollatzProblem(10000);
        collatzProblem.execute();
        long end = System.currentTimeMillis();
        System.out.println("总共用时：" + (end-start) +"ms");
    }
}
