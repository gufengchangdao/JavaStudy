package javastudy.swing_.component.list;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * 使用JComboBox类制作一个下拉列表，并显示用户所选内容
 */
public class JComboBoxTest extends JFrame {
    public JComboBoxTest() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300,400,317,147);
        setLayout(null);
        Container container = getContentPane();

        JLabel tip = new JLabel("请选择证件：");
        tip.setBounds(28,14,80,15);
        container.add(tip);

        // TODO 创建JComboBox对象，可以传入数组，vector或者JComboBox对象
        // String[] choices={"身份证","军人证","学生证"};
        // JComboBox<String> comboBox = new JComboBox<>(choices);
        // JComboBox<String> comboBox = new JComboBox<>();      //空的
        // 使用vector有点特殊，创建JComboBox对象时数组要提前赋好值，创建后再赋值就没有效果，但是vector可以先传入再赋值，添加或者删除
        //空的vector传入再赋值的话，点开列表前是一个null值，返回索引为-1，点开后那个空选项就没有了
        Vector<String> choices = new Vector<>();
        JComboBox<String> comboBox = new JComboBox<>(choices);
        choices.add("身份证");
        choices.add("军人证");
        choices.add("学生证");
        // choices.remove(2);  //效果好像和comboBox.removeItem()一样的

        // TODO 常用方法
        comboBox.setBounds(110,14,80,20);
        // 1. 向列表中添加项
        // 如果插入大量内容，addItem()会有些慢，可以创建DefaultComboBoxModel对象，对模型添加Item，再用comboBox.setModel()来设置模型
        // DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        // comboBox.addItem("工作证");
        // comboBox.insertItemAt("工作证",0); //在任何位置插入
        // 2. 得到列表中的项数
        // int count = comboBox.getItemCount();
        // 3. 去除
        // comboBox.removeItem("工作证");
        // comboBox.removeItemAt(0);
        // comboBox.removeAllItems();
        // 4. 确定下拉列表中的字段是否可编辑，这个很有趣，编辑的内容不会保存，切换到其他项就没了，但是使用getSelectedItem()可以得到编辑的内容
        comboBox.setEditable(true);
        container.add(comboBox);

        // 空的标签，用来显示选择结果
        JLabel choice = new JLabel("");
        choice.setBounds(0,57,146,15);
        container.add(choice);

        JButton submit = new JButton("确定");
        submit.setBounds(200,10,67,23);
        submit.addActionListener(e->{
            // 可以到选择的值或者项所在的索引值
            choice.setText("你的选择是第"+(comboBox.getSelectedIndex()+1)+"项的"+comboBox.getSelectedItem());
            // 如果组合框是不可编辑的，可以使用下面的方法获取内容，这个方法的好处是返回值的类型不是Object类型的，而是指定的泛型类型
            // choice.setText("你的选择是第"+(comboBox.getSelectedIndex()+1)+"项的"+comboBox.getItemAt(comboBox.getSelectedIndex()));
        });
        container.add(submit);
    }

    public static void main(String[] args) {
        new JComboBoxTest().setVisible(true);
    }
}
