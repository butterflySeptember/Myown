package com.example.myapplicationsql.pojo;

public class User {
	private int _id;

	private String userName;

	private String password;

	private String sex;

	private int age;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"_id=" + _id +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", sex='" + sex + '\'' +
				", age=" + age +
				'}';
	}
}
