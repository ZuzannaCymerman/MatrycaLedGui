package matryca_led_gui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PickView {
    private JComboBox pickViewComboBox;
    private JButton deleteFromDatabaseButton;
    public JPanel pickViewPanel;
    private JButton resetDatabaseButton;
    private JButton showButton;
    private JButton stopViewButton;
    private JButton previewButton;
    private Database db = new Database();
    private WiFi wifi = new WiFi();

    public PickView() {
        setPickViewComboBox();
        setDeleteFromDatabaseButton();
        setShowButton();
        setResetDatabaseButton();
        setStopViewButton();
    }

    public void setPickViewComboBox(){
        pickViewComboBox.removeAllItems();
        ArrayList<String> views = new ArrayList<String>();
        try{views =  db.fetchViews();}catch(Exception e){}
        views.forEach((view) -> {
            pickViewComboBox.addItem(view);
        });
    }

    public void setStopViewButton(){
        stopViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wifi.sendRequest("|S|");
            }
        });
    }

    public void setDeleteFromDatabaseButton(){
        deleteFromDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.deleteView(pickViewComboBox.getSelectedItem().toString());
                setPickViewComboBox();
            }
        });
    }

    public void setResetDatabaseButton(){
        resetDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.clearViews();
                setPickViewComboBox();
            }
        });
    }

    public void setShowButton(){
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String view = pickViewComboBox.getSelectedItem().toString();
                HashMap<String, ArrayList<String>> viewData =  new HashMap<String, ArrayList<String>>();
                try {
                    viewData = db.fetch(view, new String[]{"led_color"});
                } catch (Exception ex) {
                }

                ArrayList ledColors = viewData.get("led_color");
                System.out.println(ledColors.size());

                String requestString ="";

                for(int i =0;i<200;i++){
                    requestString = requestString + ledColors.get(i);
                }

                System.out.println(requestString);
                wifi.sendRequest("|V|"+requestString);
            }
        });
    }
    public void setPreviewButton(ColoredJRadioButton[] radioButtons){
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String view = pickViewComboBox.getSelectedItem().toString();
                HashMap<String, ArrayList<String>> viewData =  new HashMap<String, ArrayList<String>>();
                try {
                    viewData = db.fetch(view, new String[]{"led_color"});
                } catch (Exception ex) {}

                ArrayList ledColors = viewData.get("led_color");

                for(int i =0;i<200;i++) {
                    ColoredJRadioButton rb = radioButtons[i];
                    if(rb.isSelected()){
                        rb.doClick();
                        rb.setIcon(null);
                    }
                    rb.doClick();
                    int ledColor = Integer.valueOf(ledColors.get(i).toString());
                    rb.setColor(pickColor(ledColor));
                }
            }
        });
    }

    LedColor pickColor(int pickedColorNumber){
        LedColor color = new LedColor(Color.black, 0);

        switch(pickedColorNumber){
            case(1):
                color = new LedColor(Color.red, 1);
                break;
            case(2):
                color =  new LedColor(Color.green, 2);
                break;
            case(3):
                color = new LedColor(Color.blue, 3);
                break;
            case(4):
                color = new LedColor(Color.yellow, 4);
                break;
            case(5):
                color = new LedColor(Color.orange, 5);
                break;
            case(6):
                color = new LedColor(Color.cyan, 6);
                break;
            case(7):
                color = new LedColor(Color.white, 7);
                break;
            case(8):
                color = new LedColor(Color.magenta, 8);
                break;
            case(9):
                color = new LedColor(Color.pink, 9);
                break;
        }
        return color;

    }


}
