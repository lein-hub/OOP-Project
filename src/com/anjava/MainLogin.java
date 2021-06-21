package com.anjava;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;




public class MainLogin extends JFrame implements ActionListener, KeyListener{
	JPanel logInPanel, signUpBtnPanel, imagePanel, signUpMainPanel, cRoomPanel;
	JLabel mainTitle, subTitle, idLabel, pwdLabel, welcome, reLabel, colLabel, rowLabel, colBlankLabel, rowBlankLabel, roomNumLabel, logTypingLabel, roomPanelLabel, roomHintLabel, selectHintLabel;
	AntialiasedLabel mainLogLabel, signUpPanelLabel;
	JTextField ID, col, row, roomNum, colBlank, rowBlank, deleteNum, roomNumField, colField, rowField, colBlankField, rowBlankField;
	JPasswordField PASSWORD;;
	JButton aBtn, logInBtn, signUpBtn, backBtn2, signUpBtn2, exitButton, backBtn, mainBtn, logOutBtn, cRoom, dRoom, makeRoomBtn, dBtn,editBtn, refresh ,sprefresh, outBtn, outRoomStBtn;
	LoggedInPanel loggedInPanel;
	Font Title = new Font(null);
	ImageIcon icon;
	HttpCaller hc = new HttpCaller();
	JPanel logInLabelPanel = new JPanel();
	SignUpPanel signUpPanel = new SignUpPanel();
	JLabel[] logInLabels = new JLabel[signUpPanel.categories.length];
	JPanel seatsPanel;
	JSONArray roomsData;
	int currentRoomNumber, xDrag, yDrag, xPress, yPress;


	public MainLogin(){
		
		//Panel
		 //LogInPanel
//		icon = new ImageIcon();
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
		idLabel = new JLabel("");
		idLabel.setBounds(47,20,135,20);
		
		 //Password Label
		pwdLabel = new JLabel("");
		pwdLabel.setBounds(38,45,135,20);
		
		 //welcome Label
		welcome = new JLabel();
		welcome.setBounds(10,-130,300,300);
		welcome.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		welcome.setForeground(Color.white);
		welcome.setVisible(false);
		
		 // 메인타이틀
		mainLogLabel = new AntialiasedLabel("");
		mainLogLabel.setIcon(new ImageIcon(MainLogin.class.getResource("/image/mainlogin2.jpg")));
		mainLogLabel.setBounds(0, 0, 800, 500);
//		BufferedImage image = new BufferedImage();
		
		 // 회원가입

		 roomHintLabel = new AntialiasedLabel("");
	      roomHintLabel.setIcon(new ImageIcon
	            (MainLogin.class.getResource("/image/RoomHintLabel.jpg")));
	      roomHintLabel.setLayout(null);
	      roomHintLabel.setBounds(0,469,330,22);
		
		//----------------------------------------------------------------------------------------------
		
		
		//TextField
		
		 //ID
		ID = new JTextField(15);
		ID.setBorder(null);
		ID.setBounds(70,35,250,40);
		ID.addKeyListener(this);
		ID.setFont(new Font("SAN SERIF", Font.PLAIN, 25));
		ID.setForeground(new Color(125,124,130));

		 //Password
		PASSWORD = new JPasswordField(15);
		PASSWORD.setBorder(null);
		PASSWORD.setBounds(70,108,250,40);
		PASSWORD.addKeyListener(this);
		PASSWORD.setFont(new Font("SAN SERIF", Font.PLAIN, 25));
		PASSWORD.setForeground(new Color(125,124,130));
		
		logTypingLabel = new AntialiasedLabel("");
		logTypingLabel.setIcon(new ImageIcon(MainLogin.class.getResource("/image/logTypingLabel.jpg")));
		logTypingLabel.setBounds(0, 0, 300, 300);
		
		//----------------------------------------------------------------------------------------------
		
		
		//Buttons
		 //LogIn Button
		logInBtn = new JButton();
		logInBtn.setText("로그인");
		logInBtn.setBounds(160, 185, 100, 35);
		logInBtn.setBackground(new Color(135,77,162));
		logInBtn.setForeground(Color.white);
		logInBtn.setFont(new Font("HY견고딕", Font.PLAIN, 14));
		logInBtn.addActionListener(this);
		logInBtn.setBorderPainted(false);
		
		//main Button
		mainBtn = new JButton("");
		mainBtn.setText("뒤로가기");
		mainBtn.setBounds(600, 468, 84, 25);
		mainBtn.setBackground(new Color(135,77,162));
		mainBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		mainBtn.setForeground(Color.white);
		mainBtn.setBorderPainted(false);
		
		
		
		mainBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				editBtn.setVisible(false);
				sprefresh.setVisible(false);
				refresh.setVisible(true);
				deleteSeats();
				addAdminBtn();
				add(roomHintLabel);
				
				mainBtn.setVisible(false);
				roomPanelLabel.setVisible(false);
				currentRoomNumber=0;
				refreshLoggedInPanel();
				remove(selectHintLabel);
			}
		});
		
		
		//create Room Button
		cRoom = new JButton("방만들기");
		cRoom.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		cRoom.setForeground(new Color(135,77,162));
		cRoom.setBackground(Color.white);
		cRoom.setBounds(505,7,90,25);
		cRoom.setBorderPainted(false);
		cRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new CreateRoom();
			}			
		});
		
		//delete Room Button
		dRoom = new JButton("방지우기");
		dRoom.setBorderPainted(false);
		dRoom.setBounds(620,7,90,25);
		dRoom.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		dRoom.setForeground(new Color(135,77,162));
		dRoom.setBackground(Color.white);
		dRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new DeleteRoom();
            }
         });
		
		//admin button
		aBtn = new JButton("관리자 권한 부여");
		aBtn.setBorderPainted(false);
		aBtn.setBounds(350,7,130,25);
		aBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		aBtn.setForeground(new Color(135,77,162));
		aBtn.setBackground(Color.white);
		aBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new GrantAdmin();
			}
			
		});
		
		 //SignUp Button
		signUpBtn = new JButton("회원가입");
		signUpBtn.setBounds(50, 185, 100, 35);
		signUpBtn.setBackground(Color.LIGHT_GRAY);
		signUpBtn.setFont(new Font("HY견고딕", Font.PLAIN, 14));
		signUpBtn.setBorderPainted(false); // 버튼 테두리 없애기
		signUpBtn.addActionListener(this);
		
		 //Exit Button
		exitButton = new JButton("");
		exitButton.setBounds(770, 10, 20, 20);
		exitButton.setBackground(new Color(255,130,130));
		exitButton.setBorderPainted(false);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//refresh button
	      refresh = new JButton("새로고침");
	      refresh.setBounds(500, 468, 84, 25);
	      refresh.setForeground(Color.white);
	      refresh.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	      refresh.setBackground(new Color(135,77,162));
	      refresh.setBorderPainted(false); // 버튼 테두리 없애기
	      refresh.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e) {
	        	 loggedInPanel.setVisible(false);
	        	 remove(roomHintLabel);
	        	 refreshLoggedInPanel();
	        	 
	         }
	         
	      });
	      refresh.setVisible(false);
	      add(refresh);
	      
	      
	      
	      
	      //sprefresh button
	      
	      sprefresh=new JButton("새로고침");
	      sprefresh.setBounds(500,468,84,25);
	      sprefresh.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	      sprefresh.setForeground(Color.white);
	      sprefresh.setBackground(new Color(135,77,162));
	      
	      sprefresh.setBorderPainted(false); // 버튼 테두리 없애기
	      sprefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				seatsPanel.setVisible(false);
				remove(selectHintLabel);
				addSeats(currentRoomNumber);
				
			}
	    	  
	      });
	      
	      sprefresh.setVisible(false);
	      add(sprefresh);
		 //LogOutButton
		logOutBtn = new JButton("로그아웃");
		logOutBtn.setBorderPainted(false);
		logOutBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		logOutBtn.setForeground(Color.white);
		logOutBtn.setBackground(new Color(135,77,162));
		logOutBtn.setVisible(true);
		logOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
//				remove(sprefresh);
//				remove(editBtn);
//				remove(roomHintLabel);
//				remove(selectHintLabel);
				editBtn.setVisible(false);
				roomHintLabel.setVisible(false);
				if (selectHintLabel != null)
				selectHintLabel.setVisible(false);
				deleteLoggedInPanel();
				hc.clearData();
				if (seatsPanel != null) deleteSeats();
				deleteAdminBtn();
				welcome.setVisible(false);
				if(cRoomPanel != null)
				cRoomPanel.setVisible(false);
				sprefresh.setVisible(false);
				refresh.setVisible(false);
				addMainLogIn();
				remove(roomPanelLabel);
				
				
			}
			
		});
		logOutBtn.setBounds(699, 468, 84, 25);
		
		
		//회원가입 창 입력항목 설정
		for(int i = 0; i < signUpPanel.categories.length; i++) {
			logInLabels[i] = new JLabel(signUpPanel.categories[i]);
			logInLabels[i].setHorizontalAlignment(JLabel.RIGHT);
			logInLabelPanel.add(logInLabels[i]);
		}
		
		//----------------------------------------------------------------------------------------------
		
		
		
		//Panel Setting

		logInPanel.setLayout(null);
		logInPanel.setBounds(450,160,350,250);
		logInPanel.setBackground(new Color(255,255,255));
		logInPanel.add(ID);
		logInPanel.add(PASSWORD);
		logInPanel.add(idLabel);
		logInPanel.add(pwdLabel);
		logInPanel.add(logInBtn);
		logInPanel.add(signUpBtn);
		logInPanel.add(logTypingLabel);
		
		
		
		//----------------------------------------------------------------------------------------------
		
		
		//Frame Setting
		this.setLayout(null);
		this.add(logInPanel);
		this.add(exitButton);
		this.add(imagePanel);
		this.add(mainLogLabel);
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
//		this.setResizable(true);
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			    xDrag = e.getX();
			    yDrag = e.getY();
			    JFrame sFrame = (JFrame) e.getSource();
			    sFrame.setLocation(sFrame.getLocation().x+xDrag-xPress, 
			    sFrame.getLocation().y+yDrag-yPress);
			 }
			 @Override
			 public void mouseMoved(MouseEvent e) {
			     xPress = e.getX();
			     yPress = e.getY();
			  }
			
		});
		this.setUndecorated(true);
		this.setVisible(true);
	}

	public void refreshLoggedInPanel() {
		roomsData = new JSONObject(hc.getAllRoom()).getJSONObject("data").getJSONArray("roomsData");
	    roomsData = sortJsonArray(roomsData, "roomNum");
		loggedInPanel = new LoggedInPanel(roomsData);
		
		addActionListenerToButtons();
		
		mainBtn.setVisible(false);
		roomPanelLabel.setVisible(false);
		addLoggedInPanel();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		imagePanel.setVisible(true);
		
		//로그인 버튼 눌렀을 때
		if(e.getSource()==logInBtn) {
			hc.postLogIn(ID.getText(), PASSWORD.getPassword());
			add(mainBtn);
			addAdminBtn();
			
			//로그인 된 창에서 뒤로가기 눌렀을 때
			editBtn = new JButton("방설정변경");
	        editBtn.setBounds(630, 7, 105, 25);
	        editBtn.setBorderPainted(false);
			editBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
			editBtn.setForeground(new Color(135,77,162));
			editBtn.setBackground(Color.white);
	        add(editBtn);
	        editBtn.setVisible(false);
	        editBtn.addActionListener(new ActionListener() {
	        	
	        	@Override
        		public void actionPerformed(ActionEvent e) {
	        		
	        		
        			JSONObject roomData = new JSONObject(hc.getOneRoom(currentRoomNumber)).getJSONObject("data").getJSONObject("roomData");
        			new UpdateRoom(currentRoomNumber, roomData);
        			
        		}
	        });
	        
	        
			mainBtn.setVisible(false);
			//로그인 정보가 일치할 때
			if(hc.isLoggedIn()) {
				roomsData = new JSONObject(hc.getAllRoom()).getJSONObject("data").getJSONArray("roomsData");
			    roomsData = sortJsonArray(roomsData, "roomNum");
				loggedInPanel = new LoggedInPanel(roomsData);
				refresh.setVisible(true);
				sprefresh.setVisible(false);
				
				deleteMainLogIn();
				setLogInTextEmpty();
				addLoggedInPanel();

				//로그인 했을 때 생기는 Buttons
				
				addActionListenerToButtons();
				
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

//			logInLabelPanel.setLayout(new GridLayout(6,0,10,10));
//			logInLabelPanel.setBounds(200,130,130,200);
//			logInLabelPanel.setBackground(Color.white);
			
			
			//회원가입창의 버튼
			
			signUpBtn2 = new JButton("회원가입");
			backBtn = new JButton("뒤로가기");
			
			
			
			signUpBtn2.setBackground(Color.LIGHT_GRAY);
			signUpBtn2.setBorderPainted(false);
			signUpBtn2.setFont(new Font("HY견고딕", Font.PLAIN, 12));
			backBtn.setBackground(new Color(135,77,162));
			backBtn.setBorderPainted(false);
			backBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
			backBtn.setForeground(Color.white);
			
			signUpBtnPanel.setBounds(515,420,180,35);
			signUpBtnPanel.setBackground(Color.white);
			
			signUpMainPanel = new JPanel();
			signUpMainPanel.setSize(400,300);		
			
			signUpPanel.setBackground(new Color(255,255,255,0));
			signUpMainPanel.setBounds(350,550,300,200);
			
			signUpPanel.add(signUpMainPanel);			
			signUpBtnPanel.add(signUpBtn2);
			signUpBtnPanel.add(backBtn);
			signUpBtnPanel.add(signUpMainPanel);

			
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
		                        signUpPanel.pwdFields[0].getPassword(), 
		                        signUpPanel.fields[0].getText(), 
		                        yjuNum, 
		                        signUpPanel.fields[5].getText());
		               			JSONObject jo = new JSONObject(res);
		               			
					if (!Arrays.equals(signUpPanel.pwdFields[0].getPassword(), signUpPanel.pwdFields[1].getPassword())) {
						JOptionPane.showInternalMessageDialog(null, "비밀번호가 동일하지 않습니다.", "Error",1);
						return;
					}
					else if (jo.isNull("status")) {
						JOptionPane.showInternalMessageDialog(null, "회원가입이 완료되었습니다.\n 다시 로그인 해주십시오.","회원가입 완료",1);
						for(int i = 0; i < signUpPanel.categories.length; i++) {
							logInLabels[i].setVisible(false);
						}
						deleteSignUpPanel();
						deleteInfo();
						addMainLogIn();
						return;
					} else {
						JOptionPane.showInternalMessageDialog(null, jo.getString("message"), "Error",1);
						return;
					}
				}				
			});
			
			//뒤로가기 버튼
			backBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deleteSignUpPanel();
					deleteInfo();
					addMainLogIn();
//					setLogInTextEmpty();
				}
			});
			deleteMainLogIn();
			addSignUpPanel();
		}	
	}
	
	
	private void addActionListenerToButtons() {

		for(int i = 0; i < loggedInPanel.boxCount; i++) {
			int roomNum = roomsData.getJSONObject(i).getInt("roomNum");
			if (roomsData.getJSONObject(i).isNull("acceptDate")) {
				loggedInPanel.reserveBtn[i].addActionListener(new ActionListener() {
//									//예약버튼 눌렀을 때
//									@Override
					public void actionPerformed(ActionEvent e) {
//										remove(loggedInPanel);
//										deleteLoggedInPanel();
						if (hc.isAdmin()) {
							editBtn.setVisible(true);
						}
//						sprefresh.setVisible(true);
						loggedInPanel.setVisible(false);
						addSeats(roomNum);
						deleteAdminBtn();
						refresh.setVisible(false);
						mainBtn.setVisible(true);
						sprefresh.setVisible(true);
						currentRoomNumber=roomNum;
					}
				});
			} else {
				if (!hc.isAdmin()) {
					loggedInPanel.reserveBtn[i].addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(null, "아직 예약할 수 없습니다.", "Message", JOptionPane.ERROR_MESSAGE);
							sprefresh.setVisible(true);
						}
						
					});
				} else {
					loggedInPanel.reserveBtn[i].addActionListener(new ActionListener() {
//						//예약버튼 눌렀을 때
//						@Override
						public void actionPerformed(ActionEvent e) {
				//							remove(loggedInPanel);
				//							deleteLoggedInPanel();
							if (hc.isAdmin()) {
								editBtn.setVisible(true);
							}
							loggedInPanel.setVisible(false);
							addSeats(roomNum);
							deleteAdminBtn();
							refresh.setVisible(false);
							mainBtn.setVisible(true);
							sprefresh.setVisible(true);
							currentRoomNumber=roomNum;
						}
					});
				}
			}
		}
		
	}

	//changes display
	
	public void setLogInTextEmpty() {
		ID.setText("");
		PASSWORD.setText("");
	}
	
	public void addSignUpPanel() {
		add(signUpPanel);
		add(logInLabelPanel);
		add(signUpBtnPanel);
		add(signUpPanelLabel);
		logInLabelPanel.setVisible(true);
		signUpPanel.setVisible(true);
	}
	
	public void deleteSignUpPanel() {
		logInLabelPanel.setVisible(false);
		signUpPanel.setVisible(false);
		signUpPanelLabel.setVisible(false);
		remove(signUpBtnPanel);
	}
	
	public void addLoggedInPanel() {
		add(welcome);
		welcome.setVisible(true);
		loggedInPanel.setVisible(true);
		welcome.setText(hc.getName() + "님 반갑습니다.");
		add(logOutBtn);
		add(loggedInPanel);	
		
		if(hc.isAdmin()) {
			add(cRoom);
			add(dRoom);
			add(aBtn);
		}
		add(roomHintLabel);
		roomPanelLabel = new AntialiasedLabel("");
		roomPanelLabel.setIcon(new ImageIcon(MainLogin.class.getResource("/image/room.jpg")));
		roomPanelLabel.setBounds(0, 0, 800, 500);
		add(roomPanelLabel);
	}
	
	class CreateRoom extends JFrame implements ActionListener{
	       JTextField[] fields;
//	       String[] fieldsName = {"roomNum", "year", "month", "date", "hour", "minute"};
	       
	       JLabel[] labels;
	       String[] labelsName = {"강의실 호수", "년", "월", "일", "시간","분"};
	       
	       //초기화 날짜 설정
	       UtilDateModel model = new UtilDateModel();
	       JDatePanelImpl datePanel = new JDatePanelImpl(model);
	       JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	       JComboBox<String> resetHour = new JComboBox<>();
	       JComboBox<String> resetMinute = new JComboBox<>();
	       JLabel hour = new JLabel("시");
	       JLabel minute = new JLabel("분");
	       JCheckBox shuffle;
	       JComboBox<String> weekBox;
	       JTextField weekDelayField;
	       JComboBox<String> monthWeekBox;
	       JComboBox<String> dayBox;
	       JTextField monthResetDelayField;
	       JTextField noResetDelayField;
	       
	       //예약시작시간 설정
	       UtilDateModel model2 = new UtilDateModel();
	       JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
	       JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2);
	       JComboBox<String> startHour = new JComboBox<>();
	       JComboBox<String> startMinute = new JComboBox<>();
	       JLabel hour2 = new JLabel("시");
	       JLabel minute2 = new JLabel("분");
	       
	       //3번째항목 패널
	       JPanel p1 = new JPanel();
	       JPanel p2 = new JPanel();
	       JPanel p3 = new JPanel();
//	       JButton b1 = new JButton();
//	       JButton b2 = new JButton();
//	       JButton b3 = new JButton();
	       
	       //옵션 리스트
	       JComboBox<String> options = new JComboBox<>();
	       
	       
	      public CreateRoom() {
	      setTitle("강의실 예약하기");
	      setSize(900,450);
	      setLayout(null);
	      roomNumField = new JTextField();
	      roomNumField.setBounds(115,35,150,25);
	      roomNumField.setToolTipText("숫자로만 입력하세요.");
	      roomNumLabel = new JLabel("강의실 호수");
	      roomNumLabel.setBounds(38,35,70,25);
	      roomNumLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(roomNumLabel);
	      add(roomNumField);
	      
	      colField = new JTextField();
	      colField.setBounds(115,82,150,25);
	      colField.setToolTipText("숫자로만 입력하세요.");
	      colLabel = new JLabel("열 수");
	      colLabel.setBounds(55,82,50,25);
	      colLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(colLabel);
	      add(colField);
	      

	      rowField = new JTextField();
	      rowField.setBounds(115,129,150,25);
	      rowField.setToolTipText("숫자로만 입력하세요.");
	      rowLabel = new JLabel("행 수");
	      rowLabel.setBounds(55,129,50,25);
	      rowLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(rowLabel);
	      add(rowField);
	      
	      
	      colBlankField = new JTextField();
	      colBlankField.setBounds(115,176,150,25);
	      colBlankLabel = new JLabel("열 띄우기");
	      colBlankLabel.setBounds(25,176,80,25);
	      colBlankLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(colBlankLabel);
	      add(colBlankField);
	      
	      rowBlankField = new JTextField();
	      rowBlankField.setBounds(115,223,150,25);
	      rowBlankLabel = new JLabel("행 띄우기");
	      rowBlankLabel.setBounds(25,223,80,25);
	      rowBlankLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(rowBlankLabel);
	      add(rowBlankField);
	      
	      makeRoomBtn = new JButton("방만들기");
	      makeRoomBtn.setBackground(Color.LIGHT_GRAY);
	      makeRoomBtn.setBorderPainted(false);
	      makeRoomBtn.addActionListener(this);
	      makeRoomBtn.setBounds(180,265,85,25);
	      add(makeRoomBtn);
	      
	      outRoomStBtn = new JButton("취소");
	      outRoomStBtn.setBackground(Color.LIGHT_GRAY);
	      outRoomStBtn.setBorderPainted(false);
	      outRoomStBtn.addActionListener(this);
	      outRoomStBtn.setBounds(60,265,85,25);
	      add(outRoomStBtn);
	      
//	         
//	         cRoomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//	         cRoomPanel.setLayout(new GridLayout(4,2,15,15));
//	         cRoomPanel.setBounds(63,78,500,350);
//	         add(cRoomPanel);
	      
	     //Reset Date  
	    add(datePicker);
	    datePicker.setBounds(340,35,200,30);
	   

	    JLabel selectDate = new JLabel("좌석초기화 날짜 선택");
	    selectDate.setBounds(380,4,140,30);
	    add(selectDate);
	     
	    for(int i = 0; i<24; i++) {
	       resetHour.addItem(String.valueOf(i));
	    }
	    resetHour.setBounds(350,74,42,28);
	    resetHour.setMaximumRowCount(5);
	    add(resetHour);
	   
	    for(int i = 0; i<12; i++) {
	       resetMinute.addItem(String.valueOf(i*5));
	    } 
	    resetMinute.setBounds(465,74,42,28);
	    resetMinute.setMaximumRowCount(5);
	    add(resetMinute);
	    
	    
	    hour.setBounds(395,74,35,28);
	    add(hour);
	    minute.setBounds(510,74,35,28);
	    add(minute);
	    
	    
	    
	     //start date
	      add(datePicker2);
	    datePicker2.setBounds(340,190,200,30);
	    
	    JLabel selectDate2 = new JLabel("예약시작 날짜 선택");
	    selectDate2.setBounds(382,159,140,30);
	    add(selectDate2);
	    
	    for(int i = 0; i<24; i++) {
	       startHour.addItem(String.valueOf(i));
	    }
	    startHour.setBounds(350,229,42,28);
	    startHour.setMaximumRowCount(5); 
	    add(startHour);
	   
	    for(int i = 0; i<12; i++) {
	       startMinute.addItem(String.valueOf(i*5));
	    } 
	    startMinute.setBounds(465,229,42,28);
	    startMinute.setMaximumRowCount(5);
	    add(startMinute);
	    
	    
	    hour2.setBounds(395,229,42,28);
	    add(hour2);
	    
	    minute2.setBounds(510,229,42,28);
	    add(minute2);

	    //shuffle checkBox
	    shuffle = new JCheckBox("  리셋 시 셔플");
	    shuffle.setFont(new Font("HY견고딕", Font.PLAIN, 11));
	    shuffle.setBounds(445,300,100,20);
	    shuffle.setHorizontalTextPosition(SwingConstants.RIGHT);
	    add(shuffle);
	    
	    
	    //3번째 항목들
	    options.addItem("주기적 초기화 설정안함");
	    options.addItem("주 단위 초기화");
	    options.addItem("월 단위 초기화");
	    
	    options.setBounds(620,35,220,25);
	    DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
	    listRenderer.setHorizontalAlignment(DefaultListCellRenderer. CENTER);
	    options.setRenderer(listRenderer);
	    
	    
	    
	    // 패널 설정
	    DefaultListCellRenderer right = new DefaultListCellRenderer();
	    right.setHorizontalAlignment(DefaultListCellRenderer. RIGHT);
	    
	    
	       //주 단위 설정
	      p1.setBounds(595, 80, 270, 300);
	      p1.setBorder(BorderFactory.createLineBorder(Color.magenta));
	      p1.setLayout(null);
	      
	      JLabel week = new JLabel("주 간격");
	      week.setBounds(70,25,50,20);
	      
	      JLabel weekDelay = new JLabel("오픈지연(분)");
	      weekDelay.setBounds(60,65,80,20);
	      
	      weekBox = new JComboBox<>();
	      for(int i = 1; i <5 ; i++) weekBox.addItem(String.valueOf(i));
	      weekBox.setBounds(70,27,80,20);
	      weekBox.setBounds(140,25,80,20);
	      
	      weekDelayField = new JTextField();
	      weekDelayField.setBounds(140,65,80,20);
	      
	      p1.add(week);
	      p1.add(weekDelay);
	      p1.add(weekBox);
	      p1.add(weekDelayField);
	      add(p1);
	      p1.setVisible(false);
	      
	      //월 단위 설정
	      p2.setBounds(595, 80, 270, 300);
	      p2.setBorder(BorderFactory.createLineBorder(Color.blue));
	      p2.setLayout(null);
	      
	      JLabel monthResetWeek = new JLabel("번째 주");
	      monthResetWeek.setBounds(155,25,50,20);
	      
	      JLabel day = new JLabel("요일");
	      day.setBounds(155,65,50,20);
	      
	      
	      JLabel monthResetDelay = new JLabel("오픈지연(분)");
	      monthResetDelay.setBounds(60,105,80,20);
	      monthWeekBox = new JComboBox<>();
	      for(int i = 1; i <5 ; i++) monthWeekBox.addItem(String.valueOf(i));
	      monthWeekBox.setRenderer(right);
	      monthWeekBox.setBounds(70,27,80,20);
	      
	      
	      
	      dayBox = new JComboBox<>();
	      String[] days = {"일","월","화","수","목","금","토"};
	      for(int i = 0; i < 7; i++) {         
	         dayBox.addItem(days[i]);
	      }
	      dayBox.setRenderer(right);
	      dayBox.setBounds(70,67,80,20);
	      
	      monthResetDelayField = new JTextField();
	      monthResetDelayField.setBounds(140,105,80,20);
	      
	      p2.add(monthResetDelay);
	      p2.add(day);
	      p2.add(monthResetWeek);
	      p2.add(monthWeekBox);
	      p2.add(dayBox);
	      p2.add(monthResetDelayField);
	      add(p2);
	      p2.setVisible(false);
	      
	      
	      //초기화 설정 안함
	      p3.setBounds(595, 80, 270, 300);
	      p3.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	      p3.setLayout(null);
	      
	      JLabel noResetDelay = new JLabel("오픈지연(분)");
	      noResetDelay.setBounds(60,105,80,20);
	      
	      noResetDelayField = new JTextField();
	      noResetDelayField.setBounds(140,105,80,20);
	      
	      p3.add(noResetDelay);
	      p3.add(noResetDelayField);
	      add(p3);
	      p3.setVisible(true);
	      
	      
	      
	    options.addActionListener(new ActionListener() {

	      @Override
	      public void actionPerformed(ActionEvent e) {
	         
	         
	         int index = options.getSelectedIndex();

	         if(index == 0) {
	            if(p3!=null) {
	            p1.setVisible(false);
	            p2.setVisible(false);
	            }
	            p3.setVisible(true);
	         }
	         else if(index==1) {
	            if(p2 != null) {
	               p2.setVisible(false);
	               p3.setVisible(false);
	            }
	            p1.setVisible(true);
	         }
	         else if(index==2) {
	         if(p1!=null) {
	         p1.setVisible(false);
	         p3.setVisible(false);
	         }
	         p2.setVisible(true);
	         }
	      }
	       
	    });
	    add(options);
	   
	    
	    
//	    b1.setBounds(650,35,35,35);
//	    b1.addActionListener(this);
	//    
	//    
//	    b2.setBounds(690,35,35,35);
//	    b3.setBounds(730,35,35,35);
//	    add(b1);
//	    add(b2);
//	    add(b3);
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			    xDrag = e.getX();
			    yDrag = e.getY();
			    JFrame sFrame = (JFrame) e.getSource();
			    sFrame.setLocation(sFrame.getLocation().x+xDrag-xPress, 
			    sFrame.getLocation().y+yDrag-yPress);
			 }
			 @Override
			 public void mouseMoved(MouseEvent e) {
			     xPress = e.getX();
			     yPress = e.getY();
			  }
			
		});
	      setLocationRelativeTo(null);
	      setUndecorated(true);
	      setVisible(true);
	      }
	      @Override
	      public void actionPerformed(ActionEvent e) {
	    	  
	    	  if (e.getSource() == outRoomStBtn) {
	    		  dispose();
	    	  } else {
		    	  
	  	        RoomCreator rc = new RoomCreator();
	  	         StringTokenizer colst = new StringTokenizer(colBlankField.getText(), ", ");
	  	         StringTokenizer rowst = new StringTokenizer(rowBlankField.getText(), ", ");
	  	         int[] colBlankArray = new int[colst.countTokens()];
	  	         int[] rowBlankArray = new int[rowst.countTokens()];
	  	         
	  	         for (int i=0; i<colBlankArray.length; i++) {
	  	            colBlankArray[i] = Integer.valueOf(colst.nextToken());
	  	         }
	  	         for (int i=0; i<rowBlankArray.length; i++) {
	  	            rowBlankArray[i] = Integer.valueOf(rowst.nextToken());
	  	         }
	  	         
	  	         int roomNum = Integer.valueOf(roomNumField.getText());
	  	         int col = Integer.valueOf(colField.getText());
	  	         int row = Integer.valueOf(rowField.getText());
	  	         int[] colBlank = colBlankArray.length == 0 ? null : colBlankArray;
	  	         int[] rowBlank = rowBlankArray.length == 0 ? null : rowBlankArray;
	  	         rc.setRoomNum(roomNum);
	  	         rc.setCol(col);
	  	         rc.setRow(row);
	  	         if (colBlank != null) {
	  	            rc.setColBlank(colBlank);
	  	         }
	  	         if (rowBlank != null) {
	  	            rc.setRowBlank(rowBlank);
	  	         }
	  	         
	  	         
	  	         Date resetDate = (Date) datePicker.getModel().getValue();
	  	         if (resetDate != null) {
	  	            resetDate.setHours(Integer.valueOf((String) resetHour.getSelectedItem() == "" ? "0" : (String) resetHour.getSelectedItem()));
	  	            resetDate.setMinutes(Integer.valueOf((String) resetMinute.getSelectedItem() == "" ? "0" : (String) resetMinute.getSelectedItem()));
	  	            resetDate.setSeconds(0);
	  	            rc.setResetDate(resetDate);
	  	         }
	  	         
	  	         Date acceptDate = (Date) datePicker2.getModel().getValue();
	  	         if (acceptDate != null) {
	  	            acceptDate.setHours(Integer.valueOf((String)startHour.getSelectedItem() == "" ? "0" : (String)startHour.getSelectedItem()));
	  	            acceptDate.setMinutes(Integer.valueOf((String) startMinute.getSelectedItem() == "" ? "0" : (String) startMinute.getSelectedItem()));
	  	            acceptDate.setSeconds(0);
	  	            rc.setAcceptDate(acceptDate);
	  	         }
	  	         
	  	         boolean isShuffle = shuffle.isSelected();
	  	         rc.setShuffle(isShuffle);
	  	         int openDeffer;
	  	         
	  	         int measure = options.getSelectedIndex();
	  	         if (measure == 1) {
	  	            measure = 0;
	  	            int weekendInterval = weekBox.getSelectedIndex()+1;
	  	            rc.setMeasure(measure);
	  	            rc.setWeekendInterval(weekendInterval);
	  	            if (!weekDelayField.getText().equals("")) {
	  			        openDeffer = Integer.valueOf(weekDelayField.getText());
	  			        rc.setOpenDeffer(openDeffer);
	  			     } else {
	  			        rc.setOpenDeffer(0);
	  			     }
	  	         }else if (measure == 2) {
	  	            measure = 1;
	  	            int weekNth = monthWeekBox.getSelectedIndex()+1;
	  	            int day = dayBox.getSelectedIndex();
	  	            rc.setWeekNth(weekNth);
	  	            rc.setMeasure(measure);
	  	            rc.setDay(day);
	  	            if (!monthResetDelayField.getText().equals("")) {
	  	 	           openDeffer = Integer.valueOf(monthResetDelayField.getText());
	  	 	           rc.setOpenDeffer(openDeffer);
	  	 	         } else {
	  	 	        	rc.setOpenDeffer(0);
	  	 	         }
	  	         }
	  	         
	  	         System.out.println(hc.postCreateRoom(rc));
	  	         loggedInPanel.setVisible(false);
	          	 refreshLoggedInPanel();
	  	         dispose();
	  	         
	  	      }
	    	  }
	    	  

	      
	   }
	
	class UpdateRoom extends JFrame implements ActionListener{
	       JTextField[] fields;
//	       String[] fieldsName = {"roomNum", "year", "month", "date", "hour", "minute"};
	       
	       JLabel[] labels;
	       String[] labelsName = {"강의실 호수", "년", "월", "일", "시간","분"};
	       
	       //초기화 날짜 설정
	       UtilDateModel model = new UtilDateModel();
	       JDatePanelImpl datePanel = new JDatePanelImpl(model);
	       JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	       JComboBox<String> resetHour = new JComboBox<>();
	       JComboBox<String> resetMinute = new JComboBox<>();
	       JLabel hour = new JLabel("시");
	       JLabel minute = new JLabel("분");
	       JCheckBox shuffle;
	       JComboBox<String> weekBox;
	       JTextField weekDelayField;
	       JComboBox<String> monthWeekBox;
	       JComboBox<String> dayBox;
	       JTextField monthResetDelayField;
	       JTextField noResetDelayField;
	       
	       //예약시작시간 설정
	       UtilDateModel model2 = new UtilDateModel();
	       JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
	       JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2);
	       JComboBox<String> startHour = new JComboBox<>();
	       JComboBox<String> startMinute = new JComboBox<>();
	       JLabel hour2 = new JLabel("시");
	       JLabel minute2 = new JLabel("분");
	       
	       //3번째항목 패널
	       JPanel p1 = new JPanel();
	       JPanel p2 = new JPanel();
	       JPanel p3 = new JPanel();
//	       JButton b1 = new JButton();
//	       JButton b2 = new JButton();
//	       JButton b3 = new JButton();
	       
	       //옵션 리스트
	       JComboBox<String> options = new JComboBox<>();
	       
	       
	      public UpdateRoom(int roomNum, JSONObject roomData) {
	      setTitle("강의실 예약하기");
	      setSize(900,450);
	      setLayout(null);
	      roomNumField = new JTextField();
	      roomNumField.setBounds(115,35,150,25);
	      roomNumField.setToolTipText("숫자로만 입력하세요.");
	      roomNumField.setEditable(false);
	      roomNumField.setText(String.valueOf(roomNum));
	      roomNumLabel = new JLabel("강의실 호수");
	      roomNumLabel.setBounds(38,35,70,25);
	      roomNumLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(roomNumLabel);
	      add(roomNumField);
	      
	      
	      colField = new JTextField();
	      colField.setBounds(115,82,150,25);
	      colField.setText(String.valueOf(roomData.getInt("column")));
	      colField.setToolTipText("숫자로만 입력하세요.");
	      colLabel = new JLabel("열 수");
	      colLabel.setBounds(55,82,50,25);
	      colLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(colLabel);
	      add(colField);
	      

	      rowField = new JTextField();
	      rowField.setBounds(115,129,150,25);
	      rowField.setText(String.valueOf(roomData.getInt("row")));
	      rowField.setToolTipText("숫자로만 입력하세요.");
	      rowLabel = new JLabel("행 수");
	      rowLabel.setBounds(55,129,50,25);
	      rowLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(rowLabel);
	      add(rowField);
	      
	      
	      colBlankField = new JTextField();
	      colBlankField.setBounds(115,176,150,25);
	      colBlankLabel = new JLabel("열 띄우기");
	      colBlankLabel.setBounds(25,176,80,25);
	      if (!roomData.isNull("columnBlankLine")) {  
	    	  JSONArray colArray = roomData.getJSONArray("columnBlankLine");
	    	  String colBlankText = "";
	    	  for (int i=0; i<colArray.length(); i++) {
	    		  colBlankText += colArray.get(i);
	    		  if (i != colArray.length()-1)
	    			  colBlankText += ", ";
	    	  }
	    	  colBlankField.setText(colBlankText);
	      }
	      colBlankLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(colBlankLabel);
	      add(colBlankField);
	      
	      rowBlankField = new JTextField();
	      rowBlankField.setBounds(115,223,150,25);
	      if (!roomData.isNull("rowBlankLine")) {
	    	  JSONArray rowArray = roomData.getJSONArray("rowBlankLine");
		      String rowBlankText = "";
		      for (int i=0; i<rowArray.length(); i++) {
		    	  rowBlankText += rowArray.get(i);
		    	  if (i != rowArray.length()-1)
		    	  rowBlankText += ", ";
		      }
		      rowBlankField.setText(rowBlankText);
	      }
	      
	      rowBlankLabel = new JLabel("행 띄우기");
	      rowBlankLabel.setBounds(25,223,80,25);
	      rowBlankLabel.setHorizontalAlignment(JLabel.RIGHT);
	      add(rowBlankLabel);
	      add(rowBlankField);
	     
	      makeRoomBtn = new JButton("설정변경");
	      makeRoomBtn.setBackground(Color.LIGHT_GRAY);
	      makeRoomBtn.setBorderPainted(false);
	      makeRoomBtn.addActionListener(this);
	      makeRoomBtn.setBounds(180,265,85,25);
	      add(makeRoomBtn);
	      
	      outRoomStBtn = new JButton("취소");
	      outRoomStBtn.setBackground(Color.LIGHT_GRAY);
	      outRoomStBtn.setBorderPainted(false);
	      outRoomStBtn.addActionListener(this);
	      outRoomStBtn.setBounds(60,265,85,25);
	      add(outRoomStBtn);
	     
	      
//	         
//	         cRoomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//	         cRoomPanel.setLayout(new GridLayout(4,2,15,15));
//	         cRoomPanel.setBounds(63,78,500,350);
//	         add(cRoomPanel);
	      
	     //Reset Date  
	    add(datePicker);
	    datePicker.setBounds(340,35,200,30);
	    model.setDate(2022, 7, 5);
	   

	    JLabel selectDate = new JLabel("좌석초기화 날짜 선택");
	    selectDate.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	    selectDate.setBounds(380,4,140,30);
	    add(selectDate);
	     
	    for(int i = 0; i<24; i++) {
	       resetHour.addItem(String.valueOf(i));
	    }
	    resetHour.setBounds(350,74,42,28);
	    resetHour.setMaximumRowCount(5);
	    add(resetHour);
	   
	    for(int i = 0; i<12; i++) {
	       resetMinute.addItem(String.valueOf(i*5));
	    } 
	    resetMinute.setBounds(465,74,42,28);
	    resetMinute.setMaximumRowCount(5);
//	    resetMinute.setSelectedIndex(anIndex);
	    add(resetMinute);
	    
	    
	    hour.setBounds(395,74,35,28);
	    add(hour);
	    minute.setBounds(510,74,35,28);
	    add(minute);
	    
	    
	    
	     //start date
	      add(datePicker2);
	    datePicker2.setBounds(340,190,200,30);
	    
	    JLabel selectDate2 = new JLabel("예약시작 날짜 선택");
	    selectDate2.setBounds(382,159,140,30);
	    add(selectDate2);
	    
	    for(int i = 0; i<24; i++) {
	       startHour.addItem(String.valueOf(i));
	    }
	    startHour.setBounds(350,229,42,28);
	    startHour.setMaximumRowCount(5); 
//	    startHour.setSelectedIndex(anIndex);
	    add(startHour);
	   
	    for(int i = 0; i<12; i++) {
	       startMinute.addItem(String.valueOf(i*5));
	    } 
	    startMinute.setBounds(465,229,42,28);
	    startMinute.setMaximumRowCount(5);
//	    startMinute.setSelectedIndex(anIndex);
	    add(startMinute);
	    
	    
	    hour2.setBounds(395,229,42,28);
	    add(hour2);
	    
	    minute2.setBounds(510,229,42,28);
	    add(minute2);

	    //shuffle checkBox
	    shuffle = new JCheckBox("  리셋 시 셔플");
	    shuffle.setBounds(445,300,100,20);
	    shuffle.setHorizontalTextPosition(SwingConstants.RIGHT);
	    if(roomData.getBoolean("isShuffle")==true) {
	    	shuffle.setSelected(true);
	    }else shuffle.setSelected(false);
	    add(shuffle);
	    
	    
	    //3번째 항목들
	    options.addItem("주기적 초기화 설정안함");
	    options.addItem("주 단위 초기화");
	    options.addItem("월 단위 초기화");
	    
	    options.setBounds(620,35,220,25);
	    DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
	    listRenderer.setHorizontalAlignment(DefaultListCellRenderer. CENTER);
	    options.setRenderer(listRenderer);
	    
	    
	    
	    // 패널 설정
	    DefaultListCellRenderer right = new DefaultListCellRenderer();
	    right.setHorizontalAlignment(DefaultListCellRenderer. RIGHT);
	    
	    
	       //주 단위 설정
	      p1.setBounds(595, 80, 270, 300);
	      p1.setBorder(BorderFactory.createLineBorder(Color.magenta));
	      p1.setLayout(null);
	      
	      JLabel week = new JLabel("주 간격");
	      week.setBounds(70,25,50,20);
	      
	      JLabel weekDelay = new JLabel("오픈지연(분)");
	      weekDelay.setBounds(60,65,80,20);
	      
	      weekBox = new JComboBox<>();
	      for(int i = 1; i <5 ; i++) weekBox.addItem(String.valueOf(i));
//	      weekBox.setSelectedIndex(roomData.);
	      weekBox.setBounds(140,25,80,20);
	      
	      
	      weekDelayField = new JTextField();
	      weekDelayField.setBounds(140,65,80,20);
	      if(!roomData.isNull("openDeffer"))
	      weekDelayField.setText(String.valueOf(roomData.getInt("openDeffer")));
	      
	      p1.add(week);
	      p1.add(weekDelay);
	      p1.add(weekBox);
	      p1.add(weekDelayField);
	      add(p1);
	      p1.setVisible(false);
	      
	      //월 단위 설정
	      p2.setBounds(595, 80, 270, 300);
	      p2.setBorder(BorderFactory.createLineBorder(Color.blue));
	      p2.setLayout(null);
	      
	      JLabel monthResetWeek = new JLabel("번째 주");
	      monthResetWeek.setBounds(155,25,50,20);
	      
	      
	      JLabel day = new JLabel("요일");
	      day.setBounds(155,65,50,20);
	      
	      
	      JLabel monthResetDelay = new JLabel("오픈지연(분)");
	      monthResetDelay.setBounds(60,105,80,20);
	      monthWeekBox = new JComboBox<>();	      

	      for(int i = 1; i <5 ; i++) monthWeekBox.addItem(String.valueOf(i));
	      monthWeekBox.setRenderer(right);
	      monthWeekBox.setBounds(70,27,80,20);
	      
	      
	      
	      dayBox = new JComboBox<>();
	      String[] days = {"일","월","화","수","목","금","토"};

	      for(int i = 0; i < 7; i++) {         
	         dayBox.addItem(days[i]);
	      }
	      dayBox.setRenderer(right);
	      dayBox.setBounds(70,67,80,20);
	      
	      monthResetDelayField = new JTextField();
	      monthResetDelayField.setBounds(140,105,80,20);
	      if(!roomData.isNull("openDeffer"))
		      monthResetDelayField.setText(String.valueOf(roomData.getInt("openDeffer")));
	      
	      p2.add(monthResetDelay);
	      p2.add(day);
	      p2.add(monthResetWeek);
	      p2.add(monthWeekBox);
	      p2.add(dayBox);
	      p2.add(monthResetDelayField);
	      add(p2);
	      p2.setVisible(false);
	      
	      
	      //초기화 설정 안함
	      p3.setBounds(595, 80, 270, 300);
	      p3.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	      p3.setLayout(null);
	      
	      JLabel noResetDelay = new JLabel("오픈지연(분)");
	      noResetDelay.setBounds(60,105,80,20);
	      
	      noResetDelayField = new JTextField();
	      noResetDelayField.setBounds(140,105,80,20);
	      if(!roomData.isNull("openDeffer"))
		      noResetDelayField.setText(String.valueOf(roomData.getInt("openDeffer")));
	      
	      p3.add(noResetDelay);
	      p3.add(noResetDelayField);
	      add(p3);
	      p3.setVisible(true);
	      
	      
	      if (!roomData.isNull("measure")) {
	    	  if(roomData.getInt("measure")==0) {
	    		  
	    		  options.setSelectedIndex(1);
	    		  weekBox.setSelectedIndex(roomData.getInt("weekendInterval")-1);
	    		  p3.setVisible(false);
	    		  p1.setVisible(true);
	    	  }else if (roomData.getInt("measure")==1){
	    		  options.setSelectedIndex(2);
	    		  monthWeekBox.setSelectedIndex(roomData.getInt("weekNth")-1);
	    		  dayBox.setSelectedIndex(roomData.getInt("day")-1);
	    		  p3.setVisible(false);
	    		  p2.setVisible(true);
	    	  }
	    	  
	      }else {
    		  options.setSelectedIndex(0);
    	  }
	      
	      
	    options.addActionListener(new ActionListener() {

	      @Override
	      public void actionPerformed(ActionEvent e) {
	         
	         
	         int index = options.getSelectedIndex();

	         if(index == 0) {
	            if(p3!=null) {
	            p1.setVisible(false);
	            p2.setVisible(false);
	            }
	            p3.setVisible(true);
	         }
	         else if(index==1) {
	            if(p2 != null) {
	               p2.setVisible(false);
	               p3.setVisible(false);
	            }
	            p1.setVisible(true);
	         }
	         else if(index==2) {
	         if(p1!=null) {
	         p1.setVisible(false);
	         p3.setVisible(false);
	         }
	         p2.setVisible(true);
	         }
	      }
	       
	    });
	    add(options);
	   
	    
	    
//	    b1.setBounds(650,35,35,35);
//	    b1.addActionListener(this);
	//    
	//    
//	    b2.setBounds(690,35,35,35);
//	    b3.setBounds(730,35,35,35);
//	    add(b1);
//	    add(b2);
//	    add(b3);
	    
	    if (!roomData.isNull("resetDate")) {
	    	
	    	
	    	
	    	LocalDateTime dateTime = LocalDateTime.from(

			        Instant.from(
			            DateTimeFormatter.ISO_DATE_TIME.parse(roomData.getString("resetDate"))
			        ).atZone(ZoneId.of("Asia/Seoul"))
			    );
			    String currentResetStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일(E) HH시 mm분"));
	    	
			    JLabel currentResetDate = new JLabel("현재 초기화 날짜 : " + currentResetStr);
	    	
	    	add(currentResetDate);
	    	currentResetDate.setBounds(50,315,300,20);
	    }
	    
	    if (!roomData.isNull("acceptDate")) {
	    	
	    	LocalDateTime dateTime = LocalDateTime.from(

			        Instant.from(
			            DateTimeFormatter.ISO_DATE_TIME.parse(roomData.getString("acceptDate"))
			        ).atZone(ZoneId.of("Asia/Seoul"))
			    );
			    String currentAcceptStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일(E) HH시 mm분"));
	    	
	    	JLabel currentAcceptDate = new JLabel("현재 예약 시작 날짜 : " + currentAcceptStr);
	    	add(currentAcceptDate);
	    	currentAcceptDate.setBounds(50,340,300,20);
	    }
	    
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			    xDrag = e.getX();
			    yDrag = e.getY();
			    JFrame sFrame = (JFrame) e.getSource();
			    sFrame.setLocation(sFrame.getLocation().x+xDrag-xPress, 
			    sFrame.getLocation().y+yDrag-yPress);
			 }
			 @Override
			 public void mouseMoved(MouseEvent e) {
			     xPress = e.getX();
			     yPress = e.getY();
			  }
			
		});
	      setLocationRelativeTo(null);
	      setUndecorated(true);
	      setVisible(true);
	      }
	      @Override
	      public void actionPerformed(ActionEvent e) {
	    	  
	    	 if (e.getSource() == outRoomStBtn) {
	    		 dispose();
	    	 } else {
	 	        RoomCreator rc = new RoomCreator();
		         StringTokenizer colst = new StringTokenizer(colBlankField.getText(), ", ");
		         StringTokenizer rowst = new StringTokenizer(rowBlankField.getText(), ", ");
		         int[] colBlankArray = new int[colst.countTokens()];
		         int[] rowBlankArray = new int[rowst.countTokens()];
		         
		         for (int i=0; i<colBlankArray.length; i++) {
		            colBlankArray[i] = Integer.valueOf(colst.nextToken());
		         }
		         for (int i=0; i<rowBlankArray.length; i++) {
		            rowBlankArray[i] = Integer.valueOf(rowst.nextToken());
		         }
		         
		         int roomNum = Integer.valueOf(roomNumField.getText());
		         int col = Integer.valueOf(colField.getText());
		         int row = Integer.valueOf(rowField.getText());
		         
		         
		         int[] colBlank = colBlankArray.length == 0 ? null : colBlankArray;
		         int[] rowBlank = rowBlankArray.length == 0 ? null : rowBlankArray;
		         rc.setRoomNum(roomNum);
		         rc.setCol(col);
		         rc.setRow(row);
		         if (colBlank != null) {
		            rc.setColBlank(colBlank);
		         } else {
		        	 rc.setColBlank(new int[0]);
		         }
		         if (rowBlank != null) {
		            rc.setRowBlank(rowBlank);
		         } else {
		        	 rc.setRowBlank(new int[0]);
		         }
		         
		         
		         Date resetDate = (Date) datePicker.getModel().getValue();
		         if (resetDate != null) {
		            resetDate.setHours(Integer.valueOf((String) resetHour.getSelectedItem() == "" ? "0" : (String) resetHour.getSelectedItem()));
		            resetDate.setMinutes(Integer.valueOf((String) resetMinute.getSelectedItem() == "" ? "0" : (String) resetMinute.getSelectedItem()));
		            resetDate.setSeconds(0);
		            rc.setResetDate(resetDate);
		         } else {
		        	 rc.setResetDate(null);
		         }
		         
		         Date acceptDate = (Date) datePicker2.getModel().getValue();
		         if (acceptDate != null) {
		            acceptDate.setHours(Integer.valueOf((String)startHour.getSelectedItem() == "" ? "0" : (String)startHour.getSelectedItem()));
		            acceptDate.setMinutes(Integer.valueOf((String) startMinute.getSelectedItem() == "" ? "0" : (String) startMinute.getSelectedItem()));
		            acceptDate.setSeconds(0);
		            rc.setAcceptDate(acceptDate);
		         } else {
		        	 rc.setResetDate(null);
		         }
		         
		         boolean isShuffle = shuffle.isSelected();
		         rc.setShuffle(isShuffle);
		         int openDeffer;
		         
		         int measure = options.getSelectedIndex();
		         if (measure == 1) {
		            measure = 0;
		            int weekendInterval = weekBox.getSelectedIndex()+1;
		            rc.setMeasure(measure);
		            rc.setWeekendInterval(weekendInterval);
		            if (!weekDelayField.getText().equals("")) {
				        openDeffer = Integer.valueOf(weekDelayField.getText());
				        rc.setOpenDeffer(openDeffer);
				     } else {
				        rc.setOpenDeffer(0);
				     }
		         }else if (measure == 2) {
		            measure = 1;
		            int weekNth = monthWeekBox.getSelectedIndex()+1;
		            int day = dayBox.getSelectedIndex();
		            rc.setWeekNth(weekNth);
		            rc.setMeasure(measure);
		            rc.setDay(day);
		            if (!monthResetDelayField.getText().equals("")) {
		 	           openDeffer = Integer.valueOf(monthResetDelayField.getText());
		 	           rc.setOpenDeffer(openDeffer);
		 	         } else {
		 	        	rc.setOpenDeffer(0);
		 	         }
		         }else if (measure == 0) {
		        	 measure = -1;
		        	 rc.setMeasure(measure);
		         }
		         
		         
		         
		         
		         
		         System.out.println(hc.patchOneRoom(rc));
		         seatsPanel.setVisible(false);
		         addSeats(currentRoomNumber);
		         dispose();
		         
		      }
	    	 }

	      
	   }
	
	class DeleteRoom extends JFrame implements ActionListener{
	      
	      public DeleteRoom() {
	         setSize(320, 150);
	         
	         JLabel number = new JLabel("강의실 호수");
	         number.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	         number.setBounds(30,50,100,25);
	         
	         deleteNum = new JTextField();
	         deleteNum.setToolTipText("숫자로만 입력하세요.");
	         deleteNum.setBounds(100,50,190,25);
	         
	         dBtn = new JButton("방 지우기");
	         dBtn.setBounds(40,100,100,20);
	         dBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	         dBtn.setForeground(Color.BLACK);
	         dBtn.setBackground(Color.LIGHT_GRAY);
	         dBtn.addActionListener(this);
	         dBtn.setBorderPainted(false);
	         
	         outBtn = new JButton("취소");
	         outBtn.setBounds(180,100,100,20);
	         outBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	         outBtn.setForeground(Color.BLACK);
	         outBtn.setBackground(Color.LIGHT_GRAY);
	         outBtn.addActionListener(this);
	         outBtn.setBorderPainted(false);
	         
	         
	         add(number);
	         add(deleteNum);
	         add(dBtn);
	         add(outBtn);
	         setLayout(null);
	         setLocationRelativeTo(null);
	         setUndecorated(true);
	         setVisible(true);
	      }
	      
	      @Override
	      public void actionPerformed(ActionEvent e) {
	         System.out.println(deleteNum.getText());
	         if (e.getSource() == outBtn) {
	        	 dispose();
	         }
	         if (e.getSource() ==dBtn) {
		         if(deleteNum.getText().equals("")) {
			            JOptionPane.showMessageDialog(null, "호수를 입력하세요.", "빈 내용", JOptionPane.WARNING_MESSAGE);
			         }else {
			            hc.deleteRoom(Integer.valueOf(deleteNum.getText()));
			            JOptionPane.showMessageDialog(null, "강의실이 삭제되었습니다.", "삭제", JOptionPane.PLAIN_MESSAGE);
//			            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    		loggedInPanel.setVisible(false);
			            refreshLoggedInPanel();
			            dispose();
			         } 
	         }

	         
	      }
	      
	   }
	
	class GrantAdmin extends JFrame implements ActionListener{
	      
	      public GrantAdmin() {
	         setSize(320, 150);
	         
	         JLabel number = new JLabel("User ID");
	         number.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	         number.setBounds(30,50,100,25);
	         
	         deleteNum = new JTextField();
	         deleteNum.setToolTipText("관리자 권한을 부여할 유저 ID를 입력하세요.");
         
	         deleteNum.setBounds(100,50,190,25);
	         
	         dBtn = new JButton("권한부여");
	         dBtn.setBounds(40,100,100,20);
	         dBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	         dBtn.setForeground(Color.BLACK);
	         dBtn.setBackground(Color.LIGHT_GRAY);
	         dBtn.addActionListener(this);
	         dBtn.setBorderPainted(false);
	         
	         outBtn = new JButton("취소");
	         outBtn.setBounds(180,100,100,20);
	         outBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	         outBtn.setForeground(Color.BLACK);
	         outBtn.setBackground(Color.LIGHT_GRAY);
	         outBtn.addActionListener(this);
	         outBtn.setBorderPainted(false);
	         
	         add(number);
	         add(deleteNum);
	         add(dBtn);
	         add(outBtn);
	         setLayout(null);
	         setLocationRelativeTo(null);
	         setUndecorated(true);
	         setVisible(true);
	      }
	      
	      @Override
	      public void actionPerformed(ActionEvent e) {
	         System.out.println(deleteNum.getText());
	         if (e.getSource() == outBtn) {
	        	 dispose();
	         }
	         if (e.getSource() == dBtn) {
		         if(deleteNum.getText().equals("")) {
			            JOptionPane.showMessageDialog(null, "아이디를 입력하세요", "빈 내용", JOptionPane.WARNING_MESSAGE);
			         }else {
			            System.out.println(hc.patchGrantAdmin(deleteNum.getText()));
			            JOptionPane.showMessageDialog(null, "해당 유저에게 권한이 부여되었습니다.", "완료", JOptionPane.PLAIN_MESSAGE);
//			            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    		loggedInPanel.setVisible(false);
			            refreshLoggedInPanel();
			            dispose();
			         }
 
	         }

	         
	      }
	      
	   }
	
	
	public void deleteLoggedInPanel() {
		remove(loggedInPanel);
//		new createRoom();
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
	
	public void addSeats(int roomNum) {
		JSONObject obj = new JSONObject(hc.getOneRoom(roomNum)).getJSONObject("data").getJSONObject("roomData");
		int col = obj.getInt("column");
		int row = obj.getInt("row");
		JSONArray jcolbl = obj.getJSONArray("columnBlankLine");
		JSONArray jrowbl = obj.getJSONArray("rowBlankLine");
		JSONObject rsvd = obj.getJSONObject("reservedData");
		int[] colbl = new int[jcolbl.length()];
		int[] rowbl = new int[jrowbl.length()];
		int[] reservedData = new int[rsvd.length()];
		for (int i = 0; i<colbl.length; i++) {
			colbl[i] = jcolbl.getInt(i);
		}
		for (int i = 0; i<rowbl.length; i++) {
			rowbl[i] = jrowbl.getInt(i);
		}
		Iterator<String> iter = rsvd.keys();
		for (int i=0; i<reservedData.length; i++) {
			reservedData[i] = Integer.valueOf((String) iter.next());
		}
		seatsPanel = new SeatsPanel(col, row, colbl, rowbl, roomNum, reservedData, hc);
//		seatsPanel.setVisible(true);
		seatsPanel.setBounds(10, 50, 800, 400);
		seatsPanel.setBackground(Color.white);
		
		
	    selectHintLabel = new AntialiasedLabel("");
	    selectHintLabel.setIcon(new ImageIcon(MainLogin.class.getResource("/image/SelectHintLabel.jpg")));
	    selectHintLabel.setLayout(null);
	    selectHintLabel.setBounds(0,468,350,22);
	    add(selectHintLabel);
	    remove(roomHintLabel);
		remove(roomPanelLabel);
		add(seatsPanel);
		add(roomPanelLabel);
//		seatsPanel.setBackground(Color.gray.brighter());
		
		seatsPanel.setVisible(true);
	}
	
	public void deleteSeats() {

		mainBtn.setVisible(false);
		seatsPanel.setVisible(false);
	}
	
	public void addReserveBtn() {
		for(int i=0; i <loggedInPanel.boxCount; i++) {
			loggedInPanel.reserveBtn[i].setVisible(false);
		}
	}
	
	public void deleteReserveBtn() {
		for(int i=0; i <loggedInPanel.boxCount; i++) {
			loggedInPanel.reserveBtn[i].setVisible(true);
		}
	}
	
	public void deleteAdminBtn() {
		aBtn.setVisible(false);
		cRoom.setVisible(false);
		dRoom.setVisible(false);
	}
	
	public void addAdminBtn() {
		if(hc.isAdmin()) {
		aBtn.setVisible(true);
		cRoom.setVisible(true);
		dRoom.setVisible(true);
		}
	}
	
	private <T extends Comparable<T>> JSONArray sortJsonArray(JSONArray jsonArr, String KEY_NAME) {
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		JSONArray sortedJsonArray = new JSONArray();
	    for (int i = 0; i < jsonArr.length(); i++) {
	        jsonValues.add(jsonArr.getJSONObject(i));
	    }
	    Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        @SuppressWarnings("unchecked")
			@Override
	        public int compare(JSONObject a, JSONObject b) {
	        	
	        	T valA = (T) a.get(KEY_NAME);
	            T valB = (T) b.get(KEY_NAME);

	            return valA.compareTo(valB);
	        }
	    });
	    
	    for (int i = 0; i < jsonArr.length(); i++) {
	        sortedJsonArray.put(jsonValues.get(i));
	    }
	    
	    return sortedJsonArray;
	}
	
	//key events
	@Override
	public void keyPressed(KeyEvent e) {
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