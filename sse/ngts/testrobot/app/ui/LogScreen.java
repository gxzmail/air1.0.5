package sse.ngts.testrobot.app.ui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogScreen extends JDialog
{
    private JTextArea txtArea;

    private static LogScreen ui = null;

    public static final int ERROR = 1;

    public static final int ERR_NOT_FOUND_ACT = 1;
    public static final int ERR_GRAMMAR_WRONG = 2;
    public static final int ERR_NOT_FOUND_FILE = 3;
    public static final int ERR_NOT_FOUND_CASE_SHEET = 4;
    public static final int ERR_DUP_FOUND_CASE = 5;
    public static final int ERR_FORMAT_SHEET = 6;

    public static LogScreen Log()
    {
        if (ui == null)
        {
            ui = new LogScreen();
        }
        return ui;
    }

    private LogScreen()
    {
        this.setVisible(false);
        this.setTitle("日志分析");
        JPanel panel = new JPanel(new BorderLayout());

        txtArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(txtArea);
        panel.add(scroll, BorderLayout.CENTER);
        this.getContentPane().add(panel);

        setSize(600, 200);
        setLocation(200, 200);

    }

    public void hideUI()
    {
        this.setVisible(false);
    }

    public void showUI()
    {
        this.setVisible(true);
    }

    public void writeLog(int type, int infotype, String msg)
    {
        if (type == ERROR)
        {
            switch (infotype)
            {
                case ERR_NOT_FOUND_ACT:
                    txtArea.append("[错误]" + "[类型:没有找到对应用例指令 ]" + " " + msg + "\n");
                    break;
                case ERR_GRAMMAR_WRONG:
                    txtArea.append("[错误]" + "[类型:指令语法错误         ]" + " " + msg + "\n");
                    break;
                case ERR_NOT_FOUND_FILE:
                    txtArea.append("[错误]" + "[类型:脚本文件未找到      ]" + " " + msg + "\n");
                    break;
                case ERR_NOT_FOUND_CASE_SHEET:
                    txtArea.append("[错误]" + "[类型:未包含'用例'表      ]" + " " + msg + "\n");
                    break;
                case ERR_DUP_FOUND_CASE:
                    txtArea.append("[错误]" + "[类型:用例中有重复编号     ]" + " " + msg + "\n");
                    break;
                case ERR_FORMAT_SHEET:
                    txtArea.append("[错误]" + "[类型:用例中格式错误       ]" + " " + msg + "\n");
                    break;

            }
        }

    }

    public void writeLog(int type, String msg)
    {
        if (type == ERROR)
        {
            txtArea.append("[错误]" + msg + "\n");
        }

    }

}
