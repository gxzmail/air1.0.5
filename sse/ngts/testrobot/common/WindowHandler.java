package sse.ngts.testrobot.common;

import java.awt.BorderLayout;
import java.io.OutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WindowHandler extends StreamHandler
{
    private JDialog dialog;

    public WindowHandler()
    {
        dialog = new JDialog();
        dialog.setTitle("生成执行手册");
        final JTextArea output = new JTextArea("生成执行手册执行详情如下所示：\n");
        output.setEditable(false);
        dialog.setSize(600, 500);
        dialog.add(new JScrollPane(output), BorderLayout.CENTER);

        setOutputStream(new OutputStream()
        {
            @Override
            public void write(byte[] b, int off, int len)
            {
                output.append(new String(b, off, len));
            };

            @Override
            public void write(int b)
            {
            }

        });

        dialog.setFocusableWindowState(true);
        dialog.setVisible(true);
    }

    public JDialog getDialog()
    {
        return dialog;
    }

    @Override
    public void publish(LogRecord record)
    {
        if (!dialog.isVisible())
            return;
        super.publish(record);
        flush();
    }

}
