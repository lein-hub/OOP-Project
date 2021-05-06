package com.anjava;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoggedInPanel extends JPanel{
	JButton[] reserveBtn = new JButton[12];
	JPanel labelPanel;
	
	LoggedInPanel2 lip = new LoggedInPanel2(); 
	
	public LoggedInPanel(){
		//PanelSetting
		this.setBounds(6,54,600,400);
		this.setLayout(new GridLayout(3,4,5,5));
	}

	
}
