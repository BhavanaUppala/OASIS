package com.atm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Home_Page implements ActionListener{
	JFrame frame;
	JTextField t;
	JPasswordField p;
	JLabel l,l1,l2;
	JButton b1,b2,b3;
	String accno,pin;
	TableInitializer table;
 
	Home_Page(){
		frame=new JFrame("ATM Login");
		t=new JTextField();
		p=new JPasswordField();
		l=new JLabel("WELCOME TO OASIS BANK");
		l1=new JLabel("Account Number");
		l2=new JLabel("PIN");
		b1=new JButton("Login");
		b2=new JButton("Register");
		b3=new JButton("Clear");
		l.setBounds(150,30,200,30);
		l1.setBounds(150,60,100,30);
		l2.setBounds(150,90,100,30);
		t.setBounds(300, 60, 100, 20);
		p.setBounds(300,90,100,20);
		t.setToolTipText("Enter your Account Number");
		p.setToolTipText("Enter your pin number");
		b1.setBounds(50,150,100,30);
		b2.setBounds(200,150,100,30);
		b3.setBounds(350,150,100,30);
		b1.setBackground(Color.green);
		b2.setBackground(Color.blue);
		b3.setBackground(Color.yellow);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.add(l);
		frame.add(l1);
		frame.add(l2);
		frame.add(t);
		frame.add(p);
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.setVisible(true);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Home_Page();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == b2) {
	        new RegisterPage();
	    } else if (e.getSource() == b1) {
	        accno = t.getText();  
	        pin = new String(p.getPassword());  

	        if (accno.isEmpty() || pin.isEmpty()) {
	            javax.swing.JOptionPane.showMessageDialog(frame, "Account number and PIN cannot be empty.", "Input Error", javax.swing.JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	        boolean isVerified = new TableInitializer().verifyLogin(accno, pin);

	        if (isVerified) {
	            new FirstPage(accno);
	        } else {
	            javax.swing.JOptionPane.showMessageDialog(frame, "Invalid account number or PIN.", "Login Error", javax.swing.JOptionPane.WARNING_MESSAGE);
	        }
	    } else if (e.getSource() == b3) {
	        t.setText("");
	        p.setText("");
	    }
	}
}




