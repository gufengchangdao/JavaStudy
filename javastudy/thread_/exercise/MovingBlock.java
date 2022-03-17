package javastudy.thread_.exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * 使用SwingWorker类实现一个移动方块的程序
 */
public class MovingBlock {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            BlockJFrame frame = new BlockJFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class BlockJFrame extends JFrame {
    private static final int BLOCK_WIDTH = 60;
    private static final int BLOCK_HEIGHT = 60;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 500;
    // 方块对象
    private Rectangle2D.Double block;
    // 当前运行的工作对象
    private BlockWorker blockWorker;
    // 方块的移动方向，随便一个不同于wsad的初始化值
    private char direction = 'o';

    public BlockJFrame() {
        setLayout(null); //绝对布局
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2,
                FRAME_WIDTH, FRAME_HEIGHT);

        block = new Rectangle2D.Double((FRAME_WIDTH - BLOCK_WIDTH) / 2.0, (FRAME_HEIGHT - BLOCK_HEIGHT) / 2.0,
                BLOCK_WIDTH, BLOCK_HEIGHT);
        JComponent blockComponent = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.fill(block); //绘制方块
            }
        };
        blockComponent.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT); //得让组件大小占满整个屏幕
        add(blockComponent);

        // 监听键盘事件
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == direction) return;
                // 先取消原先工作线程，再创建一个新的工作线程
                if (blockWorker != null) blockWorker.cancel(true);
                blockWorker = new BlockWorker();
                switch (c) {
                    case 'a' -> blockWorker.setSpeed(-2, 0);
                    case 'w' -> blockWorker.setSpeed(0, -2);
                    case 'd' -> blockWorker.setSpeed(2, 0);
                    case 's' -> blockWorker.setSpeed(0, 2);
                }
                blockWorker.execute();
            }
        });

    }

    private class BlockWorker extends SwingWorker<Boolean, Void> {
        private int dx;
        private int dy;

        public void setSpeed(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        /**
         * 方块从一边移动到另一边比作一个耗时的程序
         */
        @Override
        protected Boolean doInBackground() throws Exception {
            while ((block.x + BLOCK_WIDTH < FRAME_WIDTH) && (block.y + BLOCK_HEIGHT < FRAME_HEIGHT)
                    && block.x >= 0 && block.y >= 0) {
                block.x += dx;
                block.y += dy;
                publish();
                Thread.sleep(20);
            }
            return true;
        }

        /**
         * 不时地重绘比作更新界面，报告工作进度
         */
        @Override
        protected void process(List<Void> chunks) {
            repaint();
        }

        /**
         * 撞到墙和工作线程取消都表示结束
         */
        @Override
        protected void done() {
            boolean result = false;
            try {
                result = get(); //是抛异常还是返回true
            } catch (CancellationException e) {
                System.out.println("任务被取消");
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("工作结束，是否碰到墙壁: " + result);
        }
    }
}