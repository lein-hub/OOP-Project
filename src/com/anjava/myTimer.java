package com.anjava;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TimeZone;

public class myTimer {
	
	public static void main(String[] args)	{
		new myTimer();
	}
	
	public myTimer() {
		System.out.println(checkTime());
	}

	private static long checkTime() {
		long distance = 0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			df.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			Date now = df.parse(df.format(new Date())); // 현재시간
			Date reset = df.parse("2021-04-05T06:02:00.000+0000"); // 리셋할 날짜
			distance = reset.getTime()-now.getTime(); // 리셋할 날짜까지 남은 시간(ms)
			System.out.println(df.format(reset));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return distance;
	}
}
