package com.ruta_del_valle.app_hosteria.rest_api.security;

public class JwtDto {

    private String token;
    private String type;
    private String username;
    private Object[] authorities;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Object[] authorities) {
        this.authorities = authorities;
    }
}
