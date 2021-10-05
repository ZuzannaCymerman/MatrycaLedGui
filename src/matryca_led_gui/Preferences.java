package matryca_led_gui;

import arduino.Arduino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Preferences {
    private SerialPortConfig serialPortConfig = new SerialPortConfig();
    private WiFiConfig wifiConfig = new WiFiConfig();
    public JPanel preferencesPanel;
    private JPanel serialPortConfigPanel = serialPortConfig.serialPortConfigPanel;
    private JPanel wifiConfigPanel = wifiConfig.wifiConfigPanel;
    private Arduino arduino = new Arduino();


    Preferences(){
        preferencesPanel.add(serialPortConfigPanel, BorderLayout.NORTH);
        preferencesPanel.add(wifiConfigPanel, BorderLayout.SOUTH);
        connectToWiFi();
    }

    void connectToWiFi(){
        wifiConfig.connectToWiFiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arduino = serialPortConfig.arduino;
                arduino.serialWrite(wifiConfig.selectedSSID + "|" + wifiConfig.selectedPassword +"|");
            }
        });
    }







}
