package com.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class TableInitializer{
    Statement st;
    String accno, pin, uname;
    Double balance;
    Connection con;
    public TableInitializer() {
    	
    	String driverClass="com.mysql.cj.jdbc.Driver";
    	String url="jdbc:mysql://localhost:3306/atmdb";
    	String uname="root";
    	String pass="root";
    	try {
    		Class.forName(driverClass);
    		con=DriverManager.getConnection(url,uname,pass);
    		st=con.createStatement();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
  
    }

    public boolean verifyLogin(String accno, String pin) {
        String query = "SELECT * FROM userstable WHERE accno = ? AND pin = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, accno);  
            stmt.setString(2, pin);    

           

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;  
    }
 



    public void insertATMTable(String accno, String pin, String username, Double balance) {
        String checkQuery = "SELECT * FROM userstable WHERE accno = ?";
        String insertQuery = "INSERT INTO userstable ( accno, username, pin, balance) VALUES (?,?,?,?)";
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery);
             PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
            checkStmt.setString(1, accno);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
            	JOptionPane.showMessageDialog(null, "Account already exists. Try another.", 
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            insertStmt.setString(1, accno);
            insertStmt.setString(2, username);
            insertStmt.setString(3, pin);
            insertStmt.setDouble(4, balance);
            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Registration Successful! " + username + ", you are ready to login.", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                 
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Please try again.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
 

    public void Deposit(String accno) {
    	this.accno=accno;
        String query = "SELECT balance FROM userstable WHERE accno=?";
        String update = "UPDATE userstable SET balance=? WHERE accno=?";
        
        try (PreparedStatement checkStmt = con.prepareStatement(query);
             PreparedStatement insertStmt = con.prepareStatement(update)) {
            String amount = JOptionPane.showInputDialog("Enter the amount to be deposited");
            Double amt = Double.parseDouble(amount);
            
            checkStmt.setString(1, accno);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                balance = rs.getDouble("balance");
                balance += amt;
                
                insertStmt.setDouble(1, balance);
                insertStmt.setString(2, accno);
              
                int rowsUpdated = insertStmt.executeUpdate();
                
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Deposit successful. New balance: " + balance);
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating balance.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Account not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during the deposit.");
        }
        String query1 = "INSERT INTO transaction(accno, deposit) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query1)) {
            pstmt.setString(1, accno); 
            pstmt.setDouble(2, balance);

            pstmt.executeUpdate(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    

    public void Withdraw(String accno) {
    	this.accno=accno;
        String query = "SELECT balance FROM userstable WHERE accno=?";
        String update = "UPDATE userstable SET balance=? WHERE accno=?";
        
        try (PreparedStatement checkStmt = con.prepareStatement(query);
             PreparedStatement insertStmt = con.prepareStatement(update)) {
            String amount = JOptionPane.showInputDialog("Enter the amount to be withdrawn");
            Double amt = Double.parseDouble(amount);
            
            checkStmt.setString(1, accno);
            ResultSet rs = checkStmt.executeQuery();
           
            if (rs.next()) {
                balance = rs.getDouble("balance");
                balance -= amt;
                
                insertStmt.setDouble(1, balance);
                insertStmt.setString(2, accno);
              
                int rowsUpdated = insertStmt.executeUpdate();
                
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "withdraw is done successfully. New balance: " + balance);
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating balance.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Account not found.");
            }
        
        }
    
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during the deposit.");
        }
        String query1 = "INSERT INTO transaction (accno, withdraw) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query1)) {
            pstmt.setString(1, accno); 
            pstmt.setDouble(2, balance); 

            pstmt.executeUpdate(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
}

	


