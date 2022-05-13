package ru.gb.server.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UploadRequest implements BasicRequest{
    private String remPath;
    private byte[] data;
    private byte[][] bigData;
    private String name;



    private File file;

    public UploadRequest(File file,String remPath) {
//        this.remPath = remPath;
//        this.file = file;
//
//        try {
//                data = Files.readAllBytes(file.toPath());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String getRemPath() {
        return remPath;
    }


    public byte[] getData() {
        return data;
    }

    public String getName() {
        name = file.getName();
        return name;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getType() {
        return "upload";
    }
}
