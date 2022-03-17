package javastudy.swing_.exercise;

import javax.swing.*;
import java.awt.*;

/**
 * 使用下拉列表选择日期并打印
 */
public class DateChoice extends JFrame {
    public DateChoice() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 100, 300, 100);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        // 直接使用getContentPane()获取的主容器没有border方法，所以这里创建了一个JPanel对象
        JPanel container = new JPanel();
        setContentPane(container);
        // Container container = getContentPane();

        JComboBox<Integer> year = new JComboBox<>();
        DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<>();
        for (int i = 1900; i < 2022; i++) {
            yearModel.addElement(i);
        }
        year.setModel(yearModel);

        JComboBox<Integer> month = new JComboBox<>();
        DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<>();
        for (int i = 1; i < 13; i++) {
            monthModel.addElement(i);
        }
        month.setModel(monthModel);

        JComboBox<Integer> day = new JComboBox<>();
        DefaultComboBoxModel<Integer> dayModel = new DefaultComboBoxModel<>();
        for (int i = 1; i <= 31; i++) {
            dayModel.addElement(i);
        }
        day.setModel(dayModel);

        // 不知道是修改过年再修改日还是先修改月再修改日，所以这两个监听器都得有
        year.addActionListener(e -> {
            if (monthModel.getElementAt(month.getSelectedIndex()) == 2) {
                dayModel.removeElement(30);
                dayModel.removeElement(31);
                if (yearModel.getElementAt(year.getSelectedIndex()) % 4 != 0)
                    dayModel.removeElement(29); //平年只有28天
            }
        });
        month.addActionListener(e -> {
            int ms = monthModel.getElementAt(month.getSelectedIndex());
            if (ms == 2) {
                dayModel.removeElement(30);
                dayModel.removeElement(31);
                if (yearModel.getElementAt(year.getSelectedIndex()) % 4 != 0)
                    dayModel.removeElement(29); //平年只有28天
            } else{ //除了2月以外的月份
                if (dayModel.getIndexOf(29)==-1)
                    dayModel.addElement(29);
                if (dayModel.getIndexOf(30)==-1)
                    dayModel.addElement(30);
                if ((ms == 1 || ms == 3 || ms == 5 || ms == 7 || ms == 8 || ms == 10 || ms == 12)) {
                    if (dayModel.getIndexOf(31) == -1)
                        dayModel.addElement(31);
                }else {
                    dayModel.removeElement(31);
                }
            }
        });

        container.add(year);
        container.add(month);
        container.add(day);

        JButton submit = new JButton("确定");
        submit.addActionListener(e -> {
            System.out.println("你的生日是" + year.getSelectedItem() + "年" + month.getSelectedItem() + "月" + day.getSelectedItem() + "日");
        });
        container.add(submit);
        container.setBorder(BorderFactory.createTitledBorder("请选择你的出生日期"));
    }

    public static void main(String[] args) {
        new DateChoice().setVisible(true);

    }
}

