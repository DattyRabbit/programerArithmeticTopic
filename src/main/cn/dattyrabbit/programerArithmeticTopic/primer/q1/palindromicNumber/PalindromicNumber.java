package cn.dattyrabbit.programerArithmeticTopic.primer.q1.palindromicNumber;

/**
 * 《程序员的算法趣题》 - 入门篇 - Q01 - 回文数
 *
 * 题目：如果把某个数的各个数字按相反的顺序排列，得到的数和原来的数相同，则这个数就是“回文数”。
 * 譬如123454321就是一个回文数
 *
 * 问题
 *
 * 求用十进制、二进制、八进制表示都是回文数的所有数字中，大于十进制数10的最小值
 *
 * 例） 9（十进制数） = 1001（二进制数） = 11（八进制数）
 * ※本例中的十进制数9小于10，因此不符合要求
 *
 *
 * @description 查找回文数算法实现
 *                思路：用十进制数，1,2,3...(((分别构造回文数，在十进制数为小于10的情况下，每个数只能构成一种回文数，其位数为偶数个。
 *                而在其大于等于10之后，每个数能选择构成位数为奇数个或者偶数个的回文数。例如10-->>101|1001。所以在遍历的时候，需要对
 *                遍历数进行按位区分，n位数(n>=2)的数进行回文数构造，需要先进行位数是奇数的回文数构造，如果位数为奇数的回文数构造验证
 *                完之后仍未有结果，再从头遍历n位数，用其构造位数为偶数位的回文数，并验证。
 *                例如，10 -> 101 验证不通过，11 -> 111验证不通过 .... 99 -> 999(假设此前都没有结果)。返回从10-99的遍历，这次生成形如
 *                1001,1111,1221的位数为偶数的回文数，并依次验证。
 *
 *                因为同样从1-65535，十进制数中的回文数 < 八进制表示的回文数 < 二进制表示的回文数。
 *                所以用十进制构建，再用八进制判断，二进制判断是否是回文数。
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.primer.q1.palindromicNumber
 * @author: 丁奕
 * @date: 2019-05-31 22:33
 **/
public class PalindromicNumber {

    /**
     * 作者给出的解法实现
     * @param min
     * @return
     */
    public int findPalindromicNumber(Integer min){
        //开始遍历的数
        int num = 0;
        //进行最开始的遍历数的初始化操作，使之为奇数
        if(min%2 == 0){
            num = min + 1;
        }else{
            num = min + 2;
        }
        //进入循环，直接对这个遍历的数进行3种进制下是否是回文数的判断。
        while (true){
            String numStr = Integer.valueOf(num).toString();
            String numStrRollback = new StringBuffer(numStr).reverse().toString();
            if(numStr.equals(numStrRollback)){
                //此处借用我自己所写的判断八进制和二进制是否是回文数的方法，不影响
                if(checkResult(num)){
                    break;
                }
            }
            num += 2;
        }
        return num;
    }

    /**
     * 构造回文，并找出符合要求的数
     * @param min 符合要求的数不小于min
     * @param buildStart 构造回文开始的数
     * @return
     */
    public int findPalindromicNumber(Integer min, Integer buildStart){
        String buildStartStr = buildStart.toString();
        //初始化符合要求的回文数返回值
        Integer resultNum = null;
        //是否进行奇数构造
        Boolean odd = true;
        //遍历中的i的长度
        Integer length_i = buildStart.toString().length();
        //构造数的长度
        Integer length_b = buildStartStr.length();
        for(Integer i = buildStart; length_i <= length_b; i ++){
            length_i = i.toString().length();
            if(odd == true && length_i > length_b){
                //将遍历数还原，开始进行偶数位的回文数构造
                odd = false;
                i = buildStart;
            }
            //进行回文数构造
            Integer palindromicNum = buildPalindromic(i, odd);
            //如果构建出的数既大于传入的最小值，又符合八进制二进制是回文数的要求，则返回该值。
            if(palindromicNum > min && checkResult(palindromicNum)){
                return palindromicNum;
            }
        }
        //遍历结束，仍未找到结果，从新设置buildStart的值，开始递归
        buildStart = (int)Math.pow(10, buildStartStr.length());
        resultNum = findPalindromicNumber(min, buildStart);
        return resultNum;
   }

    /**
     * 传入用作构建回文数的数字，以及构建出的回文数位数是奇数偶数为参数，从而构建回文数
     * @param sourceNum 原始数字，用于构建回文数的初始值
     * @param odd 是否构建奇数型回文，默认为true
     * @return
     */
   public int buildPalindromic(Integer sourceNum, Boolean odd){
       //做初始化操作，odd参数不传时设置为真
       if(odd == null){
           odd = true;
       }
       if(sourceNum < 1){
           throw new IllegalArgumentException("参数不正确，sourceNum(用于构建的数)不能小于1");
       }
       //开始构建
       String sourceNumStr = sourceNum.toString();
       StringBuffer rollbackNumStr = new StringBuffer(sourceNumStr).reverse();
       //初始化要返回的回文数StringBuffer型
       StringBuffer palindromiceNum = new StringBuffer();
       //先拼接上原始的构造数
       palindromiceNum.append(sourceNumStr);
       if(odd){
           //构建位数为奇数个的回文数
           palindromiceNum.append(rollbackNumStr.substring(1, sourceNumStr.length()));
       }else{
           //构建位数为偶数个的回文数
           palindromiceNum.append(rollbackNumStr.substring(0, sourceNumStr.length()));
       }
       return Integer.valueOf(palindromiceNum.toString());
   }

    /**
     * 验证传入数值是否满足要求,由于传入的数就是由十进制状态构建的回文数，故不验证十进制
     * @param checkNum 需要被检查的数值
     * @return
     */
   public boolean checkResult(Integer checkNum){
       //先转化成八进制，然后反转，进行判断。
       String octalString = Integer.toOctalString(checkNum);
       String rollbackOctalStr = new StringBuffer(octalString).reverse().toString();
       //判断的时候先判断在八进制状态下是否满足，再判断，因为同样从十进制的1到100，八进制的回文数比二进制少
       if(rollbackOctalStr.equals(octalString)){
           //通过八进制判断，在进行二进制转换，并判断
           String binaryString = Integer.toBinaryString(checkNum);
           String rollbackBinaryStr = new StringBuffer(binaryString).reverse().toString();
           if(rollbackBinaryStr.equals(binaryString)){
               return true;
           }
       }
       return false;
   }

    /**
     * 根据最小值，求出一个允许的最小构建回文数的值
     *
     * 思路: 若要求最后获得的回文数不能小于2001，则2001之后的第一个十进制表示的回文数应该是将2001作为字符串看待，从中截取一半，取前半段。
     *       若要求最后获得的回文数不能小于201，则201之后的第一个十进制表示的回文数应该是将201作为字符串看待，从中截取一半，不能完全平分，前半段保留多一个字符，取前半段。
     *       既可以通过字符串操作，也可以如下方法所实现，直接用数字的值进行操作。
     * @param min
     * @return
     */
    public int getBuildStart(Integer min) {
        if(min <= 100){
            return 1;
        }
        int length = min.toString().length();
        return min/(int)Math.pow(10,(length/2));
    }
}
