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
    private JButton refreshButton;
    private Database db = new Database();
    private WiFi wifi = new WiFi();

    public PickView() {
        setPickViewComboBox();
        resetDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.setDB();
                db.clearViews();
                db.closeConnection();
            }
        });
        deleteFromDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.setDB();
                db.deleteView(pickViewComboBox.getSelectedItem().toString());
                db.closeConnection();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPickViewComboBox();
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
                JSONObject json_data = new JSONObject();
                json_data.put("led_number", viewData.get("led_number"));
                json_data.put("led_color", viewData.get("led_color"));
                System.out.println(json_data);
                wifi.sendRequest(json_data.toString());
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
