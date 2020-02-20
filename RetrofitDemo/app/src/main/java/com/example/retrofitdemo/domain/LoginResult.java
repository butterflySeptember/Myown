package com.example.retrofitdemo.domain;

public class LoginResult {

	/**
	 * success : true
	 * code : 10000
	 * message : 这是你提交上来的数据：拉大锯 - 12938471902387
	 * data : 2a142e7c-754e-413d-a662-77c27abc097c
	 */

	private boolean success;
	private int code;
	private String message;
	private String data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "LoginResult{" +
				"success=" + success +
				", code=" + code +
				", message='" + message + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
