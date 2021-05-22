//package com.anjava;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.Date;
//import java.util.TimeZone;
//
//import javax.swing.*;
//
//import org.json.JSONArray;
//
//public class LoggedInPanelOld extends JPanel{
//	int boxCount;
//	JButton[] reserveBtn;
//	JPanel temp = new JPanel();
//	JScrollPane scroll = new JScrollPane(temp,
//										 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//										 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//	LoggedInPanel2 lip = new LoggedInPanel2(); 
//	
//	
//	public LoggedInPanelOld(JSONArray roomsData){
//		this.boxCount = roomsData.length();
//		this.reserveBtn = new JButton[boxCount];
//		
//		//PanelSetting
//		temp.setBorder(BorderFactory.createLineBorder(Color.black));
//		temp.setPreferredSize(new Dimension(600, (this.boxCount / 4 + 1) * 100));
//		scroll.setPreferredSize(new Dimension(600, 400));
//		this.setBounds(6, 54, 600, 400);
//		
////			this.setPreferredSize(new Dimension(600, 400));
////			this.setLocation(6, 54);
//			
//		for(int i = 0; i < roomsData.length(); i++) {
//			int roomNum = roomsData.getJSONObject(i).getInt("roomNum");
//			int maxSit = roomsData.getJSONObject(i).getInt("maxSit");
//			int remainSit = roomsData.getJSONObject(i).getInt("remainSit");
//			boolean isUserIncluded = roomsData.getJSONObject(i).getBoolean("isUserIncluded");
//			String btnText = "<HTML>" + "방번호: " + roomNum + "<br>" + "남은 좌석: " + remainSit + "/" + maxSit;
//			if (!roomsData.getJSONObject(i).isNull("resetDate")) {
//				String datestr = roomsData.getJSONObject(i).getString("resetDate");
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.Z");
//				df.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
//				try {
//				    // 타임존이 포함된 ISO 8601 문자열로부터 Asia/Seoul 타임존의 LocaDateTime 오브젝트 획득
//				    LocalDateTime dateTime = LocalDateTime.from(
//
//				        Instant.from(
//				            DateTimeFormatter.ISO_DATE_TIME.parse(datestr)
//				        ).atZone(ZoneId.of("Asia/Seoul"))
//				    );
//				    datestr = dateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E) HH시 mm분"));
//				// 파씽 오류시 DateTimeParseException 예외 발생
//				} catch (DateTimeParseException ex) {
//
//				    // 예외 처리 로직 작성
//				}
//				btnText += "<br>" + "좌석 초기화: " + datestr + "</HTML>";
//				System.out.println(btnText);
//
//			} else {
//				btnText.concat("</HTML>");
//			}
//			reserveBtn[i] = new JButton(btnText);
//			if (isUserIncluded) {
//				reserveBtn[i].setBackground(Color.orange);
//			}else {				
//				reserveBtn[i].setBackground(Color.gray.brighter());
//			}
//			reserveBtn[i].setBorder(null);
//			reserveBtn[i].setPreferredSize(new Dimension(135,90));
//			temp.add(reserveBtn[i]);
//			add(scroll);
//		}
//		
//	}
//  
//
//	
//}