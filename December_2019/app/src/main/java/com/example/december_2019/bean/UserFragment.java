package com.example.december_2019.bean;

public class UserFragment {

	private String userId;
	private String userName;
	private String userLocation;

	public UserFragment(String userId, String userName, String userLocation) {
		this.userId = userId;
		this.userName = userName;
		this.userLocation = userLocation;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
}
