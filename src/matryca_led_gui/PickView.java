package matryca_led_gui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        db.setDB();
        try{views =  db.fetchViews();}catch(Exception e){}
        views.forEach((view) -> {
            pickViewComboBox.addItem(view);
        });
        db.closeConnection();
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
                db.setDB();
                db.deleteView(pickViewComboBox.getSelectedItem().toString());
                setPickViewComboBox();
                db.closeConnection();
            }
        });
    }

    public void setResetDatabaseButton(){
        resetDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.setDB();
                db.clearViews();
                setPickViewComboBox();
                db.closeConnection();
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
                    viewData = db.fetch(view, new String[]{"led_number", "led_color"});
                } catch (Exception ex) {
                }

                int ledQuantity = viewData.get("led_number").size();

                int[] ledNumbersBinary = new int[200];
                int[] ledColorsBinary = new int[200];
                Arrays.fill(ledNumbersBinary, 0);
                Arrays.fill(ledColorsBinary, 0);
                String requestString ="";

                for(int i=0;i<ledQuantity;i++){
                    int index =  Integer.valueOf(viewData.get("led_number").get(i));
                    ledNumbersBinary[index] = 1;
                    ledColorsBinary[index] = Integer.valueOf(viewData.get("led_color").get(i));
                }
                for(int i =0;i<200;i++){
                    requestString = requestString + ledNumbersBinary[i];
                }
                for(int i =0;i<200;i++){
                    requestString = requestString + ledColorsBinary[i];
                }

                System.out.println(requestString);
                wifi.sendRequest("|V|"+requestString);
            }
        });
    }

}
