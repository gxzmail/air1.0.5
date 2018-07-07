package sse.ngts.testrobot.ui.thirdui.xf.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class XFButtonUI extends MetalButtonUI
{
    public static ComponentUI createUI(JComponent c)
    {
        return new XFButtonUI();
    }

    private Color backColor = new Color(204, 204, 204);

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b)
    {
        if (b.isContentAreaFilled())
        {
            Dimension size = b.getSize();

            g.setColor(backColor.darker());
            g.fillRect(0, 0, size.width, size.height);
        }
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect)
    {
        g.setColor(getFocusColor());
        g.drawRect(viewRect.x - 7, viewRect.y - 1, viewRect.width + 13, viewRect.height + 1);
    }
}
