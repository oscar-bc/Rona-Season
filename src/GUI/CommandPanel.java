package GUI;

import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

class CommandPanel extends JPanel  {

    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE = 14;
    private static int fileSize = 0;

    private final JTextField commandField;
    private final LinkedList<String> commandBuffer;

    CommandPanel() {
        commandField = new JTextField();
        commandBuffer = new LinkedList<>();

        JButton submitButton = new JButton("Submit");
        JButton quitButton = new JButton("Quit");
        JButton sixtyButton = new JButton("       60 Person File       ");
        JButton oneTwentyButton = new JButton("      120 Person File      ");
        JButton twoFortyButton = new JButton("      240 Person File      ");
        JButton fiveHButton = new JButton("      500 Person File      ");
        JButton customButton = new JButton("       Custom File       ");

        class AddActionListener implements ActionListener {
            public void actionPerformed(ActionEvent event)	{
                synchronized (commandBuffer) {
                    commandBuffer.add(commandField.getText());
                    commandField.setText("");
                    commandBuffer.notify();
                }
            }
        }
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    commandBuffer.add(commandField.getText());
                    commandField.setText("");
                    commandBuffer.notify();
            }
        }});
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    System.exit(0);
                }
            }});
        sixtyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    fileSize=60;
                }
            }});
        oneTwentyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    fileSize=120;
                }
            }});
        twoFortyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    fileSize=240;
                }
            }});
        fiveHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    fileSize=500;
                }
            }});
        customButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    fileSize=1;
                }
            }});
        ActionListener listener = new AddActionListener();
        commandField.addActionListener(listener);
        commandField.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.add(submitButton);
        panel.add(quitButton);
        add(commandField, BorderLayout.CENTER);
        add(panel, BorderLayout.LINE_END);
    }

    String getCommand() {
        String command;
        synchronized(commandBuffer) {
            while (commandBuffer.isEmpty()) {
                try {
                    commandBuffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            command = commandBuffer.pop();
        }
        return command;
    }

}
