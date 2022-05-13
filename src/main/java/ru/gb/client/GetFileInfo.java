package ru.gb.client;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class GetFileInfo {

    public void updateList(Path path, TableView<FileInfo> fileDir, TextField address) {
        try {
            Path currentPath = path.normalize().toAbsolutePath();
            address.setText(currentPath.toString());
            fileDir.getItems().clear();
            fileDir.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            fileDir.sort();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "По какой-то неведомой причине не удалось обновить список файлов", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public  GetFileInfo(TableView<FileInfo> fileDir, Path path, TextField address) {

        updateList(path, fileDir, address);

        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn("Type");
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileType().toString()));
        fileTypeColumn.setPrefWidth(50);

        TableColumn<FileInfo, String> fileNameColumn = new TableColumn("Filename");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));
        fileNameColumn.setPrefWidth(100);

        TableColumn<FileInfo, String> lastModifiedColumn = new TableColumn("Last Modified");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        lastModifiedColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()
                .getLastModified().format(dtf)));
        lastModifiedColumn.setPrefWidth(120);

        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn("Size");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        fileSizeColumn.setCellFactory(column -> new TableCell<FileInfo, Long>(){
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null|| empty){
                    setText(null);
                    setStyle("");
                }else{
                    String text = String.format("%,d bytes", item);
                    if(item == -1L){
                        text = "[DIR]";
                    }else if(item >=10_000 && item <= 1_000_000){
                        text = String.format("%d KB", item/1024);
                    }else if(item >=1_000_000){
                        text = String.format("%d MB", item/(1024*1024));
                    }
                    setText(text);
                }
            }
        });
        fileSizeColumn.setPrefWidth(50);

        fileDir.getColumns().addAll(fileTypeColumn, fileNameColumn,fileSizeColumn, lastModifiedColumn);
    }


}
