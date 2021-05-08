package com.anjava;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;


public class MainLoginOld extends JFrame implements ActionListener{
	JPanel logInPanel, signUpBtnPanel, logInLabelPanel;
	JLabel[] logInLabels;
	JLabel mainTitle, subTitle, idLabel, pwdLabel, welcome;
	JTextField ID;
	JPasswordField PASSWORD;;
	JButton logInBtn, signUpBtn, signUpBtn2;
	LoggedInPanel loggedInPanel = new LoggedInPanel();
	LoggedInPanel2 loggedInPanel2 = new LoggedInPanel2();
	FakeDB fake = new FakeDB();
	HttpCaller hc = new HttpCaller();
	
	
	public MainLoginOld(){
		
		//Panel
		 //LogInPanel
		logInPanel = new JPanel();
		
		
		//----------------------------------------------------------------------------------------------
		
		
		//Label
		
		 //Main Title Label
		mainTitle = new JLabel("영진 2WDJ 좌석 예약");
		mainTitle.setBounds(155,-160,500,500);
		mainTitle.setFont(new Font(null,Font.CENTER_BASELINE,50));
		
		 //Sub Title Label
		subTitle = new JLabel("Anjava");
		subTitle.setBounds(365,-110,500,500);
		subTitle.setFont(new Font(null,Font.BOLD,22));
		
		 //ID Label
		idLabel = new JLabel("ID");
		idLabel.setBounds(47,20,135,20);
		
		 //Password Label
		pwdLabel = new JLabel("PW");
		pwdLabel.setBounds(38,45,135,20);
		
		 //welcome Label
		welcome = new JLabel("안녕하세요. " + fake.name + "님");
		welcome.setBounds(5,-123,300,300);
		welcome.setFont(new Font(null,Font.CENTER_BASELINE,30));
		welcome.setVisible(false);
		
		
		//----------------------------------------------------------------------------------------------
		
		
		//TextField
		
		 //ID
		ID = new JTextField(15);
		ID.setBounds(64,20,135,20);
	
		 //Password
		PASSWORD = new JPasswordField(15);
		PASSWORD.setBounds(64,45,135,20);
		
		
		//----------------------------------------------------------------------------------------------
		
		
		//Button
		 //LogIn Button
		logInBtn = new JButton("로그인");
		logInBtn.setBounds(85, 75, 80, 25);
		logInBtn.addActionListener(this);
		
		 //SignUp Button
		signUpBtn = new JButton("회원가입");
		signUpBtn.setBounds(80, 110, 90, 25);
		signUpBtn.addActionListener(this);
		
		
		//----------------------------------------------------------------------------------------------
		
		
		
		//Panel Setting
		logInPanel.setBorder(new LineBorder(Color.blue));
		logInPanel.setLayout(null);
		logInPanel.setBounds(275, 190, 250, 150);
		logInPanel.add(ID);
		logInPanel.add(PASSWORD);
		logInPanel.add(idLabel);
		logInPanel.add(pwdLabel);
		logInPanel.add(logInBtn);
		logInPanel.add(signUpBtn);
		
		
		//----------------------------------------------------------------------------------------------
		
		
		//Frame Setting
		this.setLayout(null);
		this.add(mainTitle);
		this.add(subTitle);
		this.add(logInPanel);
		this.setTitle("Anjava(앉아봐)");
		this.add(welcome);
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
			
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		MainLoginOld logInfo = new MainLoginOld();
		
		
		
		//로그인 버튼 눌렀을 때
		if(e.getSource()==logInBtn) {
			hc.postLogIn(ID.getText(), PASSWORD.getText());
			
			if(hc.isLoggedIn()) {
//			if(ID.getText().equals(fake.userId)&&PASSWORD.getText().equals(fake.password)) {
				logInfo.logInPanel.setVisible(false);
				logInfo.mainTitle.setVisible(false);
				logInfo.subTitle.setVisible(false);
				logInfo.add(welcome);
				logInfo.add(loggedInPanel);
				welcome.setVisible(true);
				logInfo.add(loggedInPanel2.reserve);
				
				//Buttons
				for(int i = 0; i < 12; i++) {
					loggedInPanel.reserveBtn[i] = new JButton("<HTML>" + fake.relistedRoom[0] + "<br>예약 현황20/40<br>좌석초기화:2021/03/09<br>앞으로 27일 5시간 39분<HTML>");
					loggedInPanel.reserveBtn[i].setBackground(Color.gray.brighter());
					loggedInPanel.reserveBtn[i].setBorder(null);
					loggedInPanel.reserveBtn[i].addActionListener((ActionListener) new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												//예약버튼설정
													for(int i=0; i <12; i++) {
														loggedInPanel.reserveBtn[i].setVisible(false);
													}
													loggedInPanel2.reserve.setVisible(false);
													System.out.println(5496);
												}
											});
					loggedInPanel.add(loggedInPanel.reserveBtn[i]);
				}
				
				
			}else {
				JOptionPane.showInternalMessageDialog(null, "회원정보가 일치하지 않습니다.", "정보 불일치",0 );
			}
		}

		
		//메인 화면에서 회원가입 버튼 눌렀을 때
		if(e.getSource()==signUpBtn) {
			
			SignUpPanel signUpPanel = new SignUpPanel();
			signUpBtnPanel = new JPanel();
			
			logInLabelPanel = new JPanel();
			
			logInLabels = new JLabel[signUpPanel.categories.length];
			for(int i = 0; i < signUpPanel.categories.length; i++) {
				logInLabels[i] = new JLabel(signUpPanel.categories[i]);
				logInLabels[i].setHorizontalAlignment(JLabel.RIGHT);
				logInLabelPanel.add(logInLabels[i]);
			}
			
			
			logInLabelPanel.setLayout(new GridLayout(6,0,10,10));
			logInLabelPanel.setBounds(90,100,200,200);
			
			
			
			signUpBtn2 = new JButton("회원가입");
			signUpBtnPanel.add(signUpBtn2);
			signUpBtnPanel.setBounds(347,310,100,40);
			
			signUpBtn2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//회원가입 창에서 회원가입 버튼 눌렀을 때
					
					logInfo.remove(signUpPanel);
					
					logInfo.logInPanel.setVisible(true);
					logInfo.mainTitle.setVisible(true);
					logInfo.subTitle.setVisible(true);
					for(int i = 0; i < signUpPanel.categories.length; i++) {
						logInLabels[i].setVisible(false);
					}
					signUpBtn2.setVisible(false);
					
					JOptionPane.showInternalMessageDialog(null, "회원가입이 완료되었습니다.\n 다시 로그인 해주십시오.","회원가입 완료",1);
				}				
			});
			
			
			
			
			
			logInfo.logInPanel.setVisible(false);
			logInfo.mainTitle.setVisible(false);
			logInfo.subTitle.setVisible(false);
			welcome.setVisible(false);
			
			
			logInfo.add(logInLabelPanel);
			logInfo.add(signUpBtnPanel);
			logInfo.add(signUpPanel);
		}
		
	}
	public static void main(String[] args) {
		new MainLoginOld();
	}
}
