package com.anjava;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class SignUpPanel extends JPanel{
	
	String[] categories = {"Name", "ID", "Password", "Password Check", "Student ID", "Email"};
	JTextField[] fields = new JTextField[categories.length];
	JPasswordField[] pwdFields = new JPasswordField[2];

	public SignUpPanel(){

		for(int i = 0; i < categories.length; i++) {			
			//ÆÐ½º¿öµåÇÊµå ¼³Á¤
			if(i == 2 || i ==3) {
				pwdFields[i-2] = new JPasswordField(i){
					@Override 
					public void setBorder(Border border) {		
					}
				};
				pwdFields[i-2].setToolTipText(categories[i]);
				pwdFields[i-2].setBackground(new Color(255,255,255));
				pwdFields[i-2].setForeground(new Color(125, 124, 130));
				pwdFields[i-2].setFont(new Font("SAN SERIF", Font.PLAIN, 18));
				this.add(pwdFields[i-2]);
			}else {
			fields[i] = new JTextField(15){
				@Override 
				public void setBorder(Border border) {		
				}
			};
			fields[i].setToolTipText(categories[i]);
			fields[i].setBackground(new Color(255,255,255));
			fields[i].setForeground(new Color(125, 124, 130));
			fields[i].setFont(new Font("SAN SERIF", Font.PLAIN, 18));
			this.add(fields[i]);
			}
		}
		
		this.setBounds(570,70,200,320);
		this.setBackground(new Color(255,255,255));
		this.setLayout(new GridLayout(6,0,10,10));
	}

}