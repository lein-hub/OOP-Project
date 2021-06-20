package com.anjava;

import java.util.Date;

public class RoomCreator {
	private int roomNum = 99999;
	private int col = 99;
	private int row = 99;
	private int[] colBlank;
	private int[] rowBlank;
	private Date resetDate;
	private Date acceptDate;
	private int measure = 2;
	private int weekendInterval;
	private int openDeffer;
	private int day;
	private int weekNth;
	private boolean isShuffle = false;
	
	
	public boolean isShuffle() {
		return isShuffle;
	}
	public void setShuffle(boolean isShuffle) {
		this.isShuffle = isShuffle;
	}
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int[] getColBlank() {
		return colBlank;
	}
	public void setColBlank(int[] colBlank) {
		this.colBlank = colBlank;
	}
	public int[] getRowBlank() {
		return rowBlank;
	}
	public void setRowBlank(int[] rowBlank) {
		this.rowBlank = rowBlank;
	}
	public Date getResetDate() {
		return resetDate;
	}
	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}
	public Date getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	public int getMeasure() {
		return measure;
	}
	public void setMeasure(int measure) {
		this.measure = measure;
	}
	public int getWeekendInterval() {
		return weekendInterval;
	}
	public void setWeekendInterval(int weekendInterval) {
		this.weekendInterval = weekendInterval;
	}
	public int getOpenDeffer() {
		return openDeffer;
	}
	public void setOpenDeffer(int openDeffer) {
		this.openDeffer = openDeffer;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getWeekNth() {
		return weekNth;
	}
	public void setWeekNth(int weekNth) {
		this.weekNth = weekNth;
	}
	
	
}
