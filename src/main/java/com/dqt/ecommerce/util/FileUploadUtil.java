package com.dqt.ecommerce.util;

import com.dqt.ecommerce.constant.SystemConstant;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class FileUploadUtil {
    public static String convertFormMultipartFileToString(MultipartFile multipartFile) throws IOException {
        Boolean flag = false;
        String image = null;

        String original = multipartFile.getOriginalFilename().toString();
        if (original.contains("png") || original.contains("jpg") || original.contains("jpeg")) {
            image = SystemConstant.FILE_NAME_IMAGE_DEFAULT;
            flag = false;
        }

//        randomNameFileImage
        String randomFileName = String.valueOf(new Date().getTime()) + "_" + multipartFile.getOriginalFilename().toString();

        image = randomFileName;
        Path uploadPath = Paths.get("src/main/resources/static/admin/images/");
//        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        if (!multipartFile.isEmpty()) {
            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = uploadPath.resolve(randomFileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                flag = true;
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + randomFileName, ioe);
            }
        }

        if (!flag) {
            return SystemConstant.FILE_NAME_IMAGE_DEFAULT;
        }
        return randomFileName;
    }

    public static MultipartFile convertFormStringToMultipartFile(String getPhoto) throws IOException {
        File file = new File("uploads/" + getPhoto);
        System.out.println("File: " + file);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new
                    MockMultipartFile("file", file.getName(),
                    "text/plain", IOUtils.toByteArray(fileInputStream));

            return multipartFile;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void saveFile(String fileName,
//                                MultipartFile multipartFile) throws IOException {
//        Path uploadPath = Paths.get("uploads/");
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//        if (!multipartFile.isEmpty()) {
//            try {
//                InputStream inputStream = multipartFile.getInputStream();
//                Path filePath = uploadPath.resolve(fileName);
//                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            } catch (IOException ioe) {
//                throw new IOException("Could not save image file: " + fileName, ioe);
//            }
//        }
//    }

}

