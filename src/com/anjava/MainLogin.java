package com.anjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;




public class MainLogin extends JFrame implements ActionListener, KeyListener{
	JPanel logInPanel, signUpBtnPanel, imagePanel, signUpMainPanel, cRoomPanel;
	JLabel mainTitle, subTitle, idLabel, pwdLabel, welcome, reLabel, colLabel, rowLabel, colBlankLabel, rowBlankLabel, roomNumLabel;
	AntialiasedLabel mainLogLabel, signUpPanelLabel;
	JTextField ID, col, row, roomNum, colBlank, rowBlank;
	JPasswordField PASSWORD;;
	JButton logInBtn, signUpBtn, backBtn2, signUpBtn2, exitButton, backBtn, mainBtn, logOutBtn, cRoom, dRoom, resetDate, makeRoomBtn;
	LoggedInPanel loggedInPanel;
	FakeDB fake = new FakeDB();
	Font Title = new Font(null);
	ImageIcon icon;
	HttpCaller hc = new HttpCaller();
	JPanel logInLabelPanel = new JPanel();
	SignUpPanel signUpPanel = new SignUpPanel();
	JLabel[] logInLabels = new JLabel[signUpPanel.categories.length];
	JPanel seatsPanel;
	JSONArray roomsData;


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
		ID.setText("test6");

		 //Password
		PASSWORD = new JPasswordField(15);
		PASSWORD.setBounds(64,45,135,20);
		PASSWORD.addKeyListener(this);
		PASSWORD.setText("12341234");

		
		//----------------------------------------------------------------------------------------------
		
		
		//Buttons
		 //LogIn Button
		logInBtn = new JButton("로그인");
		logInBtn.setBounds(85, 75, 80, 25);
		logInBtn.setBackground(Color.LIGHT_GRAY);
		logInBtn.addActionListener(this);
		
		//main Button
		mainBtn = new JButton("뒤로가기");
		mainBtn.setBounds(598, 464, 84, 25);
		
		//create Room Button
		cRoom = new JButton("방만들기");
		cRoom.setBounds(622,58,160,100);
		cRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new createRoom();
			}			
		});
		
		//delete Room Button
		dRoom = new JButton("방지우기");
		dRoom.setBounds(622,204,160,100);
		dRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // TODO Auto-generated method stub
               String input = JOptionPane.showInputDialog("지울 방의 호수를 입력하세요.");
               hc.deleteRoom(Integer.valueOf(input));
            }
         });
		
		//reset Date Button
		resetDate = new JButton("초기화날짜설정");
		resetDate.setBounds(622,350,160,100);
		
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
				if (seatsPanel != null) deleteSeats();
				deleteAdminBtn();
				welcome.setVisible(false);
				if(cRoomPanel != null)
				cRoomPanel.setVisible(false);
				}
			
		});
		logOutBtn.setBounds(699, 464, 84, 25);
		
		
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
			hc.postLogIn(ID.getText(), PASSWORD.getPassword());
			add(mainBtn);
			addAdminBtn();
			
			//로그인 된 창에서 뒤로가기 눌렀을 때
			mainBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					addLoggedInPanel();
					deleteSeats();
					addAdminBtn();
					mainBtn.setVisible(false);
				}
			});
			mainBtn.setVisible(false);
			//로그인 정보가 일치할 때
			if(hc.isLoggedIn()) {		
				roomsData = new JSONObject(hc.getAllRoom()).getJSONObject("data").getJSONArray("roomsData");
			    roomsData = sortJsonArray(roomsData, "roomNum");
				loggedInPanel = new LoggedInPanel(roomsData);
				addLoggedInPanel();
				deleteMainLogIn();
				setLogInTextEmpty();
				//로그인 했을 때 생기는 Buttons
				
				for(int i = 0; i < loggedInPanel.boxCount; i++) {
					int roomNum = roomsData.getJSONObject(i).getInt("roomNum");
					loggedInPanel.reserveBtn[i].addActionListener(new ActionListener() {
//											//예약버튼 눌렀을 때
//											@Override
											public void actionPerformed(ActionEvent e) {
//												remove(loggedInPanel);
//												deleteLoggedInPanel();
												loggedInPanel.setVisible(false);
												addSeats(roomNum);
												deleteAdminBtn();
												
//												deleteReserveBtn();
												loggedInPanel.reserve.setVisible(false);
												mainBtn.setVisible(true);
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
		                        signUpPanel.pwdFields[0].getPassword(), 
		                        signUpPanel.fields[0].getText(), 
		                        yjuNum, 
		                        signUpPanel.fields[5].getText());
					System.out.println(res);
					JSONObject jo = new JSONObject(res);
					if (!signUpPanel.pwdFields[0].getPassword().equals(signUpPanel.pwdFields[1].getPassword())) {
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
			
			//뒤로가기 버튼
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
		add(loggedInPanel);
		if(hc.isAdmin()) {
//			add(loggedInPanel.reserve);
			loggedInPanel.btnPanel.setPreferredSize(new Dimension(600, (50 / 4 + 1) * 100));
			loggedInPanel.scroll.setPreferredSize(new Dimension(600, 405));
			loggedInPanel.setBounds(6, 49, 600, 405);
			add(cRoom);
			add(dRoom);
			add(resetDate);
		}
		add(welcome);
		welcome.setVisible(true);
		loggedInPanel.setVisible(true);
		welcome.setText("안녕하세요. " + hc.getName() + "님");
		add(logOutBtn);
	}
	
	class createRoom extends JFrame implements ActionListener{
		public createRoom() {
		
		setSize(370,347);
		setLayout(null);
		roomNum = new JTextField();
		roomNum.setBounds(125,35,150,25);
		roomNum.setToolTipText("숫자로만 입력하세요.");
		roomNumLabel = new JLabel("강의실 호수");
		roomNumLabel.setBounds(48,35,70,25);
		roomNumLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(roomNumLabel);
		add(roomNum);
		
		col = new JTextField();
		col.setBounds(125,82,150,25);
		col.setToolTipText("숫자로만 입력하세요.");
		colLabel = new JLabel("열 수");
		colLabel.setBounds(65,82,50,25);
		colLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(colLabel);
		add(col);
		

		row = new JTextField();
		row.setBounds(125,129,150,25);
		row.setToolTipText("숫자로만 입력하세요.");
		rowLabel = new JLabel("행 수");
		rowLabel.setBounds(65,129,50,25);
		rowLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(rowLabel);
		add(row);
		
		
		colBlank = new JTextField();
		colBlank.setBounds(125,176,150,25);
		colBlankLabel = new JLabel("열 띄우기");
		colBlankLabel.setBounds(35,176,80,25);
		colBlankLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(colBlankLabel);
		add(colBlank);
		
		rowBlank = new JTextField();
		rowBlank.setBounds(125,223,150,25);
		rowBlankLabel = new JLabel("행 띄우기");
		rowBlankLabel.setBounds(35,223,80,25);
		rowBlankLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(rowBlankLabel);
		add(rowBlank);
		
		makeRoomBtn = new JButton("방만들기");
		makeRoomBtn.addActionListener(this);
		makeRoomBtn.setBounds(190,265,85,25);
		add(makeRoomBtn);
//			
//			cRoomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//			cRoomPanel.setLayout(new GridLayout(4,2,15,15));
//			cRoomPanel.setBounds(63,78,500,350);
//			add(cRoomPanel);
		setLocationRelativeTo(null);
		setVisible(true);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			StringTokenizer colst = new StringTokenizer(colBlank.getText(), ", ");
			StringTokenizer rowst = new StringTokenizer(rowBlank.getText(), ", ");
			int[] colBlankArray = new int[colst.countTokens()];
			int[] rowBlankArray = new int[rowst.countTokens()];
			
			System.out.println("coltokencount: " + colst.countTokens());
			System.out.println("rowtokencount: " + rowst.countTokens());
			
			for (int i=0; i<colBlankArray.length; i++) {
				colBlankArray[i] = Integer.valueOf(colst.nextToken());
			}
			for (int i=0; i<rowBlankArray.length; i++) {
				rowBlankArray[i] = Integer.valueOf(rowst.nextToken());
			}
			
			for (int a : colBlankArray) {
				System.out.println("colblank: " + a);
			}
			for (int a : rowBlankArray) {
				System.out.println("rowblank: " + a);
			}
			System.out.println("colblanklength: " + colBlankArray.length);
			System.out.println("rowblanklength: " + rowBlankArray.length);
			
			hc.postCreateRoom(Integer.valueOf(roomNum.getText()), 
							  Integer.valueOf(col.getText()), 
							  Integer.valueOf(row.getText()), 
							  colBlankArray.length == 0 ? null : colBlankArray,
							  rowBlankArray.length == 0 ? null : rowBlankArray);
			dispose();
			loggedInPanel.revalidate();
			loggedInPanel.repaint();
			repaint();
		}
		
	}
	
	public void deleteLoggedInPanel() {
		remove(loggedInPanel);
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
		int[] reservedSeats;
		seatsPanel = new SeatsPanel(col, row, colbl, rowbl, roomNum, reservedData, hc);
		seatsPanel.setVisible(true);
		seatsPanel.setBounds(20, -46, 800, 500);
		add(seatsPanel);
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
		cRoom.setVisible(false);
		dRoom.setVisible(false);
		resetDate.setVisible(false);
	}
	
	public void addAdminBtn() {
		if(hc.isAdmin()) {
		cRoom.setVisible(true);
		dRoom.setVisible(true);
		resetDate.setVisible(true);
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