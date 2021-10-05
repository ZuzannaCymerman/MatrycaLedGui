package matryca_led_gui;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LEDs {
    public JPanel led_panel;
    private JButton led_off;
    private JButton led_on;
    private WiFi wifi = new WiFi();

    LEDs(){
        led_on_action();
        led_off_action();
    }

    void led_on_action(){
        led_on.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("on");
                String json_data = new JSONObject()
                        .put("led" , "on")
                        .toString();
                wifi.sendRequest(json_data);
            }
        });
    }

    void led_off_action(){
        led_off.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("off");
                String json_data = new JSONObject()
                        .put("led" , "off")
                        .toString();
               wifi.sendRequest(json_data);
            }
        });
    }

}
