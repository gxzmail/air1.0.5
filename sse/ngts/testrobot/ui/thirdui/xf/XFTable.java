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
 * DESCRIPTIVE NAME : ��EZMAN�н��õ���Tableģ�͵ĳ�����
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

    // ά��TableBO���б�,ÿһ��BO��Ӧһ���еĶ���
    private ArrayList<XFTableBO> boList;

    private DefaultTableModel tableModel;

    private Color clickColumnColor = new Color(255, 186, 117);

    /**************************************************************************
     * ��ʼ��Tableģ��
     * @param columnName
     *        ����������
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
     * ����һ��
     * @param bo
     *        TableBO
     **************************************************************************/
    public void addRow(XFTableBO bo)
    {
        tableModel.addRow(bo.getItems());
        boList.add(bo);
    }

    /**************************************************************************
     * ����������
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
     * ����ĳһ�е���ɫ
     * @param row
     *        �ڼ���
     * @param bo
     *        �����滻��BO
     * @return ���س���BO
     **************************************************************************/
    public void changeRowColor(int row)
    {

    }

    /**************************************************************************
     * ���Table
     **************************************************************************/
    public void clearAll()
    {
        for (int i = boList.size() - 1; i >= 0; i--)
        {
            removeRow(i);
        }
    }

    /**************************************************************************
     * �õ�ĳһ�е�BO,��boList�л�ȡ
     * @param columnName
     *        ����������
     * @return ���س���BO
     **************************************************************************/
    public XFTableBO getBO(int row)
    {
        return boList.get(row);
    }

    /**************************************************************************
     * ���ɱ༭
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
     * �޸�ĳһ�е�ֵ
     * @param row
     *        �ڼ���
     * @param bo
     *        �����滻��BO
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
     * ɾ��ĳһ��
     * @param row
     *        ɾ���ڼ���
     **************************************************************************/
    public void removeRow(int row)
    {
        tableModel.removeRow(row);
        boList.remove(row);
    }

    /**************************************************************************
     * �����еĿ��
     * @param width
     *        ��ȵ�����
     **************************************************************************/
    public void setWidth(int width[])
    {
        for (int i = 0; i < tableModel.getColumnCount(); i++)
        {
            this.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
        }
    }
}
