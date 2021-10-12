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
    private Database db = new Database();
    private WiFi wifi = new WiFi();

    public PickView() {
        setPickViewComboBox();
        resetDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.setDB();
                db.clearViews();
                setPickViewComboBox();
                db.closeConnection();
            }
        });
        deleteFromDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.setDB();
                db.deleteView(pickViewComboBox.getSelectedItem().toString());
                setPickViewComboBox();
                db.closeConnection();
            }
        });
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String view = pickViewComboBox.getSelectedItem().toString();
                HashMap<String, ArrayList<String>> viewData =  new HashMap<String, ArrayList<String>>();
                try {
                    viewData = db.fetch(view, new String[]{"led_number", "led_color"});
                } catch (Exception ex) {
                }
                JSONObject ledNumber = new JSONObject();
                JSONObject ledColor = new JSONObject();
                ledNumber.put("led_number", viewData.get("led_number"));
                ledColor.put("led_color", viewData.get("led_color"));
                int ledQuantity = viewData.get("led_number").size();
                String ledNumberRequest = "|N|"+ledQuantity+viewData.get("led_number");
                String ledColorRequest = "|C|"+ledQuantity+viewData.get("led_color");
                System.out.println(ledNumberRequest);
                System.out.println(ledColorRequest);
                wifi.sendRequest(ledNumberRequest);
                wifi.sendRequest(ledColorRequest);
            }
        });
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
}
