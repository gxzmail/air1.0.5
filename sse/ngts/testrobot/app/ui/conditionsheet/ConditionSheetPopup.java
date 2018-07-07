package sse.ngts.testrobot.app.ui.conditionsheet;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import sse.ngts.testrobot.app.execute.process.ActionController;
import sse.ngts.testrobot.app.model.ScriptCase;
import sse.ngts.testrobot.ui.thirdui.xf.presentation.XFPopupMenuUI;

public class ConditionSheetPopup extends JPanel implements ActionListener, PopupMenuListener, MouseListener
{
    private JMenuItem itemCopyContent;
    private JMenuItem itemMark;
    private JMenuItem itemMarkBreakPoint;
    private JMenuItem itemMarkBreakPointSet;
    private JMenuItem itemMarkBreakPointCancel;
    private JMenuItem itemMarkCurrStepRun;
    private JMenuItem itemMarkStart;
    private JMenuItem itemMarkFinish;
    private JMenuItem itemMarkUnFinish;
    private JMenuItem itemMarkFailed;

    public JPopupMenu popup;

    private JComponent parent;

    private ArrayList<ActionController> selectedControllers;

    private boolean startEnable = true;
    private ConditionSheetController controller = null;

    public ConditionSheetPopup(Boolean startEnable, ConditionSheetController controller)
    {
        this.startEnable = startEnable;
        this.controller = controller;
        createUI();
        if (controller.getStopRunRow() != -1)
        {
            itemMarkBreakPointCancel.setEnabled(true);
        } else
        {
            itemMarkBreakPointCancel.setEnabled(false);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    }

    public void createUI()
    {
        popup = new JPopupMenu();
        popup.setUI(new XFPopupMenuUI());

        itemMarkFinish = new JMenuItem("���:ִ�гɹ�      1");
        itemMarkFailed = new JMenuItem("���:ִ��ʧ��      2");
        itemMarkUnFinish = new JMenuItem("���:δִ��        0");
        itemCopyContent = new JMenuItem("����ִ������");
        itemMark = new JMenu("״̬���");
        itemMark.add(itemMarkUnFinish);
        itemMark.add(itemMarkFinish);
        itemMark.add(itemMarkFailed);

        itemMarkBreakPointSet = new JMenuItem("��Ϊ�ϵ�");
        itemMarkBreakPointCancel = new JMenuItem("ȡ���ϵ�");

        itemMarkBreakPoint = new JMenu("�ϵ�����(F7)");
        itemMarkBreakPoint.add(itemMarkBreakPointSet);
        itemMarkBreakPoint.add(itemMarkBreakPointCancel);
        itemMarkCurrStepRun = new JMenuItem("ִ�е�ǰ����(F6)");
        itemMarkStart = new JMenuItem("�Ӵ˿�ʼִ��(F8)");

        itemMarkCurrStepRun.setEnabled(startEnable);
        itemMarkStart.setEnabled(startEnable);

        popup.add(itemCopyContent);
        popup.add(itemMark);
        popup.add(itemMarkStart);
        popup.add(itemMarkBreakPoint

                );
        popup.add(itemMarkCurrStepRun);
        popup.setBorder(new BevelBorder(BevelBorder.RAISED));
        popup.addPopupMenuListener(this);

    }

    public void init(JComponent p, ArrayList<ActionController> c)
    {
        this.parent = p;
        this.selectedControllers = c;
        itemMarkFinish.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (!selectedControllers.isEmpty())
                        {
                            int selection = JOptionPane.showConfirmDialog(null, "��Ҫ��������ѡ���Ĳ���Ϊ\"ִ�гɹ�\"�����", "����",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (selection == JOptionPane.CANCEL_OPTION)
                                return;
                            else if (selection == JOptionPane.OK_OPTION)
                            {
                                controller.changeStepStatus(1);
                            }
                        }
                    }
                }.start();
                parent.repaint();

            }
        });
        itemMarkUnFinish.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (!selectedControllers.isEmpty())
                        {
                            int selection = JOptionPane.showConfirmDialog(null, "��Ҫ��������ѡ���Ĳ���Ϊ\"δִ��\"�����", "����",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (selection == JOptionPane.CANCEL_OPTION)
                                return;
                            else if (selection == JOptionPane.OK_OPTION)
                            {
                                controller.changeStepStatus(0);

                            }
                        }
                    }
                }.start();
                parent.repaint();
            }
        });
        itemMarkFailed.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (!selectedControllers.isEmpty())
                        {
                            int selection = JOptionPane.showConfirmDialog(null, "��Ҫ��������ѡ���Ĳ���Ϊ\"ִ��ʧ��\"�����", "����",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (selection == JOptionPane.CANCEL_OPTION)
                                return;
                            else if (selection == JOptionPane.OK_OPTION)
                            {
                                controller.changeStepStatus(2);
                            }
                        }
                    }
                }.start();
                parent.repaint();
            }
        });
        itemMarkBreakPointCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (selectedControllers.get(0).getApplSheetUIController().getStopRunRow() != -1)
                        {
                            selectedControllers.get(0).getApplSheetUIController().setStopRunStep(-1);
                        }
                    }
                }.start();
                parent.repaint();
            }
        });
        itemMarkBreakPointSet.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        for (ActionController a : selectedControllers)
                        {
                            if (selectedControllers.size() > 1)
                            {
                                break;
                            } else if (selectedControllers.size() < 1)
                            {
                                break;
                            } else
                            {
                                a.getApplSheetUIController().setStopRunStep(
                                        Integer.parseInt(a.getCurrentScript().getFrmCase().getStepsId()) - 1);
                            }
                        }
                    }
                }.start();
                parent.repaint();
            }
        });
        itemMarkStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (controller.isApplSheetWorking())
                        {
                            JOptionPane.showMessageDialog(null, "��ǰ��������ִ���У�����ִ��ѡ�����裡", "warnning",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        controller.setSuspend(false);
                        if (selectedControllers.size() == 0)
                            return;
                        ActionController a = selectedControllers.get(0);
                        a.getApplSheetUIController().execAutoActions(
                                Integer.parseInt(a.getCurrentScript().getFrmCase().getStepsId()) - 1);
                        controller.getUI().setFuncStatus(false);
                        controller.setSuspend(false);
                    }

                }.start();
                parent.repaint();
            }
        });
        itemMarkCurrStepRun.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {

                new Thread()
                {
                    @Override
                    public void run()
                    {
                        if (controller.isApplSheetWorking())
                        {
                            JOptionPane.showMessageDialog(null, "��ǰ��������ִ���У�����ִ��ѡ�����裡", "warnning",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        if (selectedControllers == null)
                            return;
                        ActionController a = selectedControllers.get(0);
                        a.getApplSheetUIController().executSelectStep(
                                Integer.parseInt(a.getCurrentScript().getFrmCase().getStepsId()) - 1);
                        controller.getUI().setFuncStatus(false);
                        controller.setSuspend(false);
                    }
                }.start();
                parent.repaint();
            }
        });
        itemCopyContent.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                for (ActionController a : selectedControllers)
                {
                    if (selectedControllers.size() < 1)
                        return;
                    ScriptCase script = a.getCurrentScript();
                    StringSelection str = new StringSelection(script.getFrmCase().getTestContent());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, str);
                    break;

                }
                parent.repaint();
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e)
    {
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
    {
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e)
    {
    }

    public void show(int x, int y)
    {
        popup.show(parent, x, y);
    }
}
