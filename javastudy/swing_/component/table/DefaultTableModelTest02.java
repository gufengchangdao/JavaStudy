/*
DefaultTableModel的常用方法：
setValueAt()    //修改
getValueAt()    //获取
addRow()        //添加
removeRow()     //删除

表格排序后，不能直接使用 data[getSelectedRow()][0]来获取数据了，因为表格变了，data没有变，以下提供两个方法
获取转换后的选中行的第一列
table.getModel().getValueAt(table.convertRowIndexToModel(table.getSelectedRow()),table.convertColumnIndexToModel(0))
table.getValueAt(table.getSelectedRow(), 0)
*/
package javastudy.swing_.component.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 使用DefaultTableModel类创建表格，对表格内容进行增删改查
 */
public class DefaultTableModelTest02 extends JFrame {
    private JTextField aTextField = new JTextField("", 7);
    private JTextField bTextField = new JTextField("", 7);
    private JTextField cTextField = new JTextField("", 7);

    public DefaultTableModelTest02() throws HeadlessException {
        setTitle("维护表格模型");
        setBounds(300, 300, 520, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        String[][] data = {{"A1", "B1", "C1"}, {"A2", "B2", "C2"}, {"A3", "B3", "C3"}, {"A4", "B4", "C4"},
                {"A5", "B5", "C5"}, {"A6", "B6", "C6"}, {"A7", "B7", "C7"}, {"A8", "B8", "C8"}};
        String[] colName = {"A", "B", "C"};
        DefaultTableModel tableModel = new DefaultTableModel(data, colName) {
            @Override
            public boolean isCellEditable(int row, int column) { //阻止用户双击直接修改表格内容
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setRowSorter(new TableRowSorter<>(tableModel)); //设置表格的排序器
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //设置表格的选择模式为单选，这样表格一次只能选一个了
        // 为表格添加鼠标事件监听器，更新三个文本框内容
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow(); //获取表格所选中的行
                Object cola = tableModel.getValueAt(selectedRow, 0); //获取指定行和指定列的单元格
                Object colb = tableModel.getValueAt(selectedRow, 1);
                Object colc = tableModel.getValueAt(selectedRow, 2);
                aTextField.setText(cola.toString());
                bTextField.setText(colb.toString());
                cTextField.setText(colc.toString());
            }
        });
        container.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(); //JPanel默认使用的是居中对齐的流布局，所以不用设置布局了
        panel.add(new JLabel("A："));
        panel.add(aTextField);
        panel.add(new JLabel("B："));
        panel.add(bTextField);
        panel.add(new JLabel("C："));
        panel.add(cTextField);

        // 添加功能
        JButton addButton = new JButton("添加");
        addButton.addActionListener(e -> {
            String[] rowValues = {aTextField.getText(), bTextField.getText(), cTextField.getText()};
            tableModel.addRow(rowValues); //添加一行数据
        });
        panel.add(addButton);

        // 修改功能
        JButton updateButton = new JButton("修改");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { //如果为-1表示没有选取任何一行
                tableModel.setValueAt(aTextField.getText(), selectedRow, 0); //修改指定单元格的数据
                tableModel.setValueAt(bTextField.getText(), selectedRow, 1);
                tableModel.setValueAt(cTextField.getText(), selectedRow, 2);
            }
        });
        panel.add(updateButton);

        // 删除功能
        JButton delButton = new JButton("删除");
        delButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1)
                tableModel.removeRow(selectedRow); //删除指定行
        });
        panel.add(delButton);

        container.add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new DefaultTableModelTest02().setVisible(true);
    }
}
