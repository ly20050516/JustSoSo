package com.ly.picassostudy.bean;

import java.util.ArrayList;

public class Pictures {

	String serverName;
	String clientName;
	int realCounts;
	ArrayList<PictureData> pictures;

	public int getRealCounts() {
		return realCounts;
	}
	public void setRealCounts(int realCounts) {
		this.realCounts = realCounts;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public ArrayList<PictureData> getPictures() {
		return pictures;
	}
	public void setPictures(ArrayList<PictureData> pictures) {
		this.pictures = pictures;
	}
}
