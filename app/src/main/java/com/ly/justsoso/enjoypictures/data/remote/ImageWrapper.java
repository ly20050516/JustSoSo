package com.ly.justsoso.enjoypictures.data.remote;

import java.util.ArrayList;

public class ImageWrapper {
	
	String queryEnc;
	long displayNum;
	String bdIsClustered;
	int listNum;
	String bdFmtDispNum;
	String bdSearchTime;
	int isNeedAsyncRequest;
	ArrayList<ImageData> data;
	public String getQueryEnc() {
		return queryEnc;
	}
	public void setQueryEnc(String queryEnc) {
		this.queryEnc = queryEnc;
	}
	public long getDisplayNum() {
		return displayNum;
	}
	public void setDisplayNum(long displayNum) {
		this.displayNum = displayNum;
	}
	public String getBdIsClustered() {
		return bdIsClustered;
	}
	public void setBdIsClustered(String bdIsClustered) {
		this.bdIsClustered = bdIsClustered;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public String getBdFmtDispNum() {
		return bdFmtDispNum;
	}
	public void setBdFmtDispNum(String bdFmtDispNum) {
		this.bdFmtDispNum = bdFmtDispNum;
	}
	public String getBdSearchTime() {
		return bdSearchTime;
	}
	public void setBdSearchTime(String bdSearchTime) {
		this.bdSearchTime = bdSearchTime;
	}
	public int getIsNeedAsyncRequest() {
		return isNeedAsyncRequest;
	}
	public void setIsNeedAsyncRequest(int isNeedAsyncRequest) {
		this.isNeedAsyncRequest = isNeedAsyncRequest;
	}
	public ArrayList<ImageData> getData() {
		return data;
	}
	public void setData(ArrayList<ImageData> data) {
		this.data = data;
	}
	
	
}
