package com.test.m281220;

public class TokenDTO {

    private String method;
    private String auth_token;

    public TokenDTO(String method, String auth_token) {
        this.method = method;
        this.auth_token = auth_token;

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
