package primer.q2.sequenceArithmetic;

import cn.dattyrabbit.programerArithmeticTopic.primer.q2.sequenceArithmetic.SequenceArithmetic;

import java.util.ArrayList;

/**
 * 数列的四则运算测试
 *
 * @version V1.0
 * @Package: primer.q2.sequenceArithmetic
 * @author: 丁奕
 * @date: 2020-09-01 11:00
 **/
public class SequenceArithmeticTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SequenceArithmetic sequenceArithmetic = new SequenceArithmetic();
        ArrayList<Integer> integers = sequenceArithmetic.searchTargetNum(100, 99999);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
        long end = System.currentTimeMillis();
        System.out.println("总共用时：" + (end-start) +"ms");
    }
}
