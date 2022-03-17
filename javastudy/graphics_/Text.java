package javastudy.graphics_;

import javax.swing.*;
import java.awt.*;

/**
 * 输出一段话
 */
public class Text extends JFrame {
    public Text() throws HeadlessException {
        setBounds(300,300,500,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(new JPanel(){
            @Override
            public void paint(Graphics g) {
                Graphics2D g2=(Graphics2D) g;
                // 设置字体
                Font font = new Font("宋体", Font.BOLD, 16);
                // PLAIN                    正常
                // BOLD                     粗体
                // ITALIC                   斜体
                // Font.ITALIC|Font.BOLD    这个是混合使用

                g2.setFont(font);

                // 参考点好像是字符串的左下角坐标
                g2.drawString("陌生人，你好，我可以向你倾诉几句吗？",0,17);

            }
        });

    }

    public static void main(String[] args) {
        new Text().setVisible(true);
    }
}
