package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CreateView{
    public ColoredJRadioButton[] radioButtons = new ColoredJRadioButton[200];
    public JPanel createViewPanel ;
    private JPanel radioButtonsPanel;
    public JButton saveViewButton;
    private JComboBox pickToolComboBox;
    private JComboBox pickColorComboBox;
    private JButton clearAllButton;
    public JTextField viewNameTextField;
    public JSlider brightnessSlider;
    public JLabel validator;
    private JPanel panel;
    private String viewName;
    public int[] pickedRadioButtonsColors;
    private boolean pressed;
    private boolean isSelectedOnEntered;
    private Database db = new Database();

    CreateView(){
        initializeJRadioButtons();
        setClearAllButton();
    }

    private void initializeJRadioButtons(){
       pickedRadioButtonsColors = new int[200];

        for(int i = 0; i< 200; i++){
            radioButtons[i] = new ColoredJRadioButton();
            radioButtons[i].setActionCommand(Integer.toString(i));
            radioButtons[i].setText("");
            radioButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pressed = true;
                    Object obj = e.getSource();
                    ColoredJRadioButton rb = (ColoredJRadioButton) obj;
                    drawRadioButtononPressed(rb, pressed);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    pressed = false;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Object obj = e.getSource();
                    ColoredJRadioButton rb = (ColoredJRadioButton) obj;
                    drawRadioButtononPressed(rb, pressed);
                }

                @Override
                public void mouseDragged(MouseEvent e) {

                }
            });
            radioButtonsPanel.add(radioButtons[i]);
        }

    }

    void setClearAllButton() {
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i< radioButtons.length; i++){
                    if(radioButtons[i].isSelected()){
                        radioButtons[i].doClick();
                        radioButtons[i].setIcon(null);
                       Arrays.fill(pickedRadioButtonsColors, 0);
                    }
                }
            }
        });
    }

    void setViewTable(){
        String columnsWithoutBrightness[] = new String[]{"led_number","led_color"};
        String columnsWithBrightness[] = new String[]{"led_number","led_color", "led_brightness"};
        viewName = viewNameTextField.getText();
        int brightness  = brightnessSlider.getValue();
        try{db.createView(viewName);}catch(Exception ex){};

        try{db.insert( viewName, columnsWithBrightness, new int[]{
                0,
                pickedRadioButtonsColors[0],
                brightness}
        );}catch(Exception ex){}

        for (int i = 1;i<200;i++){
           try{db.insert( viewName, columnsWithoutBrightness, new int[]{
                   i,
                   pickedRadioButtonsColors[i]}
           );}catch(Exception ex){}
        }
    }



    void drawRadioButtononPressed(ColoredJRadioButton rb, boolean pressed){
        if(pickToolComboBox.getSelectedItem() == "Rysuj") {
            if(pressed) {
                if (!rb.isSelected()) {
                    rb.doClick();
                    LedColor color = pickColor(pickColorComboBox.getSelectedItem().toString());
                    rb.setColor(color);
                }
            }
        }else{
            if(pressed) {
                if (rb.isSelected()) {
                    rb.doClick();
                    rb.setIcon(null);
                    rb.setNullColor();
                }
            }
        }
    }

    void saveView(){
        for(int i = 0; i< 200; i++){
            if(radioButtons[i].getColor()!=null){
                LedColor color = radioButtons[i].getColor();
                pickedRadioButtonsColors[i] = color.code;
            }
        }
        setViewTable();
        Arrays.fill(pickedRadioButtonsColors, 0);
    }

    LedColor pickColor(String pickedColorName){
        LedColor color = new LedColor(Color.black, 0);
        switch(pickedColorName){
            case("Czerwony"):
                color = new LedColor(Color.red, 1);
                break;
            case("Zielony"):
                color = new LedColor(Color.green, 2);
                break;
            case("Niebieski"):
                color = new LedColor(Color.blue, 3);
                break;
            case("??????ty"):
                color = new LedColor(Color.yellow, 4);
                break;
            case("Pomara??czowy"):
                color = new LedColor(Color.orange, 5);
                break;
            case("B????kitny"):
                color = new LedColor(Color.cyan, 6);
                break;
            case("Bia??y"):
                color = new LedColor(Color.white, 7);
                break;
            case("Ciemny r????"):
                color = new LedColor(Color.magenta, 8);
                break;
            case("R????owy"):
                color = new LedColor(Color.pink, 9);
                break;
        }
        System.out.println(color.color);
        return color;

    }



}
