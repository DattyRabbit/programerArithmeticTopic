package cn.dattyrabbit.programerArithmeticTopic.primer.q4.cutTheStick;

/**
 * 《程序员的算法趣题》 - 入门篇 - Q04 - 切分木棒
 *
 * 题目：假设要把长度为 n 厘米的木棒切分为 1 厘米长的小段，但是 1 根木
 * 棒只能由 1 人切分，当木棒被切分为 3 段后，可以同时由 3 个人分别切
 * 分木棒。
 * 求最多有 m 个人时，最少要切分几次。譬如 n ＝ 8，m ＝ 3 时如下
 * 图所示，切分 4 次就可以了。
 *
 * 第一次：  １２３４｜５６７８
 *                  ／　　＼
 * 第二次： １２｜３４　　　５６｜７８
 * 　　　　　　／＼　　　　　　／　＼
 * 第三次：１｜２　３｜４　　５｜６　７８
 * 　　　　　／＼　　／＼　　／＼　　　↓      因为只有3个人，所以剩下一根木棒
 * 第四次：１　２　３　４　５　６　７｜８
 *　　　　　↓　↓　↓　↓　↓　↓　／＼
 *         １　２　３　４　５　６　７　８
 *
 *         n = 8, m = 3 的时候
 *
 * 问题1
 * 求当 n ＝ 20，m ＝ 3 时的最少切分次数。
 *
 * 问题2
 * 求当 n ＝ 100，m ＝ 5 时的最少切分次数。
 *
 * @description 切分木棒解题实现
 *                思路：这个题目应该就是使用递归的方式来调用解决，然后每一次递归把方法调用次数加上去，然后完成之后返回方法的总计
 *                调用次数（栈深度）即可。
 *
 *                木棒最初状态是1根，最后状态为n根。所以递归条件应该有当前木棒根数，木棒长度（需要切成多少根），人数，当递归判断
 *                出当前木棒根数大于等于长度后结束，否则，在当前根数小于人数时，均对半拆分（木棒根数翻倍），在大于人数时，木棒的
 *                根数只能再+m（因为只有m个人，对半切也是m个人多m根棒子）
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.primer.q4.cutTheStick
 * @author: 丁奕
 * @date: 2020-09-10 20:28
 **/
public class CutTheStick {

    /**
     * 提供给外部调用的方法。最终执行结果会打印
     * @param currentSticks
     * @param length
     * @param workers
     */
    public void cutTheStick(int currentSticks, int length, int workers){
        //调用实际的切分方法，并获得切分次数
        int result = cut(currentSticks, length, workers);
        //打印结果
        System.out.println("切分完成，当有" + workers + "人切长度为" + length + "的木棒时，最少要切" + result + "次");
    }

    /**
     * 切分木棒的方法
     * @param currentSticks 当前的木棒根数
     * @param length 需要切成多少根
     * @param workers 有多少可用的人
     * @return
     */
    private int cut(int currentSticks, int length, int workers){
        if(currentSticks >= length){
            //当木棒数量大于木棒长度时，则完成了切分
            return 0;
        }else if(currentSticks <= workers){
            //木棒数量小于可用的人数，则木棒数量翻倍进入下一次递归，+1次操作数
            return cut(currentSticks * 2, length, workers) + 1;
        }else{
            //木棒数量大于可用的人数，则木棒数量+可用人数进入下一次递归，+1次操作数
            return cut(currentSticks + workers, length, workers) + 1;
        }
    }
}
