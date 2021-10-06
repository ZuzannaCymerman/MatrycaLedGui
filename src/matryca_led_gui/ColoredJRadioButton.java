package matryca_led_gui;
import java.awt.Color;
import javax.swing.*;

public class ColoredJRadioButton extends JRadioButton {
    private Color instanceColor;
    private int instanceNumber;

    ColoredJRadioButton(){};

    public Color getColor(){
        return instanceColor;
    }

    public void setColor(Color color){
        ColorIcon colorIcon = new ColorIcon(18,18);
        colorIcon.setBorderColor(color);
        colorIcon.setColor(color);
        setIcon(colorIcon);
        instanceColor = color;
    }

    public void setNumber(int number){
        instanceNumber = number;
    }

    public int getNumber(){
        return instanceNumber;
    }

}


