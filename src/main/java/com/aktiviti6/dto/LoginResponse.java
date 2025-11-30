package com.aktiviti6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "PrincessToken";
    private String message;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Геттеры и сеттеры
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
