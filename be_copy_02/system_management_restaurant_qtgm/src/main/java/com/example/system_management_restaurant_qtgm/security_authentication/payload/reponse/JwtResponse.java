package com.example.system_management_restaurant_qtgm.security_authentication.payload.reponse;

import java.util.List;


public class JwtResponse {
    private int userId;
    private String token;
    private String type = "Bearer";
    private String username;
    private String name;
    private List<String> roles;

    public JwtResponse(String token, int userId, String name, String username, List<String> roles) {
        this.userId = userId;
        this.token = token;
        this.name = name;
        this.username = username;
        this.roles = roles;
    }

    public JwtResponse() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
