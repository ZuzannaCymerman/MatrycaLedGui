package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.*;

public class Views extends JFrame{
    PickView pickView = new PickView();
    CreateView createView = new CreateView();
    public JPanel viewsPanel;
    private JPanel createViewPanel = createView.createViewPanel;
    private JPanel pickViewPanel = pickView.pickViewPanel;

    Views(){
        setVisible(true);
        setContentPane(viewsPanel);
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        viewsPanel.add(createViewPanel, BorderLayout.WEST);
        viewsPanel.add(pickViewPanel, BorderLayout.EAST);
        createView.saveViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String viewName = createView.viewNameTextField.getText();
                Pattern p = Pattern.compile("^[A-Za-z0-9]{1,15}$");
                Matcher m = p.matcher(viewName);
                boolean correctMatch = m.matches();
                if(!correctMatch) {
                    createView.validator.setVisible(true);
                }else{
                    createView.validator.setVisible(false);
                    createView.saveView();
                    pickView.setPickViewComboBox();
                }
            }
        });
    }
}
