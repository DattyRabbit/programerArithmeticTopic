package cn.dattyrabbit.programerArithmeticTopic.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于直接计算数学表达式的结果。
 * 参考文章:https://blog.csdn.net/xujiangdong1992/article/details/79508993
 * 该实现是基于讲数学表达式转换为逆波兰表示法（后缀表达式），然后通过后缀表达式进行计算。
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.util
 * @author: 丁奕
 * @date: 2020-09-05 09:34
 **/
public class Calculator {
    //运算字符栈  +-*/
    static Stack<String> stack = new Stack<>();
    //后缀表达式集合
    static List<String> list = new ArrayList<String>();

    public static Object calculate(String str) throws Exception{
        toRpn(str);//转换成后缀表达式
        Object result = calcRpn(list);
        stack = new Stack<>();
        list = new ArrayList<>();
        return result;
    }
    /**
     * 转换成后缀表达式
     */
    static void toRpn(String reg) {
        List<String> strList=new ArrayList<String>();
        //将输入字符串分割成符号序列
        String sRegExp="(((?<=^|\\(|\\+|-|\\*|/|%)(\\+|-))?\\d+(\\.\\d+)?)"
                +"|\\(|\\)|\\*|/|\\+|-";
        Pattern p=Pattern.compile(sRegExp);
        Matcher m=p.matcher(reg.replaceAll("\\s+",""));
        //遍历序列放入list集合
        while(m.find()){
            strList.add(m.group());
        }
        for(int i=0;i<strList.size();i++) {
            String str = strList.get(i);
            switch (str) {
                case "+":
                case "-":
                    priorityCheck(str, 1);//优先级校验，参数1：运算字符，参数2：运算优先级
                    break;
                case "*":
                case "/":
                    priorityCheck(str, 2);
                    break;
                case "(":
                    stack.push(str);//进栈
                    break;
                case ")":
                    popIf();
                    break;
                //默认数字输出
                default:
                    list.add(str);
                    break;
            }
            //最后还在栈中的元素输出
            if(i==strList.size()-1){
                while(!stack.isEmpty()){
                    list.add(stack.pop());
                }
            }
        }
    }
    /**
     *  *\/ 优先级大于 + - 优先级
     * 与栈顶运算符比较 一直弹出 直到遇到更大优先级运算符或者栈为空
     * @param str 传入元素
     * @param adv 优先级
     * @return
     */
    static List<String> priorityCheck(String str, int adv) {
        while (!stack.isEmpty()) {
            String strP = stack.pop();
            //遇到（则退出循环 再将其放入即可
            if (strP.equals("(")) {
                stack.push(strP);
                break;
            }
            int adv2 = 0;// 遇到的另一个元素优先级
            if (strP.equals("+") || strP.equals("-")) {
                adv2 = 1;
            } else {
                adv2 = 2; // 定义优先级
            }
            //如果运算符优先级低，则把原来弹出元素放回 如：*遇-则放回,-遇*弹出
            if (adv2 < adv) {
                stack.push(strP);
                break;
            } else {
                list.add(strP);
            }
        }
        stack.push(str);//将运算符放入栈
        return list;
    }
    /**
     * 括号配对输出括号中的内容
     *
     * @return
     */
    static List<String> popIf() {
        String str;
        while (!stack.isEmpty()) {
            str = stack.pop();
            // 弹出的值不是"("就一直加下去
            if (!str.equals("(")) {
                list.add(str);
            } else {
                break;
            }
        }
        return list;
    }
    /**
     * 计算后缀表达式的值
     */
    static double calcRpn(List<String> rpnList) throws Exception{
        NumberFormat nf=NumberFormat.getInstance();
        //运算结果栈
        Stack<Double> numStack=new Stack<Double>();
        for (String optr : rpnList) {
            if (optr.matches("^(\\+|-)?\\d+(.\\d+)?$")) {
                optr=optr.indexOf('+')==0
                        ?optr.substring(1)
                        :optr;
                numStack.add(nf.parse(optr).doubleValue());
            } else {
                doCalcByOptr(optr, numStack);
            }
        }

        if (!numStack.isEmpty() && numStack.size() == 1) {
            return numStack.lastElement();
        } else {
            throw new Exception("字符格式有问题，请输入正确的运算字符!");
        }
    }
    /**
     * 根据运算符对数据栈中的内容进行操作.
     */
    public static void  doCalcByOptr(String optr,Stack<Double> numStack){
        double n1,n2;
        try {
            n2=numStack.pop();
            n1=numStack.pop();
            if(optr.equals("+")){
                numStack.push(n1+n2);
            }else if(optr.equals("-")){
                numStack.push(n1-n2);
            }else if(optr.equals("*")){
                numStack.push(n1*n2);
            }else if(optr.equals("/")){
                numStack.push(n1/n2);
            }
        } catch (Exception e) {
            System.out.println("运算字符格式有问题，请输入正确的运算字符!");
        }
    }
}
