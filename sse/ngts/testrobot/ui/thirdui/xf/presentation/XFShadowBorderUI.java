package sse.ngts.testrobot.ui.thirdui.xf.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class XFShadowBorderUI extends AbstractBorder
{
    int xoff;
    int yoff;
    Insets insets;

    public XFShadowBorderUI(int x, int y)
    {
        this.xoff = x;
        this.yoff = y;
        insets = new Insets(0, 0, xoff, yoff);
    }

    @Override
    public Insets getBorderInsets(Component c)
    {
        return insets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        g.setColor(Color.black);
        g.translate(x, y);
        g.fillRect(width - xoff, yoff, xoff, height - yoff);
        g.fillRect(xoff, height - yoff, width - xoff, yoff);
        g.translate(-x, -y);
    }

}
