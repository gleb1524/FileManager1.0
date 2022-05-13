package ru.gb.server.dto;

public class GetFileListRequest implements BasicRequest{
    @Override
    public String getType() {
        return "GetFileList";
    }
}
