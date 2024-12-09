package com.atm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class FirstPage implements ActionListener {
	JFrame frame;
	JButton[] b;
	JLabel l;
	Connection con;
	Statement st;
	String accono;
	Double balance;
	TableInitializer tableInitializer;
	FirstPage(String accno){
		String driverClass="com.mysql.cj.jdbc.Driver";
    	String url="jdbc:mysql://localhost:3306/atmdb";
    	String uname1="root";
    	String pass="root";
    	try {
    		Class.forName(driverClass);
    		con=DriverManager.getConnection(url,uname1,pass);
    		st=con.createStatement();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		this.accono=accno;
		frame=new JFrame("ATM Interface");
		l=new JLabel("Select Your Transaction");
		l.setBounds(125,10,250,50);
		l.setFont(new Font("Ariel",Font.BOLD,18));
		b = new JButton[5];
		 	for(int i=0;i<5;i++) {
		 		b[i]=new JButton();
		 	}
		b[0].setText("Transactions History");
		b[1].setText("Withdraw");
		b[2].setText("Deposit");
		b[3].setText("Transfer");
		b[4].setText("Quit");
		b[0].setBackground(Color.gray);
		b[1].setBackground(Color.orange);
		b[2].setBackground(Color.green);
		b[3].setBackground(Color.blue);
		b[4].setBackground(Color.red);
			for(int i=0;i<5;i++) {
				b[i].setBounds(150,30*(i+2),175,30);
			}
			for(int i=0;i<5;i++) {
				b[i].addActionListener(this);
			}
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.add(l);
			for(int i=0;i<5;i++)
				frame.add(b[i]);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == b[0]) {
		    String query = "SELECT * FROM transaction where accno=?";  
		    
		    try (PreparedStatement stmt = con.prepareStatement(query)) {
	            stmt.setString(1, accono);  

	            try (ResultSet rs = stmt.executeQuery()) {
	                StringBuilder result = new StringBuilder("Transaction History:\n\n");

	                boolean hasData = false;
	                while (rs.next()) {
	                    hasData = true; 
	                    String accno = rs.getString("accno");
	                    double deposit = rs.getDouble("deposit");
	                    double withdraw = rs.getDouble("withdraw");
	                    double transfer = rs.getDouble("transfer");
	                    Date transactionDate = rs.getDate("transaction_date");
	                    result.append("Account No: ").append(accno)
	                          .append("\nDeposit: ").append(deposit)
	                          .append("\nWithdraw: ").append(withdraw)
	                          .append("\nTransfer: ").append(transfer)
	                          .append("\nDate: ").append(transactionDate)
	                          .append("\n----------------------------\n");
	                }

	                if (hasData) {
	                    JOptionPane.showMessageDialog(null, result.toString());
	                } else {
	                    JOptionPane.showMessageDialog(null, "No transaction history available.");
	                }

	            }

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error retrieving transaction history.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
		}


		else if(e.getSource()==b[1]){
			
			tableInitializer =new TableInitializer();
			tableInitializer.Withdraw(accono);
		}
		else if(e.getSource()==b[2]){
			tableInitializer =new TableInitializer();
			tableInitializer.Deposit(accono);	
			}
		else if(e.getSource()==b[3]){
			new TransferAmount();
		}
		else if(e.getSource()==b[4]){
					new Home_Page();
				}
		
	}
	
}
