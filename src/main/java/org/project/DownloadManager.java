package org.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.project.config.AppConfig;
import org.project.models.FileInfo;

import java.io.File;

public class DownloadManager {

    @FXML
    private TableView<FileInfo> tableView;

    @FXML
    private TextField urlTextField;

    public int index = 0;

    @FXML
    void downloadButtonClicked(ActionEvent event) {
        String text = this.urlTextField.getText();
        System.out.println("Field text is = "+text);
//        feathing url from urlfield of download manager
        String url=urlTextField.getText().trim();
        String filename=url.substring(url.lastIndexOf("/")+1);
//        setting status at initial phase
        String status="STARTING";
//        setting default action
        String action="OPEN";
//        facting path to keep downloaded file and adding / to the last of path
        String path= AppConfig.DOWNLOAD_PATH+ File.separator+filename;
//        getting file information using FileInfo object
        FileInfo file = new FileInfo((index + 1)+ "", filename, url, status, action,path);
        this.index=this.index+1;
//        creating thread for multiple donload
        DownloadThread thread = new DownloadThread(file, this);
//        adding and increasing the index or s.no of download items
        this.tableView.getItems().add(Integer.parseInt(file.getIndex())-1,file);
//        start multuple thred to download multiple file
        thread.start();
//        make url text field set empty ,after after downloading one file
        this.urlTextField.setText("");

    }

//    updating UI
    public void updateUI(FileInfo metaFile) {
//        printing the file information like name , url , status etc.
        System.out.println(metaFile);
//        settting the content in tabe
        FileInfo fileInfo = this.tableView.getItems().get(Integer.parseInt(metaFile.getIndex()) - 1);
//        updating the status
        fileInfo.setStatus(metaFile.getStatus());
//        refreshing UI
        this.tableView.refresh();

        System.out.println("__________________________________");
    }

//    intitializing the contect of table
    @FXML
    public void initialize(){
//        getting the vale for cell 1 of table
        TableColumn<FileInfo,String> sn = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(0);
//        with the help of lembda sunction setting the cell value
        sn.setCellValueFactory(p ->{
            return p.getValue().indexProperty();
        });

        TableColumn<FileInfo,String> filename = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(1);
//        with the help of lembda sunction setting the cell value
        filename.setCellValueFactory(p ->{
            return p.getValue().nameProperty();
        });

        TableColumn<FileInfo,String> url = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(2);
//        with the help of lembda sunction setting the cell value
        url.setCellValueFactory(p ->{
            return p.getValue().urlProperty();
        });
        TableColumn<FileInfo,String> status = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(3);
//        with the help of lembda sunction setting the cell value
        status.setCellValueFactory(p ->{
            return p.getValue().statusProperty();
        });
        TableColumn<FileInfo,String> action = (TableColumn<FileInfo, String>) this.tableView.getColumns().get(4);
//        with the help of lembda sunction setting the cell value
        action.setCellValueFactory(p ->{
            return p.getValue().actionProperty();
        });

    }
}
