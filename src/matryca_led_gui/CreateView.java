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
    private JButton resetDBButton;
    public ArrayList<Integer> pickedRadioButtons;
    public ArrayList<String> pickedRadioButtonsColors;
    private boolean pressed;
    private Database db = new Database();

    CreateView(){
        initializeJRadioButtons();
        setClearAllButton();
        resetDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.clearViewsDB();
            }
        });
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
                    if(pickToolComboBox.getSelectedItem() == "Draw") {
                        Color color = pickColor(pickColorComboBox.getSelectedItem().toString());
                        rb.setColor(color);
                    }else{
                            rb.setIcon(null);
                    }
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
        HashMap<String, ArrayList<String>> viewtablenumbers = new HashMap<String, ArrayList<String>>();
        String columns[] = new String[]{"ledNumber","color"};

        db.setDB();

        try {viewtablenumbers = db.fetch(db.conn, "viewtablenumbers",new String[]{"id"});
        }catch(Exception ex){}

        ArrayList<String> ids = viewtablenumbers.get("id");
        int lastID = Integer.valueOf(ids.get(ids.size()-1));
        String tableName = "view"+(lastID+1);

        try{db.createTable(db.conn, tableName,
                new String[]{"id","ledNumber","color"},
                new String[]{"SERIAL PRIMARY KEY", "INT","VARCHAR(20)" });
        }catch(Exception ex){};

        pickedRadioButtons.forEach((ledNumber) ->{
            int index = pickedRadioButtons.indexOf(ledNumber);
            try{db.insert(db.conn, tableName, columns, new String[]{
                    Integer.toString(ledNumber),
                    pickedRadioButtonsColors.get(index)}
            );}catch(Exception ex){}
        });

        try (Statement statement = db.conn.createStatement()) {
            statement.executeQuery("INSERT INTO viewtablenumbers(name) VALUES('name');");
        }catch(Exception ex){}

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
