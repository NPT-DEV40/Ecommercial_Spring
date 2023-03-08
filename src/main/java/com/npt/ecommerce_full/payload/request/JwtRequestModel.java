package com.npt.ecommerce_full.payload.request;

import java.io.Serializable;

public class JwtRequestModel implements Serializable {
    private String userName;
    private String password;

    public JwtRequestModel() {
    }

    public JwtRequestModel(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
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
}
