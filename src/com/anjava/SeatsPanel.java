package com.anjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class SeatsPanel extends JPanel implements ActionListener{
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

	   
	   boolean admin =true;
	   static int q;
	   static String qq;
	    
	//   static JPanel mainPanel;
	   
//	   

	   int column=10;
	   int row=10;
	   int maxseat=column*row;
	   
	   
	   ArrayList<reserves> reservedData =new ArrayList();
	   
	   JButton[] btn = new JButton[maxseat];
	   JButton[] btn2= new JButton[maxseat];
	   
	   int[] colblock = {2,5};
	   boolean colblocks =false;
	   int[] roblock = {3,7};
	   boolean rowblock=false;

	   public SeatsPanel() {
	      this.setLayout(null);

//	         topLabel=new JLabel("안녕하세요. user.name님");
	         buttonPanel=new JPanel();
//	         topLabel.setFont(new Font("202호실 예약", Font.BOLD, 30));
	         
	         
	         buttonPanel.setLayout(new GridLayout(column,row,10,20));
	         buttonPanel.setBackground(Color.white);
	         
	         mouse m = new mouse();
	         
	      

	         //열을 공백주는 배열을 만들어서 int k에 넣어서 j==k일때 널로(공백)
	         int abc=1;
	         for(int i=1; i<column+1; i++) {
	            
	            for(int j=1; j<row+1; j++) {
	               for(int q: roblock) {
	                  if(i==q) {
	                     reservedData.add(new reserves(0, "null"));
	                     rowblock=true;
	                     break;
	                     
	                  }
	                  
	               }
	               if(rowblock==true) {
	                  rowblock=false;
	               continue;   
	               }
	               for(int k:colblock) {
	                  if(j==k) {
	                     reservedData.add(new reserves(0,"null"));
	                     colblocks=true;
	                     break;
	                  }
	               }
	               if(colblocks==false) {
	                  
	                     reservedData.add(new reserves(abc++, "yet"));   
	                  
	               }
	               
	            
	            colblocks=false;
	         }
	         }
	         
	            for(int i=0; i<maxseat; i++) {
	               btn[i]=new JButton();
	               btn[i].setPreferredSize(new Dimension(180,180));
	               //완전 빈칸(빈자리)
	               if(reservedData.get(i).getSitNum()==0&& reservedData.get(i).getUserId()!="yet") {
	                  btn[i].setEnabled(false);
	                  btn[i].setBackground(Color.white);
	                  btn[i].setBorder(null);
	                  
	               }else//sitnum가 0이아니고 yet인자리-->빈자리 
	               {
	                  
	                  btn[i].setEnabled(true);
	                  btn[i].setBackground(Color.BLUE);
	                  btn[i].setText((reservedData.get(i).getSitNum())+"");
	                  btn[i].addMouseListener(m);
	                  if(admin==false) {
	                  btn[i].addActionListener(this);
	                  }else {
	                     btn[i].addActionListener(new ActionListener() {
	                        
	                        @Override
	                        public void actionPerformed(ActionEvent e) {
	                           for(int i=0; i<maxseat; i++) {
	                              if(e.getSource()==btn[i]) {
	                                 q=i;
	                                 qq=btn[i].getText();
	                                 
	                              }
	                        }
	                           new yeyakcancels();
	                        }
	                     });
	                     }
	                  }
	                  if(reservedData.get(i).getUserId()=="aio")//sitnum이 0이 아니고 그중에 aio-->예약된자리
	                     
	                  {
	                     btn[i].setBackground(Color.red);
	                     btn[i].setText("예약완료");
	                     btn[i].setEnabled(false);
	                     btn[i].addMouseListener(new MouseListener() {
	                        
	                        
	                        public void mouseReleased(MouseEvent e) {
	                        }
	                        
	                        
	                        public void mousePressed(MouseEvent e) {
	                        }
	                        
	                        
	                        public void mouseExited(MouseEvent e) {
	                            JButton b = (JButton)e.getSource();
	                                b.setBackground(Color.red);
	                        }
	                        
	                        public void mouseEntered(MouseEvent e) {
	                            JButton b = (JButton)e.getSource();
	                                b.setBackground(Color.red);
	                        }
	                        
	                        public void mouseClicked(MouseEvent e) {
	                           
	                        }
	                     });
	                     
	                  }
	               buttonPanel.add(btn[i]);
	            }

	               add(buttonPanel);
	              
	                buttonPanel.setBounds(30,100,700,400);
	                repaint();     
	   }
	   
	   //------------------------------------------
	   
	   
	   
	   
	   //선그려줌
//	   public void paint(Graphics g) {
//	      super.paint(g);
//	      g.setColor(Color.blue);
//	      g.drawLine(0,100,1000, 100);
//	      g.drawLine(700, 100, 700, 800);
//	      
//	   }
	   //------------------------------------------------

	//좌석 버튼을 눌렀을때 번호 몇번인지 얻어옴
	   @Override
	   public void actionPerformed(ActionEvent e) {
	      for(int i=0; i<maxseat; i++) {
	         if(e.getSource()==btn[i]) {
	            q=i;
	            qq=btn[i].getText();
	            
	         }
	   }
	      new yeyakok();

	}
	   //-------------------------------------------------------
	   class yeyakcancels extends JFrame implements ActionListener,MouseListener{
	      JLabel topLabel;
	      JButton yesbtn;
	      JButton noBtn;
	       int aa=Integer.valueOf(qq);
	      
	      public yeyakcancels() {
	         
	         setSize(300,300);
	         
	         setLocationRelativeTo(null);
	         topLabel =new JLabel();
	         
	         topLabel.setText(aa+" 번 좌석을 취소하시겠습니까?");
	         yesbtn = new JButton("네");
	         yesbtn.addActionListener(this);
	          noBtn = new JButton("아니오");
	          noBtn.addActionListener(this);
	          setLayout(new BorderLayout());
	          add(topLabel,BorderLayout.NORTH);
	          add(yesbtn,BorderLayout.WEST);
	          add(noBtn,BorderLayout.EAST);
	         setVisible(true);
	         
	      }
	      @Override
	      public void actionPerformed(ActionEvent e) {
	         
	         
	         if(e.getSource()==yesbtn) {
	            reservedData.get(q).setUserId("yet");
	            //userid 를 yet으로 바꿔주면서 예약을 취소해버리고 버튼을 원래 파란색버튼으로 만들어버린다
	            btn[q].setEnabled(true);

	            btn[q].setText(aa+"");
	            btn2[q].setText(aa+"");
	            btn[q].setBackground(Color.blue);
	            btn2[q].setBackground(Color.blue);
	            btn[q].addMouseListener(this);
	            btn2[q].addMouseListener(this);
	            
	            dispose();
	            new cancels();
	         } 
	         if(e.getSource()==noBtn) {
	            dispose();
	         }
	      }
	      @Override
	      public void mouseClicked(MouseEvent e) {
	      }
	      @Override
	      public void mousePressed(MouseEvent e) {
	      }
	      @Override
	      public void mouseReleased(MouseEvent e) {
	      }
	      @Override
	      public void mouseEntered(MouseEvent e) {
	         JButton b = (JButton)e.getSource();
	           b.setBackground(Color.yellow);
	      }
	      @Override
	      public void mouseExited(MouseEvent e) {
	         JButton b = (JButton)e.getSource();
	           b.setBackground(Color.blue);
	      }
	      
	   }
	   
	   //-----------------------------------------------------------------------
	   //예약하시겠습니까?
	   class yeyakok extends JFrame implements ActionListener,MouseListener{
	      JLabel topLabel;
	      JButton yesbtn;
	      JButton noBtn;
	      
	       int aa=Integer.valueOf(qq);
	      
	      public yeyakok() {
	         setSize(300,300);
//	         setDefaultCloseOperation(false);
	         setLocationRelativeTo(null);
	         topLabel =new JLabel();
	         
	         topLabel.setText(aa+" 번 좌석을 예약하시겠습니까?");
	         yesbtn = new JButton("네");
	         yesbtn.addActionListener(this);
	          noBtn = new JButton("아니오");
	          noBtn.addActionListener(this);
	          setLayout(new BorderLayout());
	          add(topLabel,BorderLayout.NORTH);
	          add(yesbtn,BorderLayout.WEST);
	          add(noBtn,BorderLayout.EAST);
	         setVisible(true);
	         
	      }

	      @Override
	      public void actionPerformed(ActionEvent e) {
//	         int iq= Integer.valueOf(Anja1.q);
	         if(e.getSource()==yesbtn) {
	            btn[q].setEnabled(false);
	            btn[q].addMouseListener(this);
	            btn[q].setText("예약완료");
	            btn[q].setBackground(Color.red);
	            reservedData.get(q).setUserId("aio");
	            //userid를 aio로 바꾸고 버튼을 빨강으로만듬
	            dispose();
	            new ok();
	         } 
	         if(e.getSource()==noBtn) {
	            dispose();
	         }
	      }

	      @Override
	      public void mouseClicked(MouseEvent e) {
	      }

	      @Override
	      public void mousePressed(MouseEvent e) {
	      }

	      @Override
	      public void mouseReleased(MouseEvent e) {
	      }

	      @Override
	      public void mouseEntered(MouseEvent e) {
	         JButton b = (JButton)e.getSource();
	           b.setBackground(Color.red);
	      }

	      @Override
	      public void mouseExited(MouseEvent e) {
	          JButton b = (JButton)e.getSource();
	              b.setBackground(Color.red);
	      }
	   }
	      
	      //----------------------------------------------------

	      class ok extends JFrame{
	         
	         JLabel lb;
	         JButton okButton;
	         public ok() {
	          setSize(300,300);
//	          this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	          this.setLocationRelativeTo(null);
	          
	          setLayout(new FlowLayout());
	          lb=new JLabel("예약이 완료되었습니다");
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
	      //--------------------------------------------
	   
	         

	class mouse implements MouseListener{

	   @Override
	   public void mouseClicked(MouseEvent e) {
	   }

	   @Override
	   public void mousePressed(MouseEvent e) {
	   }

	   @Override
	   public void mouseReleased(MouseEvent e) {
	   }

	   @Override
	   public void mouseEntered(MouseEvent e) {
	      
	       JButton b = (JButton)e.getSource();
	           b.setBackground(Color.YELLOW);
	   }

	   @Override
	   public void mouseExited(MouseEvent e) {
	       JButton b = (JButton)e.getSource();
	           b.setBackground(Color.blue);
	   }
	}

	//=---------------------------------------------------------
	         
	class cancels extends JFrame{
	   
	   JLabel lb;
	   JButton okButton;
	   public cancels() {
	    setSize(300,300);
//	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
	    setLayout(new FlowLayout());
	    lb=new JLabel("예약이 취소되었습니다");
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
	      //--------------------------------------------------------


	}