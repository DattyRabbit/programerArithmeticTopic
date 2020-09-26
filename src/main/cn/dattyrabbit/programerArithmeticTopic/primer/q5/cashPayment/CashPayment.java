package cn.dattyrabbit.programerArithmeticTopic.primer.q5.cashPayment;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 《程序员的算法趣题》 - 入门篇 - Q05 - 现在还用现金支付吗
 *
 * 题目：当下，坐公交或者地铁时大部分人都是刷卡的。不过，时至今日还
 * 在用现金支付的人还是比想象的多。本题我们以安置在公交上的零钱兑
 * 换机为背景。
 *
 * 这个机器可以用纸币兑换到 10 日元、50 日元、100 日元和 500 日
 * 元硬币的组合，且每种硬币的数量都足够多（因为公交接受的最小额度
 * 为 10 日元，所以不提供 1 日元和 5 日元的硬币）。
 *
 * 兑换时，允许机器兑换出本次支付时用不到的硬币。此外，因为在
 * 乘坐公交时，如果兑换出了大量的零钱会比较不便，所以只允许机器最
 * 多兑换出 15 枚硬币。譬如用 1000 日元纸币兑换时，就不能兑换出
 * “100 枚 10 日元硬币”的组合。

 *
 * 问题
 * 求兑换 1000 日元纸币时会出现多少种组合？注意，不计硬币兑出的先后顺序。
 *
 * 兑换示例
 *
 *      1000
 *      ║
 *     ┢→ 100、100、100、100、100、100、100、100、100、100
 *    ┢→ 500、100、100、100、100、100
 *   ┢→ 500、100、100、100、100、50、50
 *  ┕→ 500、100、100、100、100、50、10、10、10、10、10
 *
 * @description "现在还用现金支付吗"解题实现
 *                思路：将能兑换出的硬币面额数字用数组保存，待兑换的面额作为参数传入，
 *                使用递归方法，将待兑换的数值和可用来兑换的数组以及允许兑换的次数作为参数
 *                进行传递，递归方法中会取可兑换面额的首位，将其从原数组中取出，然后进行判断
 *                如果可兑换的数组为空，则用当前兑换的币值和待兑换的面值作除法，结果如果小于等于
 *                允许的最大兑币个数，则满足。如果可兑换数组仍有数，则从0到待兑换面额除以当前兑换的币值结果来遍历，遍历中
 *                递归调用本方法，参数为待兑换的币值 - 当前兑换的币值 * 遍历数作为下次的待兑换币值，剩下的可兑换的硬币面额数组
 *                作为下次的可兑换数组，然后允许的最大兑换个数 - 当前遍历数作为下次的允许的最大兑换数量。
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.primer.q5.cashPay
 * @author: 丁奕
 * @date: 2020-09-26 13:40
 **/
public class CashPayment {

    //硬币面额数组
    private Integer[] coinDenominations = {500,100,50,10};
    //能兑换的总组合数
    private int exchangeGroupCount = 0;

    /**
     * 获得待兑换的纸币总共能有多少总兑换组合。
     * @param banknotes
     */
    public void execute(int banknotes){
        ArrayList<Integer> denominations = new ArrayList<>();
        Collections.addAll(denominations, coinDenominations);
        //暂时保存兑换时各个硬币面额的数量
        int[] exchangeCoinGroup = new int[coinDenominations.length];
        exchangeCash(banknotes, denominations, 15, exchangeCoinGroup);
        System.out.println("兑换"+banknotes+"面值的纸币，有"+exchangeGroupCount+"种兑换结果");
    }

    /**
     * 根据需要兑换的纸币面额，求出可以兑换的硬币组合并打印
     * @param banknotes 待兑换的面额
     * @param denominations 可兑换的硬币面额
     * @param max 最大能兑换的硬币数
     */
    public void exchangeCash(int banknotes, ArrayList<Integer> denominations, int max, int[] exchangeCoinGroup){
       //获得当前使用的兑换币面额
        int coin = denominations.remove(0);

        //保存当前需要操作的面额数组的下标
        int currentIndex = coinDenominations.length - 1 - denominations.size();
        if(denominations.isEmpty()){
            //兑换完成
            if(banknotes / coin <= max){
                //进行当前组合面额的保存
                exchangeCoinGroup[currentIndex] = banknotes / coin;
                //输出组合，总组合数+1
                exchangeGroupCount += 1;
                //TODO 输出打印单次的兑换结果
                System.out.print("兑换组合可以为：");
                for(int i = 0; i < coinDenominations.length; i++){
                    System.out.print(exchangeCoinGroup[i]+"个"+coinDenominations[i]+"元硬币 ");
                }
                System.out.println();
            }
        }else{
            //兑换未完成，进行递归兑换
            for(int i = 0; i <= banknotes/coin; i++){
                //进行当前组合面额的保存
                exchangeCoinGroup[currentIndex] = i;
                exchangeCash(banknotes - coin * i, (ArrayList<Integer>) denominations.clone(), max - i, exchangeCoinGroup);
            }
        }
    }
}
