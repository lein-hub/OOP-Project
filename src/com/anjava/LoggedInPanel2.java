package com.anjava;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LoggedInPanel2 extends JPanel{
	
	JTextArea reserve;
	
//	LoggedInPanel lip = new LoggedInPanel();
	
	public LoggedInPanel2() {
		reserve = new JTextArea();
		reserve.setBounds(615,54,160,400);
		reserve.setBackground(Color.gray.brighter());
		System.out.println(41645);
	}
}
