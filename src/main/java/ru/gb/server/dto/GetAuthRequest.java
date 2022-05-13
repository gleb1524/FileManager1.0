package ru.gb.server.dto;

public class GetAuthRequest implements BasicRequest{
    @Override
    public String getType() {
        return "auth";
    }
}
