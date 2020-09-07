package primer.q3.turnTheCardsOver;

import cn.dattyrabbit.programerArithmeticTopic.primer.q3.turnTheCardsOver.TurnTheCardsOver;

/**
 * 翻牌测试
 *
 * @version V1.0
 * @Package: primer.q3.turnTheCardsOver
 * @author: 丁奕
 * @date: 2020-09-07 11:51
 **/
public class TurnTheCardsOverTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        TurnTheCardsOver turnTheCardsOver = new TurnTheCardsOver(9999);
        turnTheCardsOver.turnOverByAuthor();
        long end = System.currentTimeMillis();
        System.out.println("总共用时：" + (end-start) +"ms");
    }
}
