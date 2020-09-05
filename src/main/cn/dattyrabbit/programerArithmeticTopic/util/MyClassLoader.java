package cn.dattyrabbit.programerArithmeticTopic.util;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

/**
 * 自定义ClassLoader。用于加载动态编译的类
 *
 * @version V1.0
 * @Package: cn.dattyrabbit.programerArithmeticTopic.util
 * @author: 丁奕
 * @date: 2020-08-28 10:18
 **/
public class MyClassLoader extends ClassLoader {
    public static final String classPath = System.getProperty("user.dir") + "\\bin\\";

    /**
     * 自定义的一个加载方法,这样的做法似乎破坏了Java的双亲委派
     * @param classFullName
     * @return
     */
    protected Class customLoadClass(String classFullName){
        String filePath = classFullName2FileName(classFullName, classPath);
        byte[] data = loadClassFromFS(filePath );
        //调用defineClass将一个字节数据转换成一个类并进行初始化工作.
        return defineClass(classFullName, data, 0, data.length);
    }

    /**
     * 将一个完整类名转换为一个当前工程classpath为基础的文件路径.
     * @param classFullName     一个完整类名
     * @param classPath         当前工程类路径
     * @return
     */
    public String classFullName2FileName(String classFullName, String classPath){
        classFullName = classFullName.replaceAll("[.]", "\\\\" );
        return classPath  + classFullName + ".class";
    }

    /**
     * 从文件系统中加载类文件,生成一个byte[].
     * @param filePath   文件路径
     * @return   类文件的字节码数组
     */
    private byte[] loadClassFromFS(String filePath) {
        FileInputStream fis = null;
        byte[] byteSource = null;
        try {
            fis = new FileInputStream(new File(filePath ) );
            ByteArrayOutputStream tempSource = new ByteArrayOutputStream();
            int readChar = 0;
            while ((readChar = fis.read()) != -1) {
                tempSource.write(readChar );
            }
            byteSource = tempSource.toByteArray();
        } catch (IOException e) {
            //IO出错
            e.printStackTrace();
        }
        return byteSource;
    }

    @Override
    public Class<?> findClass(String str) throws ClassNotFoundException{
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 内存中的源代码保存在一个从JavaFileObject继承的类中
        JavaFileObject file = new JavaSourceFromString("Temp", str);
        // System.out.println(file);
        Iterable compilationUnits = Arrays.asList(file);
        // 需要为compiler.getTask方法指定编译路径：
        // 执行过程如下：
        // 1、定义类的字符串表示。
        // 2、编译类
        // 3、加载编译后的类
        // 4、实例化并进行调用。
        String flag = "-d";
        String outDir = System.getProperty("user.dir") + "\\" + "bin";
        Iterable<String> stringdir = Arrays.asList(flag, outDir); // 指定-d dir 参数
        // 建立一个编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null,
                stringdir, null, compilationUnits);
        // 编译源程序
        boolean result = task.call();
        if (result) {
            try {
                return customLoadClass( "Temp");
            } catch (Exception e) {
                throw new ClassNotFoundException("Temp", e);
            }
        }
        return null;
    }

}

class JavaSourceFromString extends SimpleJavaFileObject {
    private String code;

    public JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/')
                + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}