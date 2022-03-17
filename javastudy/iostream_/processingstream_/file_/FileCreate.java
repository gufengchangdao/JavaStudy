package javastudy.iostream_.processingstream_.file_;

import javastudy.iostream_.OpeTarget;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileCreate {

//    @Test
    public void fileCreate(){

        //创建对象的三种构造器
//        File(String pathname)
        File file = new File(OpeTarget.TARGET);

//        File(String parent, String child)
        String parent="d:\\";
        String child="temp.txt";
        file=new File(parent,child);

//        File(File parent, String child)
        file=new File(new File(parent),child);

        try {
            //创建文件
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void directoryCreate(){
        String oneDirectory="d:\\tempDirectory";
        String multiplyDirectory="d:\\tempDirectory\\a\\b\\c";

        File file = new File(oneDirectory);

        //创建一级目录
        file.mkdir();

        file=new File(multiplyDirectory);

        //创建多级目录
        file.mkdirs();

        //注意：delete方法一次只能删除一个目录/文件,不能删除非空的目录
//        while (!file.getName().equals("tempDirectory")){
        while (file!=null){     //最后把tempDirectory删除后file的父目录是null
            System.out.println("删除 "+file.getName());
            file.delete();
            file=file.getParentFile();
        }
    }

//    @Test
    public void fileMethod() throws IOException {
        File file = new File(OpeTarget.TARGET);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file1=file.getAbsoluteFile();//返回此抽象路径名的绝对形式。
        File parentFile=file.getParentFile();

        System.out.println("绝对路径:"+file.getAbsolutePath());//返回此抽象路径名的绝对路径名字符串。
        System.out.println("规范路径:"+file.getCanonicalPath());//返回此抽象路径名的规范路径名字符串。
        System.out.println("路径   :"+file.getPath());//将此抽象路径名转换为路径名字符串。

        System.out.println("文件/目录名:"+file.getName());//返回由此抽象路径名表示的文件或目录的名称。
        System.out.println("父级文件名:"+file.getParent());

        System.out.println("文件/目录是否存在:"+file.exists());
        System.out.println("是否是文件:"+file.isFile());
        System.out.println("是否是目录:"+file.isDirectory());
        System.out.println("是否被隐藏:"+file.isHidden());
        System.out.println("上次修改时间距现在的时间:"+(new Date().getTime() - file.lastModified()));

        System.out.println("是否删除成功:"+file.delete());

    }

}
