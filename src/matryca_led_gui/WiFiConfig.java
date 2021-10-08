package matryca_led_gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class WiFiConfig {
    public JPanel wifiConfigPanel;
    private JTextField ssidField;
    private JTextField passwordField;
    private JComboBox pickNetworkComboBox;
    private JButton saveButton;
    private JButton cleanDatabaseButton;
    public JButton connectToWiFiButton;

    public String selectedSSID;
    public String selectedPassword;
    private Database db = new Database();
    private  HashMap<String, ArrayList<String>> networks = new HashMap<String, ArrayList<String>>();

    WiFiConfig(){
        saveWiFi();
        listAllNetworksAndPick();
        cleanDatabase();
    }

    public void saveWiFi() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.setDB();
                String network_ssid = ssidField.getText();
                String network_password = passwordField.getText();
                ssidField.setText(null);
                passwordField.setText(null);
                try {
                    db.insert( "networks",
                            new String[]{"ssid", "password"},
                            new String[]{network_ssid, network_password});
                } catch (Exception ex) {
                }
                listAllNetworksAndPick();
                db.closeConnection();
            }

        });

    }

    public void listAllNetworksAndPick() {
        db.setDB();
        pickNetworkComboBox.removeAllItems();
        try{networks = db.fetch( "networks", new String[]{"ssid", "password"});
        }catch(Exception ex){}
        for (String ssid: networks.get("ssid")){
            pickNetworkComboBox.addItem(ssid);
        }
        pickNetworkComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String[] selected_network = selectWiFi(networks);
                selectedSSID = selected_network[0];
                selectedPassword = selected_network[1];
                System.out.println(selectedSSID +" "+ selectedPassword);
            }
        });

        db.closeConnection();
    }

    public String[] selectWiFi(HashMap<String, ArrayList<String>> networks){
        String [] selected_wifi =new String[2];
        Object selected_object = pickNetworkComboBox.getSelectedItem();
        if(selected_object != null) {
            String selected_ssid_from_combobox = selected_object.toString();
            int ssid_index = networks.get("ssid").indexOf(selected_ssid_from_combobox);
            String selected_password_from_combobox = networks.get("password").get(ssid_index);
            selected_wifi[0] = selected_ssid_from_combobox;
            selected_wifi[1] = selected_password_from_combobox;
        }
            return selected_wifi;

    }

    public void cleanDatabase() {
        cleanDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.setDB();
                try{db.cleanTable( "networks");}catch(Exception ex){}
                listAllNetworksAndPick();
                db.closeConnection();
            }
        });
    }



}
