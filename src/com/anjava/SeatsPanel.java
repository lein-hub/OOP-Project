package com.anjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

class SeatsPanel extends JPanel {
	public JPanel buttonPanel = new JPanel();
		
	   class  reserves{  
	      
	      private int sitNum;
	      private String userId;
	      
	      public reserves(int sitNum, String userId) {
	         this.sitNum=sitNum;
	         this.userId=userId;
	      
	         
	      }
	      

	      
	      public int getSitNum() {
	         return sitNum;
	      }

	      public void setSitNum(int sitNum) {
	         this.sitNum = sitNum;
	      }

	      public String getUserId() {
	         return userId;
	      }

	      public void setUserId(String userId) {
	         this.userId = userId;
	      }

	   }

	   HttpCaller hc;
	   boolean isAdmin =true;
	   static int q;
	   static String qq;
	    
	//   static JPanel mainPanel;
	   
//	   

	   int column;
	   int row;
	   int maxseat;
	   int roomNum;
	   
	   static int myReservedSeat = 0;
	   int[] otherReservedSeats;
	   
	   
	   ArrayList<reserves> reservedData =new ArrayList<>();
	   
	   MyButton[] btn;
	   
	   int[] colblock;
	   boolean colbool =false;
	   int[] rowblock;
	   boolean rowbool=false;

	   public SeatsPanel(int col, int row, int[] colbl, int[] rowbl, int roomNum, int[] otherReservedSeats, HttpCaller hc) {
//	      this.setLayout(null);
	      this.hc = hc;
		  this.roomNum = roomNum;
	      this.setLayout(null);
	      this.column = col + colbl.length;
	      this.row = row + rowbl.length;
	      this.colblock = colbl;
	      this.rowblock = rowbl;
	      this.maxseat = this.column * this.row;
	      this.isAdmin = hc.isAdmin();
	      this.otherReservedSeats = otherReservedSeats;
	      JSONArray reservedRooms = new JSONObject(hc.getUserDetail()).getJSONObject("data").getJSONArray("reservedRooms");
	      for (int i=0; i<reservedRooms.length(); i++) {
	    	  JSONObject obj;
	    	  if ((obj = (JSONObject) reservedRooms.get(i)).getInt("roomNum") == roomNum) {
	    		  SeatsPanel.myReservedSeat = obj.getInt("sitNum");
	    	  }
	      }
	      btn = new MyButton[maxseat];
	      
	      for (int i = 0; i < rowblock.length; i++) {
	    	  rowblock[i] = rowblock[i] + i;
	      }
	      for (int i = 0; i < colblock.length; i++) {
	    	  colblock[i] = colblock[i] + i;
	      }

//	         topLabel=new JLabel("안녕하세요. user.name님");
	         buttonPanel=new JPanel();
//	         topLabel.setFont(new Font("202호실 예약", Font.BOLD, 30));
	         
	         
	         buttonPanel.setLayout(new GridLayout(column,row,10,20));
	         buttonPanel.setBackground(Color.white);
	         
	      

	         //열을 공백주는 배열을 만들어서 int k에 넣어서 j==k일때 널로(공백)
	         
	         int index=1;
	         for(int i=0; i<this.column; i++) {
	            for(int j=0; j<this.row; j++) {
	            	final int jj = j;
	            	final int ii = i;
	            	final int iidx = index;
	            	btn[i*this.row+j]=new MyButton();
		            btn[i*this.row+j].setPreferredSize(new Dimension(180,180));
	            	if (IntStream.of(rowblock).anyMatch(x -> x == jj) || IntStream.of(colblock).anyMatch(x -> x == ii)) {
	            		// 공백일 때
	            		btn[i*this.row+j].setEnabled(false);
		                btn[i*this.row+j].setBackground(Color.white);
		                btn[i*this.row+j].setBorder(null);
	            	} else {
	            		if (index == myReservedSeat) {
	            			// 내가 예약한 자리 일 때
	            			btn[i*this.row+j].setEnabled(true);
			                btn[i*this.row+j].setBackground(Color.orange);
		            		btn[i*this.row+j].setText(String.valueOf(index++));
		            		btn[i*this.row+j].setStatus(1);
	            		} else if (IntStream.of(otherReservedSeats).anyMatch(x -> x == iidx)) {
	            			// 다른사람이 예약한 자리일 때
	            			btn[i*this.row+j].setEnabled(true);
			                btn[i*this.row+j].setBackground(Color.gray);
		            		btn[i*this.row+j].setText(String.valueOf(index++));
		            		btn[i*this.row+j].setStatus(2);
	            		} else {
	            			// 빈 자리일 때
	            			btn[i*this.row+j].setEnabled(true);
		            		btn[i*this.row+j].setText(String.valueOf(index++));
			                btn[i*this.row+j].setBackground(Color.BLUE);
			                btn[i*this.row+j].setStatus(0);
	            		}
	            	}
	            	btn[i*this.row+j].addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                       for(int i=0; i<maxseat; i++) {
	                          if(e.getSource()==btn[i]) {
	                             new yeyakok(i, Integer.valueOf(btn[i].getText()), roomNum);
	                          }
	                       }
	                    }
	                });
	            	buttonPanel.add(btn[i*this.row+j]);
	             }
	         }
	         add(buttonPanel);
	         buttonPanel.setBounds(30,100,700,400);
	         repaint();     
	   }
	   
	   
	   
	   //-----------------------------------------------------------------------
	   //예약하시겠습니까?
	   class yeyakok extends JFrame implements ActionListener {
	      JLabel topLabel;
	      JButton yesbtn;
	      JButton noBtn;
	      int index;
	      int roomNum;
	      int number;
	      public yeyakok(int index, int number, int roomNum) {
	    	  this.index = index;
	    	  this.roomNum = roomNum;
	    	  this.number = number;
	    	  setSize(300,300);
	          setLocationRelativeTo(null);
	          topLabel = new JLabel();
	          
	          if (btn[index].getStatus() == 0) {
	        	  setLayout(new BorderLayout());
	        	  if (myReservedSeat > 0) {
	        		  topLabel.setText("이미 이 방에 예약한 자리가 있습니다.");
	        		  add(topLabel,BorderLayout.CENTER);
	        		  setSize(250, 100);
	        	  } else {
	        		  topLabel.setText(number+" 번 좌석을 예약하시겠습니까?");
			          yesbtn = new JButton("네");
			          yesbtn.addActionListener(this);
			          noBtn = new JButton("아니오");
			          noBtn.addActionListener(this);
			          add(topLabel,BorderLayout.NORTH);
			          add(yesbtn,BorderLayout.WEST);
			          add(noBtn,BorderLayout.EAST);
	        	  }
	          } else if (btn[index].getStatus() == 1) {
	        	  topLabel.setText(number+" 번 좌석을 예약 취소하시겠습니까?");
		          yesbtn = new JButton("네");
		          yesbtn.addActionListener(this);
		          noBtn = new JButton("아니오");
		          noBtn.addActionListener(this);
		          add(topLabel,BorderLayout.NORTH);
		          add(yesbtn,BorderLayout.WEST);
		          add(noBtn,BorderLayout.EAST);
	          } else {
	        	  if (isAdmin) {
	        		  topLabel.setText(number+" 번 좌석을 예약 취소하시겠습니까?");
			          yesbtn = new JButton("네");
			          yesbtn.addActionListener(this);
			          noBtn = new JButton("아니오");
			          noBtn.addActionListener(this);
			          add(topLabel,BorderLayout.NORTH);
			          add(yesbtn,BorderLayout.WEST);
			          add(noBtn,BorderLayout.EAST);
	        	  } else {
	        		  topLabel.setText("이미 예약된 자리입니다.");
			          add(topLabel,BorderLayout.CENTER);
	        		  setSize(250, 100);
	        	  }
	          }
	          setVisible(true);
	         
	          
	      }

	      @Override
	      public void actionPerformed(ActionEvent e) {
	    	  if(e.getSource()==yesbtn) {
	    		  if (btn[index].getStatus() == 0) {
//	    			  btn[index].addMouseListener(this);
			          btn[index].setBackground(Color.orange);
			          hc.postReserveRoom(this.roomNum, this.number);
			          btn[index].setStatus(1);
			          myReservedSeat = this.number;
			          new ok("예약이 완료되었습니다.");
	    		  } else if (btn[index].getStatus() == 1) {
//	    			  btn[index].addMouseListener(this);
			          btn[index].setBackground(Color.blue);
			          hc.deleteReserveRoom(this.roomNum, this.number);
			          btn[index].setStatus(0);
			          myReservedSeat = 0;
			          new ok("예약이 취소되었습니다.");
	    		  } else {
	    			  if (isAdmin) {
				          btn[index].setBackground(Color.blue);
				          hc.deleteReserveRoom(this.roomNum, this.number);
				          btn[index].setStatus(0);
				          new ok("예약이 취소되었습니다.");
	    			  }
	    		  }
	    		  
	            
	            dispose();
	         } 
	         if(e.getSource()==noBtn) {
	            dispose();
	         }
	      }
	   }
	      
	      //----------------------------------------------------

	      class ok extends JFrame{
	         
	         JLabel lb;
	         JButton okButton;
	         public ok(String message) {
	          setSize(300,300);
//	          this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	          this.setLocationRelativeTo(null);
	          
	          setLayout(new FlowLayout());
	          lb=new JLabel(message);
	          okButton=new JButton("OK");
	          okButton.addActionListener(new ActionListener() {
	            
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	               dispose();
	            }
	         });
	          
	          
	          add(lb);
	          add(okButton);
	          
	          setVisible(true);
	         }
	      }


	//=---------------------------------------------------------
	
	class MyButton extends JButton {
		int status;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
		
	}
	      //--------------------------------------------------------


	}