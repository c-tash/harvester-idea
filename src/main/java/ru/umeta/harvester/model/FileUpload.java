package ru.umeta.harvester.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ctash on 11.03.2015.
 */
public class FileUpload {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
