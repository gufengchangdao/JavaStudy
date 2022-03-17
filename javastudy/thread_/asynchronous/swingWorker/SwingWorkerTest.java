package javastudy.thread_.asynchronous.swingWorker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * 演示SwingWorker类的使用
 * 演示一个运行耗时任务的工作线程
 *
 * @author Cay Horstmann
 * @version 1.12 2018-03-17
 */
public class SwingWorkerTest {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(() -> {
            var frame = new SwingWorkerFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * This frame has a text area to show the contents of a text file, a menu to open a file and
 * cancel the opening process, and a status line to show the file loading progress.
 */
class SwingWorkerFrame extends JFrame {
    private JFileChooser chooser;
    private JTextArea textArea;
    private JLabel statusLine;
    private JMenuItem openItem;
    private JMenuItem cancelItem;
    private SwingWorker<StringBuilder, ProgressData> textReader;
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 60;

    public SwingWorkerFrame() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("src/javastudy/thread_/asynchronous/swingWorker"));

        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea));

        statusLine = new JLabel(" ");
        add(statusLine, BorderLayout.SOUTH);

        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        var menu = new JMenu("File");
        menuBar.add(menu);

        openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(event -> {
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                textArea.setText("");
                openItem.setEnabled(false);
                // 每当要在工作线程中做一些工作时，都会构造一个新的工作器
                textReader = new TextReader(chooser.getSelectedFile());
                // TODO: execute()
                //  1. 安排此SwingWorker在工作线程上执行。 有许多工作线程可用。 如果所有工作线程都忙于处理其他SwingWorkers,则此
                //  SwingWorker将被放置在等待队列中。
                //  2. 注意，该对象创建后只能使用一次，调用execute()后会调用doInBackground()方法
                //  3. 通常在事件分配线程中调用execute()方法
                textReader.execute();
                cancelItem.setEnabled(true);
            }
        });

        cancelItem = new JMenuItem("Cancel");
        menu.add(cancelItem);
        cancelItem.setEnabled(false);
        cancelItem.addActionListener(event -> textReader.cancel(true));
        pack();
    }

    /**
     * 用来存放进度数据的类
     */
    private class ProgressData {
        public int number; //行号
        public String line; //当前行的文本内容

        public ProgressData(int number, String line) {
            this.number = number;
            this.line = line;
        }
    }

    // TODO: SwingWorker
    //  1. JAVA界面库提供了SwingWorker辅助类来实现执行耗时任务的同时又能保证用户界面仍能响应用户的功能
    //  2. SwingWorker实现了RunnableFuture接口，泛型的第一个类型就是接口的泛型类型，产生类型为StringBuilder的结果和类型为ProgressData
    //  的进度数据
    private class TextReader extends SwingWorker<StringBuilder, ProgressData> {
        private File file;
        private StringBuilder text = new StringBuilder(); //这个对象是用来作为doInBackground()返回值的

        public TextReader(File file) {
            this.file = file;
        }

        /**
         * 读取一个文件，一次读取一行，读取每一行之后调用publish()发布行号和当前行的文本
         * doInBackground()在工作线程执行，完成后返回结果
         * @return StringBuilder对象
         */
        public StringBuilder doInBackground() throws IOException, InterruptedException {
            int lineNumber = 0;
            try (var in = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8)) {
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    lineNumber++;
                    text.append(line).append("\n");
                    var data = new ProgressData(lineNumber, line);
                    // TODO: 不时地调用publish来报告工作进度，向事件分配线程发送数据
                    publish(data);
                    Thread.sleep(1); // 测试cancel()方法，故意延长读取时间
                }
            }
            return text; //返回结果
        }

        /**
         * 1. 在doInBackground()调用publish()会在事件分配线程中调用process()方法，该方法用来实时更新UI界面
         * 2. 为了提高效率，该方法接收一个List集合，其中包含所有中间结果。使用过的数据会移除集合，不会重复遍历的
         */
        public void process(List<ProgressData> data) {
            if (isCancelled()) return;
            var builder = new StringBuilder();
            statusLine.setText("" + data.get(data.size() - 1).number);
            for (ProgressData d : data) builder.append(d.line).append("\n");
            textArea.append(builder.toString());
        }

        /**
         * doInBackground方法完成后该方法在事件派发线程上执行，这里完成UI的最后更新
         * 可以在此方法的实现内部查询状态，以确定此任务的结果或此任务是否已被取消
         */
        public void done() {
            try {
                // TODO: get()
                //  1. 这里调用的是Future的get()方法，由于该方法会阻塞，所以一般都在工作完成之后再调用，一般都在done()方法中调用
                //  2. 这里不调用也可以，这里对UI做最后的更新
                StringBuilder result = get(); //这里获取的 result 和 text 是同一个对象

                textArea.setText(result.toString());
                statusLine.setText("Done");
            } catch (InterruptedException ex) {
            } catch (CancellationException ex) { // TODO: 调用cancel()方法取消工作后调用get()方法会抛出该CancellationException异常
                textArea.setText("");
                statusLine.setText("Cancelled");
            } catch (ExecutionException ex) {
                statusLine.setText("" + ex.getCause());
            }

            cancelItem.setEnabled(false);
            openItem.setEnabled(true);
        }
    }

}
