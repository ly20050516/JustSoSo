package com.ly.picassostudy.bean;

import java.util.ArrayList;

public class Pictures {

	String serverName;
	String clientName;
	ArrayList<PictureData> pictures;
	
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
