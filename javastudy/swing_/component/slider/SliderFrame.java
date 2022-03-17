package javastudy.swing_.component.slider;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

/**
 * 展示很多样式滑动条的窗口
 */
public class SliderFrame extends JFrame {
    private JPanel sliderPanel;
    private JTextField textField;
    private ChangeListener listener;

    public SliderFrame() {
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridBagLayout());

        // 所有滑动条共有的监听器
        listener = event -> {
            JSlider source = (JSlider) event.getSource();
            textField.setText("" + source.getValue());
        };

        // 1. 普通滑动条
        var slider = new JSlider();
        addSlider(slider, "Plain");

        // 2. 带有大刻度标记和小刻度标记的滑动条
        slider = new JSlider();
        // TODO 显示刻度标记
        slider.setPaintTicks(true);
        // TODO 每20个单位(指滑动条指)显示一个大刻度标记
        slider.setMajorTickSpacing(20);
        // TODO 每5单位显示一个小刻度标记
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Ticks");

        // 3. 滑动块强制对齐滑动刻度
        slider = new JSlider();
        slider.setPaintTicks(true);
        // TODO 强制对齐刻度，当然不显示刻度条肯定没有效果
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Snap to ticks");

        // 4. 无轨道滑动条
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        // TODO 隐藏轨迹
        slider.setPaintTrack(false);
        addSlider(slider, "No track");

        // 5. 逆向滑动条
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        // TODO 反转
        slider.setInverted(true);
        addSlider(slider, "Inverted");

        // 6. 有数字标签的滑块
        slider = new JSlider();
        slider.setPaintTicks(true);
        // TODO 绘制数字标签
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Labels");

        // 7. 字母标签的滑块
        slider = new JSlider();
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        // TODO 首先需要填充一个键为Integer，值为Component的散列表，然后通过setLabelTable()添加，通常会使用JLabel对象作为值
        var labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("A"));
        labelTable.put(20, new JLabel("B"));
        labelTable.put(40, new JLabel("C"));
        labelTable.put(60, new JLabel("D"));
        labelTable.put(80, new JLabel("E"));
        labelTable.put(100, new JLabel("F"));
        slider.setLabelTable(labelTable);
        addSlider(slider, "Custom labels");

        // 8. 图标作为刻度的滑块
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(20);
        labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel(new ImageIcon("nine.gif"))); //图片资源没有拷贝过来，所以是看不到图标的
        labelTable.put(20, new JLabel(new ImageIcon("ten.gif")));
        labelTable.put(40, new JLabel(new ImageIcon("jack.gif")));
        labelTable.put(60, new JLabel(new ImageIcon("queen.gif")));
        labelTable.put(80, new JLabel(new ImageIcon("king.gif")));
        labelTable.put(100, new JLabel(new ImageIcon("ace.gif")));
        slider.setLabelTable(labelTable);
        addSlider(slider, "Icon labels");

        textField = new JTextField();
        add(sliderPanel, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
        pack();
    }

    /**
     * 把滑动条添加进面板并设置监听器
     *
     * @param slider      the slider
     * @param description the slider description
     */
    public void addSlider(JSlider slider, String description) {
        slider.addChangeListener(listener);
        var panel = new JPanel();
        panel.add(slider);
        panel.add(new JLabel(description));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); //设置水平对齐方式
        var gbc = new GridBagConstraints();
        gbc.gridy = sliderPanel.getComponentCount();
        gbc.anchor = GridBagConstraints.WEST;
        sliderPanel.add(panel, gbc);
    }
}

/**
 * @author Cay Horstmann
 * @version 1.16 2018-04-10
 */
class SliderTest02 {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new SliderFrame();
            frame.setTitle("不同样式的滑动条刻度");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}