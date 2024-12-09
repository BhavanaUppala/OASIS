package com.atm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegisterPage implements ActionListener {
	JFrame frame;
	JTextField[] T;
	JLabel[] L;
	JButton b1,b2,b3;
	int id;
	String accno,pin,username;
	Double balance;
	TableInitializer tableInitializer;
	Home_Page homepage;
	RegisterPage(){
		frame=new JFrame("Registration");
		T=new JTextField[4];
		L=new JLabel[4];
		b1=new JButton("Register");
		b2=new JButton("Clear");
		b3=new JButton("Cancel");
		for(int i=0;i<4;i++) {
			L[i]=new JLabel();
			T[i]=new JTextField();
		}
		L[0].setText("Account no");
		L[1].setText("PIN");
		L[2].setText("User Name");
		L[3].setText("Account Balance");
		for(int i=0;i<4;i++) {
			L[i].setBounds(150,30*(i+1),100,30);
			T[i].setBounds(300,30*(i+1),100,20);
		}
		b1.setBounds(150,150,100,30);
		b2.setBounds(300,150,100,30);
		b3.setBounds(200,200,100,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b1.setBackground(Color.BLUE);
		b2.setBackground(Color.yellow);
		b3.setBackground(Color.red);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		for(int i=0;i<4;i++) {
			frame.add(L[i]);
			frame.add(T[i]);
		}
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1) {
			accno = T[0].getText();
            pin = T[1].getText();
            username = T[2].getText();
            try {
                balance = Double.parseDouble(T[3].getText());
                tableInitializer = new TableInitializer();
                tableInitializer.insertATMTable(accno, pin, username, balance);
                frame.dispose();
                new Home_Page();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid balance input. Please enter a valid number.");
            }
		}
		else if(e.getSource()==b2) {
			for(int i=0;i<4;i++)
				T[i].setText("");
		}
		else if(e.getSource()==b3) {
			homepage=new Home_Page();
		}
	}
}
