package com.facialrecognition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCRUDApp {
    private JFrame frame;
    private JButton btnInsert, btnUpdate, btnDelete, btnView;

    public SimpleCRUDApp() {
        frame = new JFrame("Simple CRUD App");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnInsert = new JButton("Insert");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View");

        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call insert operation
                CRUDOperations.insertData("Value1", "Value2", "Value3");
                JOptionPane.showMessageDialog(frame, "Record inserted successfully!");
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call update operation
                CRUDOperations.updateData(1, "UpdatedValue1", "UpdatedValue2", "UpdatedValue3");
                JOptionPane.showMessageDialog(frame, "Record updated successfully!");
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call delete operation
                CRUDOperations.deleteData(1);
                JOptionPane.showMessageDialog(frame, "Record deleted successfully!");
            }
        });

        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call select operation
                CRUDOperations.selectData();
            }
        });

        JPanel panel = new JPanel();
        panel.add(btnInsert);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnView);

        frame.add(panel);
        frame.setVisible(true);
    }    
}
