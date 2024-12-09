package com.atm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TransferAmount implements ActionListener{
	JFrame frame;
	JLabel From,To,l1,l2,l3,l4;
	JTextField[] t;
	JButton b,quit;
	String u1,u2,a1,a2;
	Statement st;
	Connection con;
	String accno, pin, username;
	Double balance;
	double fromBalance = 0.0, toBalance = 0.0;
TransferAmount(){
		
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
	frame=new JFrame("Tranfer");
	From=new JLabel("From");
	To=new JLabel("To");
	l1=new JLabel("Account No:");
	l2=new JLabel("Account No:");
	l3=new JLabel("Username:");
	l4=new JLabel("Username:");
	t=new JTextField[4];
	for(int i=0;i<4;i++) {
		t[i]=new JTextField();
	}
	b=new JButton("Transfer");
	quit=new JButton("Exit");
	From.setBounds(100,10,100,30);
	To.setBounds(250,10,80,30);
	l1.setBounds(50,50,100,30);
	t[0].setBounds(150,50,80,30);
	l2.setBounds(250,50,100,30);
	t[1].setBounds(350,50,80,30);
	l3.setBounds(50,100,100,30);
	t[2].setBounds(150,100,80,30);
	l4.setBounds(250,100,100,30);
	t[3].setBounds(350,100,80,30);
	b.setBounds(100,200,100,30);
	quit.setBounds(200,200,100,30);
	b.setBackground(Color.blue);
	quit.setBackground(Color.red);
	b.addActionListener(this);
	quit.addActionListener(this);
	frame.add(From);
	frame.add(To);
	frame.add(b);
	frame.add(quit);
	frame.add(l1);
	frame.add(l2);
	frame.add(l3);
	frame.add(l4);
	for(int i=0;i<4;i++) {
		frame.add(t[i]);
	}
	frame.setSize(600,600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(null);
	frame.setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == b) {
        a1 = t[0].getText(); 
        a2 = t[1].getText(); 
        u1 = t[2].getText(); 
        u2 = t[3].getText(); 

        if (a1.isEmpty() || a2.isEmpty() || u1.isEmpty() || u2.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        try {
            String query = "SELECT accno, username FROM userstable WHERE (accno = ? AND username = ?) OR (accno = ? AND username = ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, a1); 
                pstmt.setString(2, u1); 
                pstmt.setString(3, a2); 
                pstmt.setString(4, u2); 

                ResultSet rs = pstmt.executeQuery();
                boolean fromAccountExists = false;
                boolean toAccountExists = false;
                while (rs.next()) {
                    String accno = rs.getString("accno");
                    String username = rs.getString("username");

                    if (accno.equals(a1) && username.equals(u1)) {
                        fromAccountExists = true;
                    } else if (accno.equals(a2) && username.equals(u2)) {
                        toAccountExists = true;
                    }
                }

                if (fromAccountExists && toAccountExists) {
                    String amount = JOptionPane.showInputDialog("Enter the amount to transfer from " + u1 + " to " + u2);
                    double amt = Double.parseDouble(amount);
                    

                    String balanceQuery = "SELECT accno, balance FROM userstable WHERE accno = ? OR accno = ?";
                    try (PreparedStatement pst = con.prepareStatement(balanceQuery)) {
                        pst.setString(1, a1);
                        pst.setString(2, a2);

                        ResultSet res = pst.executeQuery();
                        while (res.next()) {
                            String accno = res.getString("accno");
                            double balance = res.getDouble("balance");

                            if (accno.equals(a1)) {
                                fromBalance = balance;
                                JOptionPane.showMessageDialog(null,u1+ " Balance: " + fromBalance);
                                String query1 = "INSERT INTO transaction (accno, transfer) VALUES (?, ?)";
                                try (PreparedStatement ps = con.prepareStatement(query1)) {
                                    ps.setString(1, accno);
                                    ps.setDouble(2, amt); 

                                    ps.executeUpdate(); 
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            } else if (accno.equals(a2)) {
                                toBalance = balance;
                                JOptionPane.showMessageDialog(null,u2+" Balance: " + toBalance);
                            }
                        }
                    }

                    if (fromBalance < amt) {
                        JOptionPane.showMessageDialog(null, "Insufficient balance in the 'From' account.");
                        return;
                    }

                    String updateQuery = "UPDATE userstable SET balance = ? WHERE accno = ?";
                    try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                        updateStmt.setDouble(1, fromBalance - amt);
                        updateStmt.setString(2, a1);
                        updateStmt.executeUpdate();

                        updateStmt.setDouble(1, toBalance + amt);
                        updateStmt.setString(2, a2);
                        updateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Amount transferred successfully!");
                    }
                } else {
                    if (!fromAccountExists) {
                        JOptionPane.showMessageDialog(null, "From account does not exist.");
                    }
                    if (!toAccountExists) {
                        JOptionPane.showMessageDialog(null, "To account does not exist.");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while checking accounts.");
        }
        

    }

else if(e.getSource()==quit){
	frame.dispose();
	new Home_Page();
}
}
}