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
                    viewData = db.fetch(view, new String[]{"led_color", "led_brightness"});
                } catch (Exception ex) {
                }

                ArrayList ledColors = viewData.get("led_color");
                String ledBrightness = viewData.get("led_brightness").get(0);

                System.out.println(ledColors.size());

                String requestString = ledBrightness+"|";

                for(int i =0;i<200;i++){
                    requestString = requestString + ledColors.get(i);
                }

                System.out.println(requestString);
                wifi.sendRequest("|V|"+requestString);
            }
        });
    }


}
