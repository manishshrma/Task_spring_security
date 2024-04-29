package com.eminence.innovation.task.DTO;

public class LoginResponse {

	private String token;

    private long expiresIn;
    public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setToken(String token) {
		this.token = token;
	}

    public String getToken() {
        return token;
    }


}