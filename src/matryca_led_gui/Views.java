package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                if(viewName.length()<16) {
                    createView.saveView();
                    pickView.setPickViewComboBox();
                    createView.validator.setVisible(false);
                }else{
                    createView.validator.setVisible(true);
                }
            }
        });
    }
}
