package ru.gb.server.dto;

import ru.gb.server.dto.BasicRequest;

public class AuthRequest implements BasicRequest {

    private String login;
    private String password;

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getType() {
        return "auth";
    }
}
