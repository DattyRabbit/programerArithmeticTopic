package cn.dattyrabbit.programerArithmeticTopic.primer.q2.sequenceArithmetic;

import cn.dattyrabbit.programerArithmeticTopic.util.Calculator;
import cn.dattyrabbit.programerArithmeticTopic.util.Eval;
import com.sun.deploy.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 《程序员的算法趣题》 - 入门篇 - Q02 - 数列的四则运算
 *
 * 题目：大家小时候可能也玩过“组合车牌号里的 4 个数字最终得到 10”的游戏。
 *
 * 组合的方法是在各个数字之间插入四则运算的运算符组成算式，
 * 然后计算算式的结果（某些数位之间可以没有运算符，但最少要插入 1 个运算符）。
 *
 * 例）
 *
 * 1234 → 1 ＋ 2×3 - 4 ＝ 3
 * 9876 → 9×87 ＋ 6 ＝ 789
 *
 * 假设这里的条件是，组合算式的计算结果为“将原数字各个数位上的数逆序排列得到的数”，
 * 并且算式的运算按照四则运算的顺序进行（先乘除，后加减）。
 *
 * 那么位于 100~999，符合条件的有以下几种情况。
 *
 * 351-→-3×51 ＝ 153
 * 621-→-6×21 ＝ 126
 * 886-→-8×86 ＝ 688
 *
 * 问题
 *
 * 求位于 1000~9999，满足上述条件的数。
 *
 * @description 数列的四则运算实现
 *                思路：看到这个题目的时候，最初能想到的就是使用JavaScript中的eval()函数来解决是最便捷的。但是因为JAVA没有类似
 *                的功能，所以就需要自己来实现一个类似的功能。
 *
 *                这里我目前能想到的实现eval()函数功能的方式就是使用动态编译和动态加载来完成。所以得先完成实现该功能的相关工具
 *                类，然后再在这个类当中去使用工具类的方法，实现类似JavaScript的eval()函数来完成解题。
 *
 *                然后在已有实现类似eval()函数功能之后，再去遍历1000~9999，然后分别把四则运算的操作数也遍历加入到1000~9999的字
 *                符串之中，然后再使用实现的eval()方法来及时运算，获得结果再判断即可。当然这里四则运算的操作书除了基本的加减乘
 *                除外还需要多定义一个空符号""，表示不加入操作数，让前后的单个数字连起来作为一个整数计算。
 *
 *                ------------------------
 *                使用最初想到动态编译和动态加载的方式有大量的I/O操作，所以性能较低，耗时巨长，后来找了一种使用avaScriptEngine处理的方法，
 *                耗时减少很多，但是还是觉得耗时挺长的。最后才另寻方法，换为将计算表达式转化为后缀表达式再计算的方法。时间开销终于
 *                降到一个可以接受的范围。
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.primer.q2.sequenceArithmetic
 * @author: 丁奕
 * @date: 2020-08-26 09:37
 **/
public class SequenceArithmetic {
    //初始化ScriptEngine
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");
    /**
     * 包装方法，用于调用的时候不传type，给type赋予0的默认处理方式。
     * @param min
     * @param max
     * @return
     */
    public ArrayList<Integer> searchTargetNum(int min, int max){
        return searchTargetNum(min, max, 0);
    }

    /**
     * 根据传入的最大和最小值来查找其中符合要求的数字
     * @param min 最小值，大于等于
     * @param max 最大值，小于
     * @param type 处理方式: 0|其他 -> 转换为后缀表达式计算;
     *                        1 -> 使用JavaScriptEngine 中的eval处理
     *                        2 -> 使用态编译和动态加载处理
     * @return
     */
    public ArrayList<Integer> searchTargetNum(int min, int max, int type){
        //参数验证
        if(min >= max){
            return (ArrayList)Arrays.asList(-1);
        }
        //初始化满足的数的list
        ArrayList<Integer> targetNum = new ArrayList<>();
        //设置遍历的初始值
        int num = min;
        //定义操作符的数组,空字符暂用_代替，传入eval时替换为空
        char options[] = {'_','*'};
        //循环遍历
        for(; num < max ; num++){
            //打印当前验证的数字
//            System.out.println("正在处理:--- "+ num +" ---数字");
            //遍历每个数的时候根据数字转换为字符串，并再遍历操作符，在数字之间插入符号
            char operand[] = String.valueOf(num).toCharArray();
            //根据当前num的值，计算需要插入的操作符的个数，从而求出操作符的组合数量。遍历改组合数量，化为二进制。
            //需要插入的操作符的组合数量就是 (操作符的长度)^(位数 - 1)。因为操作符的长度为2,所以结果是2的N次幂
            int operatorNums = (int)Math.pow((double)options.length,(operand.length - 1));
            //判断拼接后的字符串，至少插入了一个操作符所以遍历操作符应该从1开始，到operatorNums-1结束。
            //0开始则没有添加任何操作符，遍历到operatorNums则操作符会加在数字的外面而不是数字的中间
            for(int i = 1; i < operatorNums; i++){
                //转换成二进制,且根据需要的位数进行左边补零的操作
                String operatorBinary = zeorFill(Integer.toBinaryString(i),operatorNums);
                //开始拼接放入eval函数中的语句
                String evalStatement = "";
                for(int j = operand.length - 1; j >= 0; j--){
                    evalStatement += operand[j];
                    //判断是否是最后一个数
                    if(j != 0){
                        evalStatement += options[Integer.parseInt(String.valueOf(operatorBinary.charAt(j-1)))];
                    }
                }
                //替换_符号。
                evalStatement = evalStatement.replaceAll("_","");
                //处理evalStatement语句中的各个操作数，使其符合java语法规范。因为出现08*1这样的数字是编译无法通过的。所以要对各操作数进行去除左边的0的操作。
                evalStatement = handleStatement(evalStatement);
                //加分号
                evalStatement += ";";
                try{
                    int check;
                    if(type == 1){
                        check = (int) engine.eval(evalStatement);
                    }else if(type == 2){
                        check = (int)Eval.eval(evalStatement);
                    }else{
                        check = ((Double)Calculator.calculate(evalStatement)).intValue();
                    }
                    //若满足条件，放入targetNum中
                    if(num == check){
                        System.out.println(evalStatement);
                        targetNum.add(num);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return targetNum;
    }

    /**
     * 根据传入的待计算语句进行处理，对各个操作数进行去除左边多余的0的操作，使其符合语法规范
     * @param evalStatement
     * @return
     */
    private String handleStatement(String evalStatement) {
        String[] operatorNums = evalStatement.split("\\*");
        List<String> array = new ArrayList<>();
        for (String operatorNum : operatorNums) {
            //用字符串转Int再转字符串的操作来去左边的0
            array.add(String.valueOf(Integer.parseInt(operatorNum)));
        }
        String resultStatement = StringUtils.join(array, "*");
        return resultStatement;
    }

    /**
     * 根据传入的二进制数（Integer.toBinaryString直接转换的数）和允许的最大的数字进行左边补零的操作，
     * @param originalBinary
     * @param operatorNums
     * @return
     */
    private String zeorFill(String originalBinary, int operatorNums) {
        String binaryStr = originalBinary;
        String maxOperatorNums = Integer.toBinaryString(operatorNums - 1);
        while(binaryStr.length() < maxOperatorNums.length()){
            binaryStr = "0"+binaryStr;
        }
        return binaryStr;
    }
}
