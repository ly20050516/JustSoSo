package com.ly.justsoso.enjoypictures.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PictureData {
	@Id
	Long id;
	String thumbURL;
	String objURL;
	@Generated(hash = 2084427360)
	public PictureData(Long id, String thumbURL, String objURL) {
		this.id = id;
		this.thumbURL = thumbURL;
		this.objURL = objURL;
	}
	@Generated(hash = 892675782)
	public PictureData() {
	}
	public String getThumbURL() {
		return thumbURL;
	}
	public void setThumbURL(String thumbURL) {
		this.thumbURL = thumbURL;
	}
	public String getObjURL() {
		return objURL;
	}
	public void setObjURL(String objURL) {
		this.objURL = objURL;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
