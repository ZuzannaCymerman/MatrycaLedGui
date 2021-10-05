package matryca_led_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateView {
    private JRadioButton[] radioButtons = new JRadioButton[200];
    public JPanel createViewPanel;
    private JPanel radioButtonsPanel;
    private JButton button1;
    public ArrayList<Integer> pickedRadioButton;

    CreateView(){
        initializeJRadioButtons();
    }

    private void initializeJRadioButtons(){
       pickedRadioButton = new ArrayList<Integer>();
        for(int i = 0; i< radioButtons.length; i++){
            radioButtons[i] = new JRadioButton("radioButton"+i);
            radioButtons[i].setSize(20,20);
            radioButtons[i].setActionCommand(Integer.toString(i));
            radioButtons[i].setText("");
            radioButtonsPanel.setSize(200,200);
            radioButtonsPanel.add(radioButtons[i]);
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i< radioButtons.length; i++){
                    if(radioButtons[i].isSelected()){
                        if(!pickedRadioButton.contains(i)){
                            pickedRadioButton.add(i);
                        }
                    }
                    else{
                        if(pickedRadioButton.contains(i)){
                            pickedRadioButton.remove(pickedRadioButton.indexOf(i));
                        }
                    }
                }
                System.out.println(pickedRadioButton);
            }
        });



    }

}
