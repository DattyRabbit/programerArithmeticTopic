package cn.dattyrabbit.programerArithmeticTopic.primer.q6.collatzProblem;

import java.util.ArrayList;

/**
 * 《程序员的算法趣题》 - 入门篇 - Q06 - （改版）考拉兹猜想
 *
 * 题目：“考拉兹猜想”是一个数学上的未解之谜。
 * 考拉兹猜想
 * 对自然数 n 循环执行如下操作。
 * ·n 是偶数时，用 n 除以 2
 * ·n 是奇数时，用 n 乘以 3 后加 1
 * 如此循环操作的话，无论初始值是什么数字，最终都会得到 1（会进入
 * 1 → 4 → 2 → 1 这个循环）。
 * 这里我们稍微修改一下这个猜想的内容，即假设初始值为偶数时，
 * 也用 n 乘以 3 后加 1，但只是在第一次这样操作，后面的循环操作不变。
 * 而我们要考虑的则是在这个条件下最终又能回到初始值的数。
 * 譬如，以2为初始值，则计算过程如下。
 * 2 → 7 → 22 → 11 → 34 → 17 → 52 → 26 → 13 → 40 → 20 → 10 → 5 →
 * 16 → 8 → 4 → 2
 * 同样，如果初始值为4，则计算过程如下。
 * 4 → 13 → 40 → 20 → 10 → 5 → 16 →8 → 4
 * 但如果初始值为6，则计算过程如下，并不能回到初始值6。
 * 6 → 19 → 58 → 29 → 88 → 44 → 22 → 11 → 34 → 17 → 52 → 26 → 13
 * →40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1 → 4 → …
 *
 * 问题
 * 求在小于 10000 的偶数中，像上述的 2 或者 4 这样“能回到初始值的数”有多少个。
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.primer.q6.collatzProblem
 * @author: 丁奕
 * @date: 2020-09-29 23:08
 **/
public class CollatzProblem {

    //满足要求的数
    public ArrayList<Integer> targetNumbers = new ArrayList<>();

    //排查范围
    public int range;

    //初始化构造方法
    public CollatzProblem(int range){
        this.range = range;
    }

    //计算范围内满足要求的数
    public void execute(){
        //从2开始循环遍历小于range的偶数，进行判断
        for(int i = 2; i <= range; i+=2){
            //若判断满足要求，则将此次遍历的值放入targetNumbers中
            if(checkCollatz(i,i)){
                targetNumbers.add(i);
            }
        }
        //循环结束，打印结果
        System.out.println("在小于"+range+"的偶数中，满足要求的数有"+targetNumbers.size()+"个，他们分别是:");
        for(Integer num : targetNumbers){
            System.out.print(" "+num);
        }
    }

    //判断这个数是否要求
    private boolean checkCollatz(int num, int temp) {
        //判断应该要对这个数进行什么操作
        if(temp == num || temp % 2 == 1){
            //先进行第一次的操作或者奇数操作
            temp = temp * 3 + 1;
        }else{
            temp = temp / 2;
        }
        //判断是否需要跳出递归
        if(temp == num){
            return true;
        }else if(temp == 1){
            return false;
        }else {
            return checkCollatz(num, temp);
        }
    }

}
