// JSON Java Class Generator
// Written by Bruce Bao
// Used for API: http://www.weather.com.cn/data/sk/101010100.html
package com.gypsophila.trygank.engine;

import com.gypsophila.trygank.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;


public class User implements Serializable, Cloneable {
	/**
	 * @Fields: serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static User instance;

	private User() {

	}

	public static User getInstance() {
		if (instance == null) {
			Object object = Utils.restoreObject(AppConstants.CACHEDIR + TAG);
			if (object == null) { // App第一次启动，文件不存在，则新建之
				object = new User();
				Utils.saveObject(AppConstants.CACHEDIR + TAG, object);
			}

			instance = (User) object;
		}

		return instance;
	}

	public final static String TAG = "User";

	private String loginName;
	private String userName;
	private int score;
	private boolean loginStatus;
	
	public void reset() {
		loginName = null;
		userName = null;
		score = 0;
		loginStatus = false;
	}
	
	public void save() {
		Utils.saveObject(AppConstants.CACHEDIR + TAG, this);
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
	public boolean isLogin() {
		return loginStatus;
	}
	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	// -----------以下3个方法用于序列化-----------------
	public User readResolve() throws ObjectStreamException, CloneNotSupportedException {
		instance = (User) this.clone();
		return instance;
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
	}

	public Object Clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
