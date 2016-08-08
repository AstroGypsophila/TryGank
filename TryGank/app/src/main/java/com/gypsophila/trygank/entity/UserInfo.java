// JSON Java Class Generator
// Written by Bruce Bao
// Used for API: http://www.weather.com.cn/data/sk/101010100.html
package com.gypsophila.trygank.entity;

public class UserInfo {
	private String loginName;
	private String userName;
	private int score;
	private boolean loginStatus;

	public UserInfo() {

	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
}
