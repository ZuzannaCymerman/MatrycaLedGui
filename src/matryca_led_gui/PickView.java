package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
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
    private JPanel previewPanel;
    private JButton previewButton;
    private JLabel brightnessPreviewLabel;
    private Database db = new Database();
    private WiFi wifi = new WiFi();
    private JLabel[] previewIcons = new JLabel[200];
    private LedColor[] ledColors = new LedColor[10];

    public PickView() {
        setPickViewComboBox();
        setDeleteFromDatabaseButton();
        setShowButton();
        setResetDatabaseButton();
        setStopViewButton();
        setPreviewButton();
        setLedColors();
        ColorIcon ci = new ColorIcon(12,12,ledColors[0]);
        for(int i=0;i<200;i++){
            previewIcons[i] = new JLabel(ci);
            previewPanel.add(previewIcons[i]);
        }
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

                wifi.sendRequest("|V|"+requestString);
                System.out.println("|V|"+requestString);

            }
        });
    }

    public void setPreviewButton(){
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("preview");
                String view = pickViewComboBox.getSelectedItem().toString();
                HashMap<String, ArrayList<String>> viewData =  new HashMap<String, ArrayList<String>>();
                try {
                    viewData = db.fetch(view, new String[]{"led_color", "led_brightness"});
                } catch (Exception ex) {
                }

                for(int i=0;i<200;i++){
                    int colorCode = Integer.valueOf(viewData.get("led_color").get(i));
                    ColorIcon ci = new ColorIcon(12,12,ledColors[colorCode]);
                    previewIcons[i].setIcon(ci);
                }
                brightnessPreviewLabel.setText("Jasność: "+viewData.get("led_brightness").get(0));
                previewPanel.repaint();
            }
        });
    }

    public void setLedColors(){
        Color transparent = new Color(163,166,168);
        ledColors[0] = new LedColor(transparent, 0);
        ledColors[1] = new LedColor(Color.red, 1);
        ledColors[2] = new LedColor(Color.green, 2);
        ledColors[3] = new LedColor(Color.blue, 3);
        ledColors[4] = new LedColor(Color.yellow, 4);
        ledColors[5] = new LedColor(Color.orange, 5);
        ledColors[6] = new LedColor(Color.cyan, 6);
        ledColors[7] = new LedColor(Color.white, 7);
        ledColors[8] = new LedColor(Color.magenta, 8);
        ledColors[9] = new LedColor(Color.pink, 9);

    }




}
