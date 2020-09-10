package primer.q4.cutTheStick;

import cn.dattyrabbit.programerArithmeticTopic.primer.q4.cutTheStick.CutTheStick;

/**
 * 切分木棒测试
 *
 * @version V1.0
 * @Package: primer.q4.cutTheStick
 * @author: 丁奕
 * @date: 2020-09-10 20:58
 **/
public class CutTheStickTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CutTheStick cutTheStick = new CutTheStick();
        cutTheStick.cutTheStick(1,20,3);
        cutTheStick.cutTheStick(1,100,5);
        long end = System.currentTimeMillis();
        System.out.println("总共用时：" + (end-start) +"ms");
    }
}
