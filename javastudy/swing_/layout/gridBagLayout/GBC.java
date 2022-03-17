package javastudy.swing_.layout.gridBagLayout;

import java.awt.*;

/**
 * 帮助类，简化 GridBagConstraints 类的使用
 * 两大好处：<br>
 * 1. 使用字段可以简化为 GBC.XXX
 * 2. setXXX() 可以进行链式调用
 */
public class GBC extends GridBagConstraints {
    /**
     * 指定单元格的起始行和列
     */
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * 指定单元格的起始行和列，还有行和列的范围
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    public GBC(int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        this(gridx, gridy, gridwidth, gridheight);
        this.anchor = anchor;
        this.fill = fill;
    }

    public GBC setLocation(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
        return this;
    }

    public GBC setSize(int gridwidth, int gridheight) {
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
        return this;
    }

    /**
     * 设置单元格扩大的容量上限，默认为0
     */
    public GBC setWeigh(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    /**
     * 设置组件在单元格内的对齐方式，默认为CENTER
     */
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * 设置组件在单元格的填充行为，默认为none
     */
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    /**
     * 设置组件的外边距。默认不填充
     *
     * @param distance 外边距的大小
     * @return GridBagConstraints对象引用
     */
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    /**
     * 设置组件的内部填充，这两个值会加到组件的最小宽度和最小高度上，保证组件不会收缩至其最小尺寸之下。默认为0
     */
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }

}
