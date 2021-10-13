package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CreateView{
    private ColoredJRadioButton[] radioButtons = new ColoredJRadioButton[200];
    public JPanel createViewPanel ;
    private JPanel radioButtonsPanel;
    public JButton saveViewButton;
    private JComboBox pickToolComboBox;
    private JComboBox pickColorComboBox;
    private JButton clearAllButton;
    private JTextField viewNameTextField;
    private String viewName;
    public ArrayList<Integer> pickedRadioButtons;
    public ArrayList<Integer> pickedRadioButtonsColors;
    private boolean pressed;
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
       pickedRadioButtons = new ArrayList<Integer>();
       pickedRadioButtonsColors = new ArrayList<Integer>();

        for(int i = 0; i< radioButtons.length; i++){
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
                    drawRadioButton(rb, true);
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
                    drawRadioButton(rb, pressed);

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
                        pickedRadioButtons.clear();
                        pickedRadioButtonsColors.clear();
                    }
                }
            }
        });
    }

    void setViewTable(){
        String columns[] = new String[]{"led_number","led_color"};
        viewName = viewNameTextField.getText();

        db.setDB();
       try{db.createView(viewName);}catch(Exception ex){};
       pickedRadioButtons.forEach((ledNumber) ->{
            int index = pickedRadioButtons.indexOf(ledNumber);
            try{db.insert( viewName, columns, new String[]{
                    Integer.toString(ledNumber),
                    pickedRadioButtonsColors.get(index).toString()}
            );}catch(Exception ex){}
        });
        db.closeConnection();
    }



    void drawRadioButton(ColoredJRadioButton rb, boolean pressed){
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
                }
            }
        }
    }
    void saveView(){
        for(int i = 0; i< radioButtons.length; i++){
            if(radioButtons[i].isSelected()){
                pickedRadioButtons.add(i);
                LedColor color = radioButtons[i].getColor();
                pickedRadioButtonsColors.add(color.code);
            }
        }
        setViewTable();
        pickedRadioButtons.clear();
        pickedRadioButtonsColors.clear();
    }

    LedColor pickColor(String pickedColorName){
        LedColor color = new LedColor(Color.black, 1);
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
        return color;

    }



}
