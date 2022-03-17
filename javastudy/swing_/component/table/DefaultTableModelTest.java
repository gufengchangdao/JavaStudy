/*
DefaultTableModel
1. AbstractTableModel抽象类实现了TableModel接口的大多数方法，DefaultTableModel又继承了AbstractTableModel抽象类，可以对表格数据进行更便捷的操作
*/
package javastudy.swing_.component.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * 使用DefaultTableModel类创建表格数据模型，依此创建一个表格
 */
public class DefaultTableModelTest extends JFrame {
    public DefaultTableModelTest() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 300, 480, 300);

        String[][] data = {{"A1", "B1", "C1"}, {"A2", "B2", "C2"}, {"A3", "B3", "C3"}, {"A4", "B4", "C4"},
                {"A5", "B5", "C5"}, {"A6", "B6", "C6"}, {"A7", "B7", "C7"}, {"A8", "B8", "C8"}};
        String[] colName = {"A", "B","C"};
        // 创建DefaultTableModel对象，并传入数据和列名
        // DefaultTableModel tableModel = new DefaultTableModel(data, colName);
        // 如果想让表格不可编辑，就可以重写DefaultTableModel的isCellEditable()方法
        DefaultTableModel tableModel = new DefaultTableModel(data, colName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        // 为表格设置排序器,单机某一列的列头时,就会把所有数据进行排序
        table.setRowSorter(new TableRowSorter<>(tableModel));
        getContentPane().add(new JScrollPane(table));
    }

    public static void main(String[] args) {
        new DefaultTableModelTest().setVisible(true);
    }
}
