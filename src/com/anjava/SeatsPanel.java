package com.anjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

class SeatsPanel extends JPanel {
	public JPanel buttonPanel = new JPanel();
		
	public JPanel getThis() {
  	  return this;
    }

	   HttpCaller hc;
	   boolean isAdmin =true;
	   static int q;
	   static String qq;
	    
	//   static JPanel mainPanel;
	   
	   public JSONObject roomData;

	   public JSONObject getRoomData() {
		return roomData;
	}


	int column;
	   int row;
	   int maxseat;
	   int roomNum;
	   
	   int myReservedSeat = 0;
	   int[] otherReservedSeats;
	   
	   
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
	      this.roomData = new JSONObject(hc.getOneRoom(roomNum)).getJSONObject("data").getJSONObject("roomData");
	      JSONObject reservedRooms = this.roomData.getJSONObject("reservedData");
	      Set<String> set = reservedRooms.keySet();
	      Iterator<String> iter = set.iterator();
	      while (iter.hasNext()) {
	    	  String id = iter.next();
	    	  
	    	  if (reservedRooms.get(id).equals(hc.getId())) {
	    		  myReservedSeat = Integer.valueOf(id);
	    		  break;
	    	  }
	    	  myReservedSeat = 0;
	      }
	      
	      btn = new MyButton[maxseat];
	      
	      for (int i = 0; i < rowblock.length; i++) {
	    	  rowblock[i] = rowblock[i] + i;
	      }
	      for (int i = 0; i < colblock.length; i++) {
	    	  colblock[i] = colblock[i] + i;
	      }

//	         topLabel=new JLabel("???????????????. user.name???");
	         buttonPanel=new JPanel();
//	         topLabel.setFont(new Font("202?????? ??????", Font.BOLD, 30));
	         
	         
	         buttonPanel.setLayout(new GridLayout(this.row,column,10,20));
	         buttonPanel.setBackground(Color.white);
	         
	      

	         //?????? ???????????? ????????? ???????????? int k??? ????????? j==k?????? ??????(??????)
	         
	         int index=1;
	         for(int i=0; i<this.row; i++) {
	            for(int j=0; j<this.column; j++) {
	            	final int jj = j;
	            	final int ii = i;
	            	final int iidx = index;
	            	btn[i*this.column+j]=new MyButton();
		            btn[i*this.column+j].setPreferredSize(new Dimension(180,180));
	            	if (IntStream.of(colblock).anyMatch(x -> x == jj) || IntStream.of(rowblock).anyMatch(x -> x == ii)) {
	            		// ????????? ???
	            		btn[i*this.column+j].setEnabled(false);
		                btn[i*this.column+j].setBackground(Color.white);
		                btn[i*this.column+j].setBorder(null);
		                
	            	} else {
	            		if (index == myReservedSeat) {
	            			// ?????? ????????? ?????? ??? ???
	            			btn[i*this.column+j].setEnabled(true);
			                btn[i*this.column+j].setBackground(Color.orange);
		            		btn[i*this.column+j].setText(String.valueOf(index++));
		            		btn[i*this.column+j].setStatus(1);
		            		btn[i*this.column+j].setBorderPainted(false);
	            		} else if (IntStream.of(otherReservedSeats).anyMatch(x -> x == iidx)) {
	            			// ??????????????? ????????? ????????? ???
	            			btn[i*this.column+j].setEnabled(true);
			                btn[i*this.column+j].setBackground(Color.gray);
		            		btn[i*this.column+j].setText(String.valueOf(index++));
		            		btn[i*this.column+j].setStatus(2);
		            		btn[i*this.column+j].setBorderPainted(false);
	            		} else {
	            			// ??? ????????? ???
	            			btn[i*this.column+j].setEnabled(true);
		            		btn[i*this.column+j].setText(String.valueOf(index++));
			                btn[i*this.column+j].setBackground(new Color(255,170,170));
			                btn[i*this.column+j].setStatus(0);
			                btn[i*this.column+j].setBorderPainted(false);
	            		}
	            	}
	            	btn[i*this.column+j].addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                       for(int i=0; i<maxseat; i++) {
	                          if(e.getSource()==btn[i]) {
	                             new yeyakok(i, Integer.valueOf(btn[i].getText()), roomNum);
	                          }
	                       }
	                    }
	                });
	            	buttonPanel.add(btn[i*this.column+j]);
//	            	buttonPanel.setBackground(Color.);
	             }
	         }
	         add(buttonPanel);
	         buttonPanel.setBounds(10,0,760,400);
	         repaint();     
	   }
	   
	   
	   



	//-----------------------------------------------------------------------
	   //?????????????????????????
	   class yeyakok extends JFrame implements ActionListener {
	      JLabel topLabel;
	      JButton yesbtn;
	      JButton noBtn;
	      int index;
	      int roomNum;
	      int number;
	      JSONObject roomData = getRoomData();
	      public yeyakok(int index, int number, int roomNum) {
	    	  this.index = index;
	    	  this.roomNum = roomNum;
	    	  this.number = number;
	    	  this.setLayout(null);
	    	  setSize(300,120);
	          setLocationRelativeTo(null);
	          topLabel = new JLabel();
	          
	          if (btn[index].getStatus() == 0) {
//	        	  setLayout(new BorderLayout());
	        	  if (myReservedSeat > 0) {
	        		  topLabel.setText("?????? ??? ?????? ????????? ????????? ????????????.");
	        		  topLabel.setFont(new Font("HY?????????", Font.PLAIN, 12));
	        		  topLabel.setBounds(45,20,210,40);
			          noBtn = new JButton("????????????");
			          noBtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
			          noBtn.setForeground(Color.BLACK);
			          noBtn.setBackground(Color.LIGHT_GRAY);
			          noBtn.addActionListener(this);
			          noBtn.setBorderPainted(false);
			          noBtn.setBounds(100,70,100,20);	
			          this.add(topLabel);
			          this.add(noBtn);
			          this.setUndecorated(true);
	        	  } else {
	        		  JSONObject rsvd = roomData.getJSONObject("reservedData");
	    			  int[] reservedData = new int[rsvd.length()];
	    			  Iterator<String> iter = rsvd.keys();
	    			  for (int i=0; i<reservedData.length; i++) {
	    				  reservedData[i] = Integer.valueOf((String) iter.next());
	    			  }
	    			  if (IntStream.of(reservedData).anyMatch(x -> x == index)) {
	    				  topLabel.setText("?????? ????????? ???????????????.");
				          add(topLabel,BorderLayout.CENTER);
		        		  setSize(250, 100);
	    			  } else {
		        		  topLabel.setText(number+" ??? ????????? ?????????????????????????");
		        		  topLabel.setFont(new Font("HY?????????", Font.PLAIN, 12));
			        	  
				          yesbtn = new JButton("???");
				          yesbtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
				          yesbtn.setForeground(Color.white);
				          yesbtn.setBackground(new Color(135,77,162));
				          yesbtn.addActionListener(this);
				          yesbtn.setBorderPainted(false);
				          
				          noBtn = new JButton("?????????");
				          noBtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
				          noBtn.setForeground(Color.BLACK);
				          noBtn.setBackground(Color.LIGHT_GRAY);
				          noBtn.addActionListener(this);
				          noBtn.setBorderPainted(false);
				          
				          topLabel.setBounds(60,30,200,20);
				          yesbtn.setBounds(20,70,80,20);
				          noBtn.setBounds(200,70,80,20);
				          this.add(topLabel);
				          this.add(yesbtn);
				          this.add(noBtn);
				          this.setUndecorated(true);
	    			  }
	        	  }
	          } else if (btn[index].getStatus() == 1) {
	        	  topLabel.setText(number+" ??? ????????? ?????? ?????????????????????????");
	        	  topLabel.setFont(new Font("HY?????????", Font.PLAIN, 12));
	        	  
		          yesbtn = new JButton("???");
		          yesbtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
		          yesbtn.setForeground(Color.white);
		          yesbtn.setBackground(new Color(135,77,162));
		          yesbtn.addActionListener(this);
		          yesbtn.setBorderPainted(false);
		          
		          noBtn = new JButton("?????????");
		          noBtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
		          noBtn.setForeground(Color.BLACK);
		          noBtn.setBackground(Color.LIGHT_GRAY);
		          noBtn.addActionListener(this);
		          noBtn.setBorderPainted(false);
		          
		          topLabel.setBounds(50,30,250,20);
		          yesbtn.setBounds(20,70,80,20);
		          noBtn.setBounds(200,70,80,20);
		          this.add(topLabel);
		          this.add(yesbtn);
		          this.add(noBtn);
		          this.setUndecorated(true);
	          } else {
	        	  if (isAdmin) {
	        		  topLabel.setText(number+" ??? ????????? ?????? ?????????????????????????");
		        	  topLabel.setFont(new Font("HY?????????", Font.PLAIN, 12));
		        	  
			          yesbtn = new JButton("???");
			          yesbtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
			          yesbtn.setForeground(Color.white);
			          yesbtn.setBackground(new Color(135,77,162));
			          yesbtn.addActionListener(this);
			          yesbtn.setBorderPainted(false);
			          
			          noBtn = new JButton("?????????");
			          noBtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
			          noBtn.setForeground(Color.BLACK);
			          noBtn.setBackground(Color.LIGHT_GRAY);
			          noBtn.addActionListener(this);
			          noBtn.setBorderPainted(false);
			          
			          topLabel.setBounds(50,30,250,20);
			          yesbtn.setBounds(20,70,80,20);
			          noBtn.setBounds(200,70,80,20);
			          this.add(topLabel);
			          this.add(yesbtn);
			          this.add(noBtn);
			          this.setUndecorated(true);
	        	  } else {
	        		  topLabel.setText("?????? ????????? ???????????????.");
	        		  topLabel.setFont(new Font("HY?????????", Font.PLAIN, 12));
	        		  topLabel.setBounds(87,20,210,40);
			          noBtn = new JButton("????????????");
			          noBtn.setFont(new Font("HY?????????", Font.PLAIN, 12));
			          noBtn.setForeground(Color.BLACK);
			          noBtn.setBackground(Color.LIGHT_GRAY);
			          noBtn.addActionListener(this);
			          noBtn.setBorderPainted(false);
			          noBtn.setBounds(100,70,100,20);
			          this.add(topLabel);
			          this.add(noBtn);
			          this.setUndecorated(true);
	        	  }
	          }
	          setVisible(true);
	         
	          
	      }
	      
	      

	      @Override
	      public void actionPerformed(ActionEvent e) {
	    	  if(e.getSource()==yesbtn) {
	    		  if (btn[index].getStatus() == 0) {  // ???????????? ????????? ???
//			          btn[index].setBackground(Color.orange);
			          JSONObject res = new JSONObject(hc.postReserveRoom(this.roomNum, this.number));
			          System.out.println(res);
			          MainLogin ml = (MainLogin) SwingUtilities.getAncestorOfClass(MainLogin.class, getThis());
			          ml.refreshSeatsPanel();
//			          btn[index].setStatus(1);
//			          myReservedSeat = this.number;
			          
			          new ok(res.getString("message"));
	    		  } else if (btn[index].getStatus() == 1) { // ??? ????????? ????????? ???
//			          btn[index].setBackground(new Color(255,170,170));
			          JSONObject res = new JSONObject(hc.deleteReserveRoom(this.roomNum, this.number));
			          System.out.println(res);
			          MainLogin ml = (MainLogin) SwingUtilities.getAncestorOfClass(MainLogin.class, getThis());
			          ml.refreshSeatsPanel();
//			          btn[index].setStatus(0);
//			          myReservedSeat = 0;
			          new ok(res.getString("message"));
	    		  } else { // ?????? ????????? ????????? ???
	    			  if (isAdmin) {
//				          btn[index].setBackground(new Color(255,170,170));
				          System.out.println(this.roomData.getJSONObject("reservedData").getString(String.valueOf(this.number)));
				          System.out.println(roomNum);
				          System.out.println(this.number);
				          JSONObject res = new JSONObject(hc.deleteReserveRoom(this.roomData.getJSONObject("reservedData").getString(String.valueOf(this.number)), this.roomNum, this.number));
				          System.out.println(res);
				          MainLogin ml = (MainLogin) SwingUtilities.getAncestorOfClass(MainLogin.class, getThis());
				          ml.refreshSeatsPanel();
//				          btn[index].setStatus(0);
				          new ok(res.getString("message"));
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
	        	 setSize(300,120);
//	          this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	          this.setLocationRelativeTo(null);
	          
	          setLayout(null);
	          lb=new JLabel(message);
	          lb.setFont(new Font("HY?????????", Font.PLAIN, 12));
	          lb.setBounds(85,20,200,20);
	          okButton=new JButton("??????");
	          okButton.setFont(new Font("HY?????????", Font.PLAIN, 12));
	          okButton.setForeground(Color.BLACK);
	          okButton.setBackground(Color.LIGHT_GRAY);
	          okButton.setBorderPainted(false);
	          okButton.setBounds(100,85,100,20);
	          okButton.addActionListener(new ActionListener() {
	            
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	               dispose();
	            }
	         });
	          
	          add(okButton);
	          add(lb);
	          setUndecorated(true);
	          
	          
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