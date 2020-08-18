package primer.q1.palindromicNumber;

import cn.dattyrabbit.programerArithmeticTopic.primer.q1.palindromicNumber.PalindromicNumber;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查找回文数测试
 *
 * @version V1.0
 * @Package: PACKAGE_NAME
 * @author: 丁奕
 * @date: 2019-06-03 20:55
 **/
public class PalindromicNumberTest {
    //时间格式Format
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] arg){
        PalindromicNumber palindromicNumber = new PalindromicNumber();
        //一些单个的方法测试
//        System.out.println(palindromicNumber.findPalindromicNumber(10, 1));
//        System.out.println(palindromicNumber.buildPalindromic(5, true));
//        System.out.println(palindromicNumber.checkResult(10001));
//        System.out.println(palindromicNumber.getBuildStart(2001));
        //初始化参数
        int min = 585;
        //使用自己的方法
        System.out.println("***********使用自己的方法************");
        start(min, true);


        //使用作者的方法
        System.out.println("\n\n***********使用作者的方法************");
        start(min, false);
    }

    /**
     * 开始执行主测试方法，查找符合要求的回文数并输出。
     * @param min 不能小于某个数
     * @doMyFunc doMyFunc 使用自己的方法
     */
    private static void start(int min, boolean doMyFunc){
        //记录开始时间，并输出
        Date startTime = new Date();
        System.out.println("查找回文数开始 " + simpleDateFormat.format(startTime));

        PalindromicNumber palindromicNumber = new PalindromicNumber();

        //开始查找符合要求的大于参数min的回文数

        int result = 0;
        if(doMyFunc){
            //使用自己的方法
            int buildStart = palindromicNumber.getBuildStart(min);
            result = palindromicNumber.findPalindromicNumber(min, buildStart);
        }else{
            //使用作者的方法
            result = palindromicNumber.findPalindromicNumber(min);
        }

        System.out.println("得到结果为：" + result + "\n八进制:" + Integer.toOctalString(result) +
                "\n二进制:" + Integer.toBinaryString(result));

        //记录结束时间，并输出
        Date endTime = new Date();
        System.out.println("查找回文数结束 " + simpleDateFormat.format(endTime));
        //计算用时并输出
        Long costTime = endTime.getTime() - startTime.getTime();
        System.out.println("查找回文数用时 " + costTime + "毫秒");

    }
}
