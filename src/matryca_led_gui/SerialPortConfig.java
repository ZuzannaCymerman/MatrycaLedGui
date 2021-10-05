package matryca_led_gui;

import arduino.Arduino;
import arduino.PortDropdownMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SerialPortConfig {
    public JPanel serialPortConfigPanel;
    private JButton connectButton;

    private PortDropdownMenu ports;
    private JLabel connectionState;
    public String port;
    public Arduino arduino;

    SerialPortConfig(){
        connect();
    }

    void connect(){
        connectionState.setFont(new Font("Helvetica", Font.PLAIN, 18));
        connectionState.setVisible(false);
        ports.refreshMenu();
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                port = ports.getSelectedItem().toString();
                arduino = new Arduino(port, 9600); //enter the port name here, and ensure that Arduino is connected, otherwise exception will be thrown.
                if(arduino.openConnection())
                connectionState.setVisible(true);
                else
                    connectionState.setText("Connection failed. Try another port");
                    connectionState.setVisible(true);
            }
        });

    }


}
