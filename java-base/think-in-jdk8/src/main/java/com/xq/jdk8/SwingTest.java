package com.xq.jdk8;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-12-17 11:34
 * @Version
 **/
public class SwingTest {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("my JFrame");
        JButton jButton = new JButton("myButton");
        jButton.addActionListener(event -> {
            System.out.println("Hello World");
            System.out.println("hhhhh hahaha ");
        });

        jFrame.add(jButton);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
