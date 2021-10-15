package matryca_led_gui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
                String ledNumbers = viewData.get("led_number").get(0);
                String ledColors = viewData.get("led_color").get(0);
                for (int i=1;i<ledQuantity;i++){
                    ledNumbers = ledNumbers+"|"+viewData.get("led_number").get(i);
                    ledColors = ledColors+"|"+viewData.get("led_color").get(i);
                }

                System.out.println(ledNumbers);
                System.out.println(ledColors);
                wifi.sendRequest("|V|"+ledQuantity+"|"+ledNumbers+"|"+ledColors+"|");
            }
        });
    }

}
