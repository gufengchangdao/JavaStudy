/*
JTable
1. 这个表格双击可以编译某一单元格，可以在创建JTable或者JDefaultTableModel时重写isCellEditable()方法来防止直接编辑
2. 表格点击一个单元格会选择一行，可以使用setRowSelectionAllowed()方法设置只能一个单元格一个单元格地选择
3. 表格可以多选，用setSelectionMode()设置只能选择一行


*/
package javastudy.swing_.component.table;

import javax.swing.*;
import java.awt.*;

/**
 * 使用JTable类创建一个多行两列的表格
 */
public class JTableTest extends JFrame {
    public JTableTest() throws HeadlessException {
        setBounds(300,300,240,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[][] data={{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"},{"A6","B6"},{"A7","B7"},{"A8","B8"}};
        String[] colName={"A","B"};
        JTable table = new JTable(data, colName); //传入数据，一个是二维数组，表示数据，一个是一维数组，表示列名
        // JTable table = new JTable(data, colName){    //重写isCellEditable()方法来禁止编辑表格
        //     @Override
        //     public boolean isCellEditable(int row, int column) {
        //         return false;
        //     }
        // };
        // table.setEnabled(false);
        // table.setShowGrid(false); //是否显示网格
        // table.setRowHeight(20);   //设置行高
        // table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //设置只能同时选择一行数据
        // table.setRowSelectionAllowed(false); //设置只能一个单元格一个单元格的选

        // 改变每个单元格的宽度
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //关闭表格自动调整宽度
        // table.getColumnModel().getColumn(0).setPreferredWidth(100); //为第一列设置首选宽度

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
    }

    public static void main(String[] args) {
        new JTableTest().setVisible(true);
    }
}
