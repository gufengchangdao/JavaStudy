package javastudy.graphics_;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * 字体类相关方法演示
 */
public class FontTest {
    public static void main(String[] args) {
        // TODO 获取计算机上可用的字体数组
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        // for (String name : fontNames) {
        //     System.out.println(name);
        // }

        // TODO 将字符串居中显示并绘制基线和包围这个字符串的矩形
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComponent component = new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    // 指定字体名、字体风格、字体大小
                    Font font = new Font("Digital Numbers", Font.BOLD, 30);
                    g2.setFont(font);

                    // TODO 获取字体对象相关信息
                    String fontName = font.getFontName(); //字体名
                    String name = font.getName(); //如果采用逻辑字体名创建字体则返回逻辑字体名，否则返回字体名
                    String family = font.getFamily();
                    int size = font.getSize();

                    String message = "abc123456789";
                    // 返回字体的字体绘制上下文
                    FontRenderContext context = g2.getFontRenderContext();
                    // 获取包围字符串的矩形
                    Rectangle2D bounds = font.getStringBounds(message, context);
                    double stringWidth = bounds.getWidth(); //矩形宽度
                    double stringHeight = bounds.getHeight(); //矩形高度
                    double ascent = -bounds.getY(); //上坡度，getY()返回一个负值，注意这不是字体的坐标
                    // 获取确定字符串宽度的一个度量对象
                    LineMetrics metrics = font.getLineMetrics(message, context);
                    float ascent1 = metrics.getAscent(); //上坡度，跟上面的一样
                    float descent = metrics.getDescent(); //下坡度
                    float leading = metrics.getLeading(); //行间距

                    // 打印字符串相关信息
                    System.out.println("字体的宽度为 " + stringWidth + "\n字体的高度为 " + stringHeight + "\n字体的上坡度为 " +
                            ascent + "\n字体的下坡度为 " + descent + "\n字体的行间距为 " + leading);

                    double x = (frame.getWidth() - stringWidth) / 2;
                    double y = (frame.getHeight() - stringHeight) / 2;
                    // 调整y值，得到基线
                    double baseY = y + ascent;
                    // 绘制字符串
                    g2.drawString(message, (int) x, (int) baseY);
                    // 绘制基线
                    g2.draw(new Line2D.Double(x, baseY, x + stringWidth, baseY));
                    // 绘制矩形
                    g2.draw(new Rectangle2D.Double(x, y, stringWidth, stringHeight));
                }
            };

            frame.add(component);
            frame.setVisible(true);
        });
    }
}
