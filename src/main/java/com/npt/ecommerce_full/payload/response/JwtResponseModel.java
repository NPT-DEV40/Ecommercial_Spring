package com.npt.ecommerce_full.payload.response;

import java.io.Serializable;

public class JwtResponseModel implements Serializable {
    private String token;

    public JwtResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
