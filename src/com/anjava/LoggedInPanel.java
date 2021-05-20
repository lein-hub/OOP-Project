package com.anjava;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import org.json.JSONArray;

public class LoggedInPanel extends JPanel{
	int boxCount;
	JButton[] reserveBtn;
	JPanel temp = new JPanel();
	JScrollPane scroll = new JScrollPane(temp,
										 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
										 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	LoggedInPanel2 lip = new LoggedInPanel2(); 
	
	
	public LoggedInPanel(JSONArray roomsData){
		this.boxCount = roomsData.length();
		this.reserveBtn = new JButton[boxCount];
		
		//PanelSetting
		temp.setBorder(BorderFactory.createLineBorder(Color.black));
		temp.setPreferredSize(new Dimension(600, (this.boxCount / 4 + 1) * 100));
		scroll.setPreferredSize(new Dimension(600, 400));
		this.setBounds(6, 54, 600, 400);
		
//			this.setPreferredSize(new Dimension(600, 400));
//			this.setLocation(6, 54);
			
		for(int i = 0; i < roomsData.length(); i++) {
			int roomNum = roomsData.getJSONObject(i).getInt("roomNum");
			int maxSit = roomsData.getJSONObject(i).getInt("maxSit");
			int remainSit = roomsData.getJSONObject(i).getInt("remainSit");
			boolean isUserIncluded = roomsData.getJSONObject(i).getBoolean("isUserIncluded");
			String btnText = "<HTML>" + "방번호: " + roomNum + "<br>" + "남은 좌석: " + remainSit + "/" + maxSit;
			if (!roomsData.getJSONObject(i).isNull("resetDate")) {
//				btnText.concat("<br>" + "좌석 초기화: " + roomsData.getJSONObject(i).getString("resetDate") + "</HTML>");
				btnText += "<br>" + "좌석 초기화: " + roomsData.getJSONObject(i).getString("resetDate") + "</HTML>";
				System.out.println(btnText);

			} else {
				btnText.concat("</HTML>");
			}
			reserveBtn[i] = new JButton(btnText);
			if (isUserIncluded) {
				reserveBtn[i].setBackground(Color.orange);
			}else {				
				reserveBtn[i].setBackground(Color.gray.brighter());
			}
			reserveBtn[i].setBorder(null);
			reserveBtn[i].setPreferredSize(new Dimension(140,90));
			temp.add(reserveBtn[i]);
			add(scroll);
		}
		
	}
  

	
}