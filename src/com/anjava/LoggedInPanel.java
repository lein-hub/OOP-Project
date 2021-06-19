package com.anjava;

import java.awt.Color;
import java.awt.Dimension;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoggedInPanel extends JPanel{
	int boxCount;
	JButton[] reserveBtn;
	JPanel btnPanel = new JPanel();
	JScrollPane scroll = new JScrollPane(btnPanel,
										 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
										 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public LoggedInPanel(JSONArray roomsData){
		this.boxCount = roomsData.length();
		this.reserveBtn = new JButton[boxCount];
	
		//PanelSetting
		btnPanel.setPreferredSize(new Dimension(750, (boxCount / 4 + 1) * 100));
		scroll.setPreferredSize(new Dimension(750, 405));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		this.setBounds(6, 49, 750, 405);
		
		//roomsData를 방 번호 순서로 정렬합니다!
		
	    // btnPanel에 버튼을 추가합니다!
		for(int i = 0; i < roomsData.length(); i++) {
			int roomNum = roomsData.getJSONObject(i).getInt("roomNum");
			int maxSit = roomsData.getJSONObject(i).getInt("maxSit");
			int remainSit = roomsData.getJSONObject(i).getInt("remainSit");
			boolean isUserIncluded = roomsData.getJSONObject(i).getBoolean("isUserIncluded");
			String btnText = "<HTML>" + "방번호: " + roomNum + "<br>" + "남은 좌석: " + remainSit + "/" + maxSit;
			reserveBtn[i] = new JButton();

			if (isUserIncluded) {
				reserveBtn[i].setBackground(Color.orange);
				if (!roomsData.getJSONObject(i).isNull("resetDate")) {
					String datestr = roomsData.getJSONObject(i).getString("resetDate");
					try {
					    // 타임존이 포함된 ISO 8601 문자열로부터 Asia/Seoul 타임존의 LocaDateTime 오브젝트 획득
					    LocalDateTime dateTime = LocalDateTime.from(

					        Instant.from(
					            DateTimeFormatter.ISO_DATE_TIME.parse(datestr)
					        ).atZone(ZoneId.of("Asia/Seoul"))
					    );
					    datestr = dateTime.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일(E) HH시 mm분"));
					// 파씽 오류시 DateTimeParseException 예외 발생
					} catch (DateTimeParseException ex) {
						ex.printStackTrace();
					    // 예외 처리 로직 작성
					}
					btnText += "<br>" + "좌석 초기화: " + datestr;
					System.out.println(btnText);

				}
			} else {
				if (!roomsData.getJSONObject(i).isNull("acceptDate")) {
					String datestr = roomsData.getJSONObject(i).getString("acceptDate");
					try {
					    // 타임존이 포함된 ISO 8601 문자열로부터 Asia/Seoul 타임존의 LocaDateTime 오브젝트 획득
					    LocalDateTime dateTime = LocalDateTime.from(

					        Instant.from(
					            DateTimeFormatter.ISO_DATE_TIME.parse(datestr)
					        ).atZone(ZoneId.of("Asia/Seoul"))
					    );
					    datestr = dateTime.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일(E) HH시 mm분"));
					// 파씽 오류시 DateTimeParseException 예외 발생
					} catch (DateTimeParseException ex) {
						ex.printStackTrace();
					    // 예외 처리 로직 작성
					}
					btnText += "<br>" + "예약시작일: " + datestr;
					System.out.println(btnText);

				}
				reserveBtn[i].setBackground(Color.gray.brighter());
			}
			reserveBtn[i].setText(btnText);
			reserveBtn[i].setBorder(null);
			reserveBtn[i].setPreferredSize(new Dimension(130,100));
			reserveBtn[i].setFocusable(false);
			btnPanel.add(reserveBtn[i]);
			scroll.setBorder(null);
			add(scroll);
		}
	}
	
	
}