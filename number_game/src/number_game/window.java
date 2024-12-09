package number_game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class window extends MyFrame implements ActionListener{
	 JFrame f;
	 JLabel l;
	 JTextField t;
	 int chance=1,max_chance=3;
	 int TryAgain,n,x;
	 Random rdm;
	window(){
		rdm=new Random();
		n=rdm.nextInt(100)+1;
		f=new JFrame();
		l=new JLabel();
		t=new JTextField();
		l.setText("Enter a number between 1 to 100 ");
		l.setBounds(150,100,200,200);
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setVerticalAlignment(JLabel.CENTER);
		t.setBounds(200,250,100,50);
		t.addActionListener(this);
		f.setSize(500,500);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(l);
		f.add(t);
		f.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		String str=t.getText();
		int x=Integer.parseInt(str);
		
	try {
		if(x<1 || x>100) {
			JOptionPane.showMessageDialog(this,"Enter a number between 1 to 100");
			return;
		}
		
		if(x<n) {
			JOptionPane.showMessageDialog(this,"Your number is less than the generated number");
		}
		else if(x>n) {
			JOptionPane.showMessageDialog(this,"Your number is bigger than the generated number");
		}
		else {
			JOptionPane.showMessageDialog(this,"Hurry! You Win !");
			return;
		}
		chance++;
		if(chance>max_chance && x!=n) {
			JOptionPane.showMessageDialog(this,"You loose and the genrated number is "+n);
			TryAgain=JOptionPane.showConfirmDialog(this, "Do you want to continue?", "Select an option", JOptionPane.YES_NO_OPTION);
		
		if(TryAgain==JOptionPane.YES_OPTION) {
			
			chance=1;
			n=rdm.nextInt(100)+1;
		}
		else {
			JOptionPane.showMessageDialog(this,"Game Over");
			MyFrame frame=new MyFrame();
		}
	}
	}
	
	catch(NumberFormatException ne) {
		JOptionPane.showMessageDialog(this, ne);
	}
	
		}
		
	
}

