package primer.q5.cashPayment;

import cn.dattyrabbit.programerArithmeticTopic.primer.q5.cashPayment.CashPayment;

/**
 * "还在用现金支付吗"测试
 *
 * @version V1.0
 * @Package: primer.q5.cashPayment
 * @author: 丁奕
 * @date: 2020-09-26 15:24
 **/
public class CashPaymentTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CashPayment cashPayment = new CashPayment();
        cashPayment.execute(1000);
        long end = System.currentTimeMillis();
        System.out.println("总共用时：" + (end-start) +"ms");
    }
}
