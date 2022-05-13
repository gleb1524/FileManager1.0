package ru.gb.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo {
    public enum FileType{

        FILE("F"), DIRECTORY("D");
        private String fileType;

        public String getFileType() {
            return fileType;
        }

        FileType(String fileType) {
            this.fileType = fileType;
        }
    }

    private FileType fileType;
    private String fileName;
    private long size;
    private LocalDateTime lastModified;

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public FileInfo(Path path) {
        try{
            this.fileName = path.getFileName().toString();
            this.size = Files.size(path);
            this.lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(),
                    ZoneOffset.ofHours(0));
            this.fileType = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
            if(fileType==FileType.DIRECTORY){
                this.size = -1L;
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to build file info from path");
        }
    }
}
