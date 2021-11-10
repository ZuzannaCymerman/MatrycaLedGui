package matryca_led_gui;
import java.awt.Color;
import javax.swing.*;

public class ColoredJRadioButton extends JRadioButton {
    private LedColor instanceColor;

    ColoredJRadioButton(){
        Color bg = new Color(163,166,168);
        setBackground(bg);
    };

    public LedColor getColor(){
        return instanceColor;
    }

    public void setColor(LedColor color) {
        ColorIcon colorIcon = new ColorIcon(13, 13, color);
        setIcon(colorIcon);
        instanceColor = color;
    }

}


