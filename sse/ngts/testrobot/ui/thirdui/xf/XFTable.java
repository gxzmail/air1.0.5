package sse.ngts.testrobot.ui.thirdui.xf;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*******************************************************************************
 * MODULE NAME : AbstractEZTableModel.java
 * DESCRIPTIVE NAME : 在EZMAN中将用到的Table模型的抽象类
 * MODULE DESCRIPTION :
 * MODULE INTERFACES :
 * CALLED MODULES :
 * CALLED BY :
 * FUNCTIONAL DESCRIPTION:
 * =======================
 * ABSTRACT:
 * --------
 *************************************************************************** MODIFICATION HISTORY:
 * Date Prog.
 * DD-MMM-YYYY Init. SIR Modification Description
 * ------------- ------- ----- --------------------
 * 11-JUN-2008 YIWEI 30322 CREATE
 ******************************************************************************/
public abstract class XFTable extends JTable
{

    // 维护TableBO的列表,每一个BO对应一行中的对象
    private ArrayList<XFTableBO> boList;

    private DefaultTableModel tableModel;

    private Color clickColumnColor = new Color(255, 186, 117);

    /**************************************************************************
     * 初始化Table模型
     * @param columnName
     *        列名的数组
     **************************************************************************/
    public XFTable(Object[] columnName)
    {
        tableModel = new DefaultTableModel(columnName, 0);
        this.setModel(tableModel);
        this.getTableHeader().setBackground(clickColumnColor);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        boList = new ArrayList<XFTableBO>();
    }

    /**************************************************************************
     * 新增一行
     * @param bo
     *        TableBO
     **************************************************************************/
    public void addRow(XFTableBO bo)
    {
        tableModel.addRow(bo.getItems());
        boList.add(bo);
    }

    /**************************************************************************
     * 加入排序功能
     **************************************************************************/
    public void addTableSortFunc()
    {

        this.getTableHeader().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                XFTable.this.getTableHeader().columnAtPoint(new Point(e.getX(), e.getY()));
                XFTable.this.getTableHeader().setBackground(Color.lightGray);

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                refreshTableModel();
                XFTable.this.getTableHeader().setBackground(clickColumnColor);
            }
        });
    }

    /**************************************************************************
     * 更改某一行的颜色
     * @param row
     *        第几行
     * @param bo
     *        将被替换的BO
     * @return 返回抽象BO
     **************************************************************************/
    public void changeRowColor(int row)
    {

    }

    /**************************************************************************
     * 清空Table
     **************************************************************************/
    public void clearAll()
    {
        for (int i = boList.size() - 1; i >= 0; i--)
        {
            removeRow(i);
        }
    }

    /**************************************************************************
     * 得到某一行的BO,从boList中获取
     * @param columnName
     *        列名的数组
     * @return 返回抽象BO
     **************************************************************************/
    public XFTableBO getBO(int row)
    {
        return boList.get(row);
    }

    /**************************************************************************
     * 不可编辑
     **************************************************************************/
    @Override
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }

    public void modifyCellValue(int row, int col, Object value)
    {
        tableModel.setValueAt(value, row, col);
    }

    /**************************************************************************
     * 修改某一行的值
     * @param row
     *        第几行
     * @param bo
     *        将被替换的BO
     **************************************************************************/
    public void modifyRow(int row, XFTableBO bo)
    {
        tableModel.removeRow(row);
        tableModel.addRow(bo.getItems());
        tableModel.moveRow(getRowCount() - 1, getRowCount() - 1, row);
    }

    public void refreshTableModel()
    {
        int row = tableModel.getRowCount();
        for (int i = row - 1; i >= 0; i--)
        {
            tableModel.removeRow(i);

        }
        for (int a = 0; a < boList.size(); a++)
        {
            tableModel.addRow(boList.get(a).getItems());
        }

    }

    /**************************************************************************
     * 删除某一行
     * @param row
     *        删除第几行
     **************************************************************************/
    public void removeRow(int row)
    {
        tableModel.removeRow(row);
        boList.remove(row);
    }

    /**************************************************************************
     * 设置列的宽度
     * @param width
     *        宽度的数组
     **************************************************************************/
    public void setWidth(int width[])
    {
        for (int i = 0; i < tableModel.getColumnCount(); i++)
        {
            this.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
        }
    }
}
