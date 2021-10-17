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
    private JTextField viewNameTextField;
    private String viewName;
    public int[] pickedRadioButtonsColors;
    private boolean pressed;
    private boolean isSelectedOnEntered;
    private Database db = new Database();
    public LedColor red = new LedColor(Color.red, 1);
    public LedColor green = new LedColor(Color.green, 2);
    public LedColor blue = new LedColor(Color.blue, 3);
    public LedColor yellow = new LedColor(Color.yellow, 4);
    public LedColor orange = new LedColor(Color.orange, 5);
    public LedColor cyan = new LedColor(Color.cyan, 6);
    public LedColor white = new LedColor(Color.white, 7);
    public LedColor magenta = new LedColor(Color.magenta, 8);
    public LedColor pink = new LedColor(Color.pink, 9);

    CreateView(){
        initializeJRadioButtons();
        setClearAllButton();
    }

    private void initializeJRadioButtons(){
       pickedRadioButtonsColors = new int[200];

        for(int i = 0; i< 200; i++){
            radioButtons[i] = new ColoredJRadioButton();
            radioButtons[i].setNumber(i);
            radioButtons[i].setSize(20,20);
            radioButtons[i].setActionCommand(Integer.toString(i));
            radioButtons[i].setText("");
            radioButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object obj = e.getSource();
                    ColoredJRadioButton rb = (ColoredJRadioButton) obj;
                    drawRadioButtonOnClick(rb);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    pressed = true;
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
            radioButtonsPanel.setSize(200,200);
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
        String columns[] = new String[]{"led_number","led_color"};
        viewName = viewNameTextField.getText();
       try{db.createView(viewName);}catch(Exception ex){};
       for (int i = 0;i<200;i++){
           try{db.insert( viewName, columns, new int[]{
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
                    isSelectedOnEntered = true;
                }
            }
        }else{
            if(pressed) {
                if (rb.isSelected()) {
                    rb.doClick();
                    System.out.println(rb.isSelected());
                    rb.setIcon(null);
                }
            }
        }
    }

    void drawRadioButtonOnClick(ColoredJRadioButton rb){
        if(pickToolComboBox.getSelectedItem() == "Rysuj") {
           LedColor color = pickColor(pickColorComboBox.getSelectedItem().toString());
           rb.setColor(color);
           if(isSelectedOnEntered){
               rb.doClick();
               isSelectedOnEntered = false;
           }
        }else{
            rb.setIcon(null);
            if(rb.isSelected()){
                rb.doClick();
            }
        }
        System.out.println(rb.isSelected());
    }
    void saveView(){
        for(int i = 0; i< 200; i++){
            if(radioButtons[i].isSelected()){
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
                color = red;
                break;
            case("Zielony"):
                color = green;
                break;
            case("Niebieski"):
                color = blue;
                break;
            case("Żółty"):
                color = yellow;
                break;
            case("Pomarańczowy"):
                color = orange;
                break;
            case("Błękitny"):
                color = cyan;
                break;
            case("Biały"):
                color = white;
                break;
            case("Ciemny róż"):
                color = magenta;
                break;
            case("Różowy"):
                color = pink;
                break;
        }
        System.out.println(color.color);
        return color;

    }



}
