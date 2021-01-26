/**
 * 
 */
package com.kanpekiti.doctoresensucasa.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author macpro
 *
 */
public class VideoCallChannel {

	@SerializedName("tkn")
	@Expose
	private String tkn;

	@SerializedName("channel")
	@Expose
	private String channel;

	/**
	 * 
	 */
	public VideoCallChannel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the tkn
	 */
	public String getTkn() {
		return tkn;
	}

	/**
	 * @param tkn the tkn to set
	 */
	public void setTkn(String tkn) {
		this.tkn = tkn;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	

}
