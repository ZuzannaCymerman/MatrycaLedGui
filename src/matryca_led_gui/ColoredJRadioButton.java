package matryca_led_gui;
import java.awt.Color;
import javax.swing.*;

public class ColoredJRadioButton extends JRadioButton {
    private LedColor instanceColor;

    ColoredJRadioButton(){};

    public LedColor getColor(){
        return instanceColor;
    }

    public void setColor(LedColor color) {
        ColorIcon colorIcon = new ColorIcon(18, 18, color);
        setIcon(colorIcon);
        instanceColor = color;
    }

}


