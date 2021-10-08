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
    private JButton saveViewButton;
    private JComboBox pickToolComboBox;
    private JComboBox pickColorComboBox;
    private JButton clearAllButton;
    private JTextField viewNameTextField;
    private String viewName;
    public ArrayList<Integer> pickedRadioButtons;
    public ArrayList<String> pickedRadioButtonsColors;
    private boolean pressed;
    private Database db = new Database();

    CreateView(){
        initializeJRadioButtons();
        setClearAllButton();
    }

    private void initializeJRadioButtons(){
       pickedRadioButtons = new ArrayList<Integer>();
       pickedRadioButtonsColors = new ArrayList<String>();

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
                    rb.doClick();
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

        saveViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i< radioButtons.length; i++){
                    if(radioButtons[i].isSelected()){
                            pickedRadioButtons.add(i);
                            Color color = radioButtons[i].getColor();
                            String hex = "#"+Integer.toHexString(color.getRGB()).substring(2);
                            pickedRadioButtonsColors.add(hex);
                    }
                }
                setViewTable();
                pickedRadioButtons.clear();
                pickedRadioButtonsColors.clear();

            }
        });

    }

    Color pickColor(String colorName){
        Color color = Color.black;

        switch(colorName){
            case("Red"):
                color = Color.red;
                break;
            case("Green"):
                color = Color.green;
                break;
            case("Blue"):
                color = Color.blue;
                break;
            case("Yellow"):
                color = Color.yellow;
                break;
            case("Pink"):
                color = Color.pink;
                break;
        }
        return color;
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
       try{db.createView("views."+viewName);}catch(Exception ex){};
       pickedRadioButtons.forEach((ledNumber) ->{
            int index = pickedRadioButtons.indexOf(ledNumber);
            try{db.insert( "views."+viewName, columns, new String[]{
                    Integer.toString(ledNumber),
                    pickedRadioButtonsColors.get(index)}
            );}catch(Exception ex){}
        });
        db.closeConnection();
    }



    void drawRadioButton(ColoredJRadioButton rb, boolean pressed){
        if(pickToolComboBox.getSelectedItem() == "Draw") {
            if(pressed) {
                if (!rb.isSelected()) {
                    rb.doClick();
                    Color color = pickColor(pickColorComboBox.getSelectedItem().toString());
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


}
