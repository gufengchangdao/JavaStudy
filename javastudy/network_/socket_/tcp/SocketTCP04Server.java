package javastudy.network_.socket_.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP服务端
 * 端口号：9999
 * 从键盘中读取内容发送给服务端，再由服务端返回内容给客户端并打印在显示屏上
 * 为了回顾内容，还用了多线程操作，服务端是用子线程启动的
 *
 */
public class SocketTCP04Server {
    public static void main(String[] args) throws IOException {
        //用多线程来实现服务端
        Thread thread = new Thread(new InlineReceive());
        thread.start();
    }
}

class InlineReceive implements Runnable {
    private boolean loop = true;

    @Override
    public void run() {
        try {
            //指定端口
            ServerSocket serverSocket = new ServerSocket(9999);

            //等待连接
            System.out.println("--- 服务端等待连接... ---");
            Socket socket = serverSocket.accept();

            //创建IO流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //循环问答
            while (loop) {
                System.out.println("--- 我是人工智障小哀，正在接收密语: ---");

                //读取输入内容
                String content = bufferedReader.readLine();
                System.out.println("收到信息，已回复 o(∩_∩)o");

                //做成回应
                String sendContent;
                if (content.equals("傻狗")) {
                    loop = false;
                    sendContent = "--- 不理你了，哼(＞﹏＜) ---";
                    System.out.println("别来找我，我不在！ヾ|≧_≦|〃");
                } else if (content.equals("name")) {
                    sendContent = "我是诺娃";
                } else if (content.equals("hobby")) {
                    sendContent = "编写java程序";
                } else {
                    sendContent = "你说啥";
                }

                //发送内容
                bufferedWriter.write(sendContent);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }

            //关闭资源
            bufferedWriter.close();
            bufferedReader.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
