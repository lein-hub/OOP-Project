package com.anjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import org.json.JSONObject;




public class MainLogin extends JFrame implements ActionListener, KeyListener{
	JPanel logInPanel, signUpBtnPanel, imagePanel, signUpMainPanel;
	JLabel mainTitle, subTitle, idLabel, pwdLabel, welcome, reLabel;
	AntialiasedLabel mainLogLabel, signUpPanelLabel;
	JTextField ID;
	JPasswordField PASSWORD;;
	JButton logInBtn, signUpBtn, signUpBtn2, exitButton, backBtn, logOutBtn;
	LoggedInPanel loggedInPanel;
	LoggedInPanel2 loggedInPanel2 = new LoggedInPanel2();
	FakeDB fake = new FakeDB();
	Font Title = new Font(null);
	ImageIcon icon;
	HttpCaller hc = new HttpCaller();
	JPanel logInLabelPanel = new JPanel();
	SignUpPanel signUpPanel = new SignUpPanel();
	JLabel[] logInLabels = new JLabel[signUpPanel.categories.length];
	JScrollPane scroll;

	public MainLogin(){
		
		//Panel
		 //LogInPanel
		icon = new ImageIcon();
		logInPanel = new JPanel();
		imagePanel = new JPanel();
		
		//----------------------------------------------------------------------------------------------
		
		
		//Label
		
		 //Main Title Label
		mainTitle = new JLabel("영진 2WDJ 좌석 예약");
		mainTitle.setBounds(155,-160,500,500);
		mainTitle.setFont(new Font("여기어때 잘난체",Font.CENTER_BASELINE,45));
		
		 //Sub Title Label
		subTitle = new JLabel("Anjava");
		subTitle.setBounds(365,-110,500,500);
		subTitle.setFont(new Font("여기어때 잘난체",Font.BOLD,20));
		
		 //ID Label
		idLabel = new JLabel("ID");
		idLabel.setBounds(47,20,135,20);
		
		 //Password Label
		pwdLabel = new JLabel("PW");
		pwdLabel.setBounds(38,45,135,20);
		
		 //welcome Label
		welcome = new JLabel();
		welcome.setBounds(5,-123,300,300);
		welcome.setFont(new Font(null,Font.CENTER_BASELINE,30));
		welcome.setVisible(false);
		
		 // 메인타이틀
		mainLogLabel = new AntialiasedLabel("");
		mainLogLabel.setIcon(new ImageIcon(MainLogin.class.getResource("/image/mainlogin.jpg")));
		mainLogLabel.setBounds(0, 0, 800, 500);
//		BufferedImage image = new BufferedImage();
		
		 // 회원가입

		
		
		//----------------------------------------------------------------------------------------------
		
		
		//TextField
		
		 //ID
		ID = new JTextField(15);
		ID.setBounds(64,20,135,20);
		ID.addKeyListener(this);
//		ID.addKeyListener(this);
//		ID.registerKeyboardAction(this, "login", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_FOCUSED);
		 //Password
		PASSWORD = new JPasswordField(15);
		PASSWORD.setBounds(64,45,135,20);
		PASSWORD.addKeyListener(this);

		
		//----------------------------------------------------------------------------------------------
		
		
		//Buttons
		 //LogIn Button
		logInBtn = new JButton("로그인");
		logInBtn.setBounds(85, 75, 80, 25);
		logInBtn.setBackground(Color.LIGHT_GRAY);
		logInBtn.addActionListener(this);
		
		
		 //SignUp Button
		signUpBtn = new JButton("회원가입");
		signUpBtn.setBounds(80, 110, 90, 25);
		signUpBtn.setBackground(Color.PINK);
		signUpBtn.addActionListener(this);
		
		 //Exit Button
		exitButton = new JButton("");
		exitButton.setBounds(770, 10, 20, 20);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		 //LogOutButton
		logOutBtn = new JButton("로그아웃");
		logOutBtn.setVisible(true);
		logOutBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addMainLogIn();
				deleteLoggedInPanel();
				hc.clearData();
			}
			
		});
		logOutBtn.setBounds(691, 464, 84, 25);
		
		
		//회원가입 창 입력항목 설정
		for(int i = 0; i < signUpPanel.categories.length; i++) {
			logInLabels[i] = new JLabel(signUpPanel.categories[i]);
			logInLabels[i].setHorizontalAlignment(JLabel.RIGHT);
			logInLabelPanel.add(logInLabels[i]);
		}
		
		//----------------------------------------------------------------------------------------------
		
		
		
		//Panel Setting

		logInPanel.setLayout(null);
		logInPanel.setBounds(275, 220, 250, 150);
		logInPanel.setBackground(new Color(255,255,255));
		logInPanel.add(ID);
		logInPanel.add(PASSWORD);
		logInPanel.add(idLabel);
		logInPanel.add(pwdLabel);
		logInPanel.add(logInBtn);
		logInPanel.add(signUpBtn);
		
		
		
		//----------------------------------------------------------------------------------------------
		
		
		//Frame Setting
		this.setLayout(null);
		this.add(logInPanel);
		this.add(imagePanel);
		this.add(exitButton);
		this.add(welcome);
		this.add(mainLogLabel);
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
//		this.setResizable(true);
		this.setUndecorated(true);
		this.setVisible(true);
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		imagePanel.setVisible(true);
		
		//로그인 버튼 눌렀을 때
		if(e.getSource()==logInBtn) {
			hc.postLogIn(ID.getText(), PASSWORD.getText());
//			logInLabelPanel.setVisible(false);
			
			//로그인 정보가 일치할 때
			if(hc.isLoggedIn()) {		
				addLoggedInPanel();
				deleteMainLogIn();
				setLogInTextEmpty();
//				 getContentPane().setLayout(new BorderLayout());
//				    getContentPane().add(loggedInPanel.scroll, BorderLayout.CENTER);
//				    setPreferredSize(new Dimension(100, 100));
//				    pack();
//				    setVisible(true);
				//로그인 했을 때 생기는 Buttons
				loggedInPanel = new LoggedInPanel(new JSONObject(hc.getAllRoom()).getJSONObject("data").getJSONArray("roomsData"));
				
				for(int i = 0; i < loggedInPanel.boxCount; i++) {
//					loggedInPanel.reserveBtn[i] = new JButton("<HTML>" + fake.relistedRoom[0] + "<br>예약 현황20/40<br>좌석초기화:2021/03/09<br>앞으로 27일 5시간 39분<HTML>");
//					loggedInPanel.reserveBtn[i].setBackground(Color.gray.brighter());
//					loggedInPanel.reserveBtn[i].setBorder(null);
					loggedInPanel.reserveBtn[i].addActionListener((ActionListener) new ActionListener() {
//											//예약버튼 눌렀을 때
//											@Override
											public void actionPerformed(ActionEvent e) {
												
												//예약버튼설정
													for(int i=0; i <loggedInPanel.boxCount; i++) {
														loggedInPanel.reserveBtn[i].setVisible(false);
													}
													loggedInPanel2.reserve.setVisible(false);
												}
											});
////					loggedInPanel.scroll.add(loggedInPanel.reserveBtn[i]);
//					loggedInPanel.add(loggedInPanel.reserveBtn[i]);
				}
				
				//로그인 정보가 불일치할 때
			}else {
				JOptionPane.showInternalMessageDialog(null, "회원정보가 일치하지 않습니다.", "정보 불일치",0 );
			}
		}

		
		//메인 화면에서 회원가입 버튼 눌렀을 때
		if(e.getSource()==signUpBtn) {
			
			
			signUpBtnPanel = new JPanel();
			signUpPanelLabel = new AntialiasedLabel("");
			signUpPanelLabel.setIcon(new ImageIcon(MainLogin.class.getResource("/image/signup.jpg")));
			signUpPanelLabel.setBounds(0, 0, 800, 500);

			logInLabelPanel.setLayout(new GridLayout(6,0,10,10));
			logInLabelPanel.setBounds(200,130,130,200);
			logInLabelPanel.setBackground(Color.white);
			
			
			//회원가입창의 버튼
			signUpBtn2 = new JButton("회원가입");
			backBtn = new JButton("뒤로가기");
			signUpBtnPanel.add(backBtn);
			signUpBtn2.setBackground(new Color(255,128,0));
			signUpBtnPanel.add(signUpBtn2);
			signUpBtnPanel.setBounds(315,350,180,35);
			signUpBtnPanel.setBackground(Color.white);
			
			signUpMainPanel = new JPanel();
			signUpMainPanel.setSize(400,300);

			signUpPanel.add(signUpMainPanel);
			signUpPanel.setBackground(new Color(255, 0, 0, 0));
			signUpBtnPanel.add(signUpMainPanel);
			signUpMainPanel.setBounds(350,550,300,200);
			
			signUpBtn2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					//회원가입 창에서 회원가입 버튼 눌렀을 때
					int yjuNum;
		               if (signUpPanel.fields[4].getText().equals("")) {
		                  yjuNum = 0;
		               } else {
		                  yjuNum = Integer.valueOf(signUpPanel.fields[4].getText());
		               }
		               
		               String res = hc.postSign(signUpPanel.fields[1].getText(), 
		                        signUpPanel.pwdFields[0].getText(), 
		                        signUpPanel.fields[0].getText(), 
		                        yjuNum, 
		                        signUpPanel.fields[5].getText());
					System.out.println(res);
					JSONObject jo = new JSONObject(res);
					if (!signUpPanel.pwdFields[0].getText().equals(signUpPanel.pwdFields[1].getText())) {
						JOptionPane.showInternalMessageDialog(null, "비밀번호가 동일하지 않습니다.", "Error",1);
					}
					else if (jo.isNull("status")) {
						JOptionPane.showInternalMessageDialog(null, "회원가입이 완료되었습니다.\n 다시 로그인 해주십시오.","회원가입 완료",1);
						for(int i = 0; i < signUpPanel.categories.length; i++) {
							logInLabels[i].setVisible(false);
						}
						deleteSignUpPanel();
						deleteInfo();
						addMainLogIn();
					} else {
						JOptionPane.showInternalMessageDialog(null, jo.getString("message"), "Error",1);
					}
				}				
			});
			
			backBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deleteSignUpPanel();
					deleteInfo();
					addMainLogIn();
					setLogInTextEmpty();
				}
				
			});
			deleteMainLogIn();
			addSignUpPanel();
		}	
	}
	
	//changes display
	
	public void setLogInTextEmpty() {
		ID.setText("");
		PASSWORD.setText("");
	}
	
	public void addSignUpPanel() {
		logInLabelPanel.setVisible(true);
		signUpPanel.setVisible(true);
		add(logInLabelPanel);
		add(signUpBtnPanel);
		add(signUpPanel);
		add(signUpPanelLabel);
	}
	
	public void deleteSignUpPanel() {
		logInLabelPanel.setVisible(false);
		signUpPanel.setVisible(false);
		signUpPanelLabel.setVisible(false);
		remove(signUpBtnPanel);
	}
	
	public void addLoggedInPanel() {
		loggedInPanel = new LoggedInPanel(new JSONObject(hc.getAllRoom()).getJSONObject("data").getJSONArray("roomsData"));
//		loggedInPanel.setPreferredSize(new Dimension(600, 400));
		add(welcome);
		
//		scroll = new JScrollPane(loggedInPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll.setPreferredSize(new Dimension(600, 400));
		welcome.setVisible(true);
//		add(scroll);
		add(loggedInPanel);
		add(loggedInPanel2.reserve);
		welcome.setText("안녕하세요. " + hc.getName() + "님");
		add(logOutBtn);
	}
	
	public void deleteLoggedInPanel() {
		remove(welcome);
		remove(loggedInPanel);
		remove(loggedInPanel2);
		remove(loggedInPanel2.reserve);
	}
	
	public void addMainLogIn() {
		logInPanel.setVisible(true);
		mainTitle.setVisible(true);
		subTitle.setVisible(true);
		mainLogLabel.setVisible(true);
		remove(logOutBtn);
	}
	
	public void deleteMainLogIn() {
		logInPanel.setVisible(false);
		mainTitle.setVisible(false);
		subTitle.setVisible(false);
		mainLogLabel.setVisible(false);
	}
	
	public void deleteInfo() {
		for(int i = 0; i < signUpPanel.categories.length; i++) {
			if(i == 2 || i ==3) {
				signUpPanel.pwdFields[i-2].setText("");
			}else {
				signUpPanel.fields[i].setText("");
			}
		}
	}
	
	//key events
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			logInBtn.doClick();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	public static void main(String[] args) {
		new MainLogin();
	}
}