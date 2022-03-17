package javastudy.swing_.component.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * 对表格进行一些设置
 * 只能单选、表格不可编辑、文字水平居中显示
 */
public class MyJTable extends JTable {
    public MyJTable(TableModel dm) { //传入表格的数据模型
        super(dm);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 只能单选
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;// 表格不可编辑
    }

    @Override
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
        // 获取单元格渲染对象
        DefaultTableCellRenderer cr = (DefaultTableCellRenderer) super.getDefaultRenderer(columnClass);
        // 表格文字居中显示
        cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        return cr;
    }
}
