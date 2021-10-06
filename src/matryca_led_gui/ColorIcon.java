package matryca_led_gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;

public class ColorIcon implements Icon
{
    private int iWidth;
    private int iHeight;

    private Color  color;
    private Color  border;
    private Insets insets;

    //---------------------------------------------------------------------------

    public ColorIcon()
    {
        this(32, 16);
    }

    //---------------------------------------------------------------------------

    public ColorIcon(int width, int height)
    {
        this(width, height, Color.black);
    }

    //---------------------------------------------------------------------------

    public ColorIcon(int width, int height, Color c)
    {
        iWidth  = width;
        iHeight = height;

        color   = c;
        border  = Color.black;
        insets  = new Insets(0,0,0,0);
    }

    //---------------------------------------------------------------------------

    public void setColor(Color c)
    {
        color = c;
    }

    //---------------------------------------------------------------------------

    public Color getColor()
    {
        return color;
    }

    //---------------------------------------------------------------------------

    public void setBorderColor(Color c)
    {
        border = c;
    }

    //---------------------------------------------------------------------------
    //---
    //--- Icon interface methods
    //---
    //---------------------------------------------------------------------------

    public int getIconWidth()
    {
        return iWidth;
    }

    //---------------------------------------------------------------------------

    public int getIconHeight()
    {
        return iHeight;
    }

    //---------------------------------------------------------------------------

    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        g.setColor(border);
        g.drawOval(x,y,iWidth, iHeight);

        g.setColor(color);
        g.fillOval(x,y, iWidth, iHeight);
    }
}