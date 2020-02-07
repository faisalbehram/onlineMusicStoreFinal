package com.onlinemusicstore.app.models;

public class CaptchaResponse {
	
	private Boolean success;
	
	private String Challenge_ts;
	private String hostName;
	
	
	
    public String getChallenge_ts() {
		return Challenge_ts;
	}
	public void setChallenge_ts(String challenge_ts) {
		Challenge_ts = challenge_ts;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
    
    

}
