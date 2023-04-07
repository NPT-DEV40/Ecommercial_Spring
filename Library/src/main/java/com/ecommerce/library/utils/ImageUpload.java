package com.ecommerce.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER  = "D:\\Desktop\\JAVA WEB\\Ecommerce_Springboot\\Ecommerce\\Admin\\src\\main\\resources\\static\\img\\imageProduct";


    public Boolean uploadImage(MultipartFile image) {
        boolean isUpload = false;
        try {
            Files.copy(image.getInputStream(), Paths.get(UPLOAD_FOLDER + File.separator, image.getOriginalFilename()) , StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }



    public boolean checkExisted(MultipartFile image) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + "\\" + image.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }
}
