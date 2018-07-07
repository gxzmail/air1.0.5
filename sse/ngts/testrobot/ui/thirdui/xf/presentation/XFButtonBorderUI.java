package sse.ngts.testrobot.ui.thirdui.xf.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;

public class XFButtonBorderUI extends AbstractBorder implements UIResource
{
    private Color backColor = new Color(204, 204, 204);

    protected Insets borderInsets = new Insets(5, 12, 5, 12);

    private void drawActiveButtonBorder(Graphics g, int x, int y, int w, int h)
    {
        drawFlush3DBorder(g, x, y, w, h);
        g.setColor(backColor);
        g.drawLine(x + 1, y + 1, x + 1, h - 3);
        g.drawLine(x + 1, y + 1, w - 3, x + 1);
        g.setColor(Color.green);

        g.drawLine(x + 2, h - 2, w - 2, h - 2);
        g.drawLine(w - 2, y + 2, w - 2, h - 2);
    }

    private void drawButtonBorder(Graphics g, int x, int y, int w, int h, boolean active)
    {
        if (active)
        {
            drawActiveButtonBorder(g, x, y, w, h);
        } else
        {
            drawFlush3DBorder(g, x, y, w, h);
        }
    }

    private void drawDefaultButtonBorder(Graphics g, int x, int y, int w, int h, boolean active)
    {
        drawButtonBorder(g, x + 1, y + 1, w - 1, h - 1, active);
        g.setColor(backColor.darker().darker());
        g.drawRect(x, y, w - 3, h - 3);
        g.drawLine(w - 2, 0, w - 2, 0);
        g.drawLine(0, h - 2, 0, h - 2);
    }

    private void drawDisabledBorder(Graphics g, int x, int y, int w, int h)
    {
        g.translate(x, y);
        g.setColor(backColor.darker());
        g.drawRect(0, 0, w - 1, h - 1);
    }

    private void drawFlush3DBorder(Graphics g, int x, int y, int w, int h)
    {
        g.translate(x, y);
        g.setColor(backColor.brighter());
        g.drawRect(1, 1, w - 2, h - 2);
        g.setColor(backColor.darker().darker());
        g.drawRect(0, 0, w - 2, h - 2);
        g.setColor(Color.green);
        g.translate(-x, -y);
    }

    private void drawPressed3DBorder(Graphics g, int x, int y, int w, int h)
    {
        g.translate(x, y);
        drawFlush3DBorder(g, 0, 0, w, h);
        g.setColor(backColor.darker());
        g.drawLine(1, 1, 1, h - 2);
        g.drawLine(1, 1, w - 2, 1);
        g.translate(-x, -y);
    }

    @Override
    public Insets getBorderInsets(Component c)
    {
        return borderInsets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
    {
        JButton button = (JButton) c;
        ButtonModel model = button.getModel();

        if (model.isEnabled())
        {
            if (model.isPressed() && model.isArmed())
            {
                drawPressed3DBorder(g, x, y, w, h);
            } else
            {
                if (button.isDefaultButton())
                {
                    drawDefaultButtonBorder(g, x, y, w, h, button.hasFocus() && false);
                } else
                {
                    drawButtonBorder(g, x, y, w, h, button.hasFocus() && false);
                }
            }
        } else
        {
            drawDisabledBorder(g, x, y, w - 1, h - 1);
        }
    }
}
