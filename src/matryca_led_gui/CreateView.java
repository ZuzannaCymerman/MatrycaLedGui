package matryca_led_gui;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CreateView {
    private JRadioButton[] radioButtons = new JRadioButton[200];
    public JPanel createViewPanel;
    private JPanel radioButtonsPanel;
    private JButton button1;
    private JComboBox comboBox1;
    public ArrayList<Integer> pickedRadioButtons;
    private boolean pressed;

    CreateView(){
        initializeJRadioButtons();
    }

    private void initializeJRadioButtons(){
       pickedRadioButtons = new ArrayList<Integer>();
        for(int i = 0; i< radioButtons.length; i++){
            radioButtons[i] = new JRadioButton("radioButton"+i);
            radioButtons[i].setSize(20,20);
            radioButtons[i].setActionCommand(Integer.toString(i));
            radioButtons[i].setText("");
            radioButtons[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    pressed = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    pressed = false;
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Object obj = e.getSource();
                    JRadioButton rb = (JRadioButton) obj;

                    if(comboBox1.getSelectedItem() == "Draw") {
                        if(pressed) {
                            if (!rb.isSelected())
                                rb.doClick();
                        }
                    }else{
                        if(pressed) {
                            if (rb.isSelected())
                                rb.doClick();
                        }
                    }

                }

            });
            radioButtonsPanel.setSize(200,200);
            radioButtonsPanel.add(radioButtons[i]);
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i< radioButtons.length; i++){
                    if(radioButtons[i].isSelected()){
                        if(!pickedRadioButtons.contains(i)){
                            pickedRadioButtons.add(i);
                        }
                    }
                    else{
                        if(pickedRadioButtons.contains(i)){
                            pickedRadioButtons.remove(pickedRadioButtons.indexOf(i));
                        }
                    }
                }
                System.out.println(pickedRadioButtons);
            }
        });



    }

}
