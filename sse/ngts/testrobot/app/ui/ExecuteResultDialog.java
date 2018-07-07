package sse.ngts.testrobot.app.ui;

import java.awt.Dialog;

import javax.swing.JOptionPane;

public class ExecuteResultDialog
{
    private final static int width = 300;
    private final static int height = 200;

    public static void viewError(String str, String title)
    {
        JOptionPane msgPanel = new MsgPanel();
        msgPanel.setMessage("<html>" + str + "</html>");
        msgPanel.setMessageType(JOptionPane.ERROR_MESSAGE);
        Dialog dialog = msgPanel.createDialog(null, title);
        dialog.setSize(width, height);
        dialog.setVisible(true);
    }

    public static void viewSuccess(String str, String title)
    {
        JOptionPane msgPanel = new MsgPanel();
        msgPanel.setMessage("<html>" + str + "</html>");
        msgPanel.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        Dialog dialog = msgPanel.createDialog(null, title);
        dialog.setSize(width, height);
        dialog.setVisible(true);
    }

}

class MsgPanel extends JOptionPane
{
    public MsgPanel()
    {

    }

    @Override
    public int getMaxCharactersPerLineCount()
    {
        return 100;
    }

}
