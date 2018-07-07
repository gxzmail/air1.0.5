package sse.ngts.testrobot.ui.background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackGroundPanel extends JPanel
{
    private String drawString;

    public BackGroundPanel(Image image)
    {
    }

    public BackGroundPanel(Image image, String drawString)
    {
        this.drawString = drawString;
    }

    public BackGroundPanel(String drawString)
    {
        this.drawString = drawString;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 50));
        g.drawString(drawString, this.getWidth() / 2, this.getHeight() / 2);
    }

}
