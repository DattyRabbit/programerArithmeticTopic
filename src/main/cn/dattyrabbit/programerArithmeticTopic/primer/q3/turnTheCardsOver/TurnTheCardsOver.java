package cn.dattyrabbit.programerArithmeticTopic.primer.q3.turnTheCardsOver;

/**
 * 《程序员的算法趣题》 - 入门篇 - Q03 - 翻牌
 *
 * 题目：这里有 100 张写着数字 1~100 的牌，并按顺序排列着。最开始所有
 * 牌都是背面朝上放置。某人从第 2 张牌开始，隔 1 张牌翻牌。然后第 2,
 * 4, 6, …, 100 张牌就会变成正面朝上。
 * 接下来，另一个人从第 3 张牌开始，隔 2 张牌翻牌（原本背面朝上
 * 的，翻转成正面朝上；原本正面朝上的，翻转成背面朝上）。再接下来，
 * 又有一个人从第 4 张牌开始，隔 3 张牌翻牌（ 图1 ）。
 * 像这样，从第 n 张牌开始，每隔 n－1 张牌翻牌，直到没有可翻动
 * 的牌为止。
 *
 * □□□□□□□□□……
 * 　↓　↓　↓　↓
 * □２□４□６□８□……
 * 　　↓　　↓　　↓
 * □２３４□□□８９……
 * 　　　↓　　　↓
 * □２３□□□□□９……
 *
 * ……
 *
 * 问题
 *
 * 求当所有牌不再变动时，所有背面朝上的牌的数字。
 *
 * @description 翻牌解题实现
 *                思路：这个题就用一个数组来模拟牌组即可，下标+1对应牌面大小，数组内存储0和1来记录牌的状态（0背面1正面）。
 *                循环数组大小次翻牌操作，然后输出数组内值为0的对应下标+1，则为执行完所有操作后仍然是背面向上的牌。
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.primer.q3.turnTheCardsOver
 * @author: 丁奕
 * @date: 2020-09-07 10:55
 **/
public class TurnTheCardsOver {
    //用于保存卡牌状态的数组。0代表背面，1代表正面，初始化时均为0
    private int cards[];

    /**
     * 有参构造方法，用于创建对象时初始化牌组大小
     * @param size
     */
    public TurnTheCardsOver(int size){
        this.cards = new int[size];
    }

    /**
     * 执行翻牌操作，并最终输出背面朝上的卡牌的数字。
     */
    public void turnOver(){
        //遍历操作，按照规则进行翻牌
        //执行遍历多轮翻牌
        for(int i = 2; i <= cards.length; i++){
            //执行单轮的翻牌操作，遍历该次需要进行操作的数组内元素，进行异或操作模拟翻牌动作
            for(int j = i-1; j < cards.length; j+=i){
                cards[j] = cards[j]^1;
            }
        }

        //输出打印的前缀
        System.out.print("执行完毕后，仍然背面向上的牌为：");
        //输出背面朝上的卡牌的数字（下标+1）
        for(int i = 0; i < cards.length; i++){
            if(cards[i] == 0){
                System.out.print( i + 1 + " " );
            }
        }

    }

}
