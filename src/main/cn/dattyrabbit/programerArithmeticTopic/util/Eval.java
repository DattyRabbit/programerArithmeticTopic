package cn.dattyrabbit.programerArithmeticTopic.util;

import java.lang.reflect.Method;

/**
 * 自定义的实现类似JavaScript中Eval()函数的工具类，但是因为需要返回计算式的运行结果，
 * 所以这个函数中传入的str不能是语句运行是void类型的。
 * （例如:"System.out.println(\"Hello\")"）
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.util
 * @author: 丁奕
 * @date: 2020-08-26 09:55
 **/
public class Eval {
    public static Object eval(String str) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("public class Temp");
        sb.append("{");
        sb.append("    public Object getObject()");
        sb.append("    {");
        sb.append("        Object obj = " + str + "return obj;");
        sb.append("    }");
        sb.append("}");
        // 调用自定义类加载器加载编译在内存中class文件
        // 说明：这种方式也需要些数据落地写磁盘的
        // 为毛一定要落地呢，直接内存里加载不就完了嘛
        // 应该也是可以的，它从磁盘读了也是进内存
        // 只不过java不允许直接操作内存
        // 写jni估计是可以
        Class clazz = new MyClassLoader().findClass(sb.toString());
        Method method = clazz.getMethod("getObject");
        // 通过反射调用方法
        return method.invoke(clazz.newInstance());
    }

    public static void main(String[] args) throws Exception {
        Object rval = eval("1+2+3+4;");
        System.out.println(rval);
    }
}
