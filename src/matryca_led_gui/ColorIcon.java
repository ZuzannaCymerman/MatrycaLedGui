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

    private LedColor color;
    private Color  border;


    //---------------------------------------------------------------------------

    public ColorIcon(int width, int height, LedColor c)
    {
        iWidth  = width;
        iHeight = height;

        color   = c;
        border  = c.color;
    }

    //---------------------------------------------------------------------------

    public void setColor(LedColor c)
    {
        color = c;
    }

    //---------------------------------------------------------------------------

    public LedColor getColor()
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

        g.setColor(color.color);
        g.fillOval(x,y, iWidth, iHeight);
    }
}