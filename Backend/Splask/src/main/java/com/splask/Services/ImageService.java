package com.splask.Services;

import com.splask.Models.User;
import com.splask.Repositories.UserDB;

import com.splask.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private static final String directory = "/home/splask_files/";

    @Autowired
    UserDB userRepo;

    public byte[] getUserImageById(Integer id) throws IOException{
        User user = userRepo.getById(id);
        String filePath = user.getImagePath();
        File file = new File(filePath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return bytes;
    }


    public void uploadNewUserImage(Integer id,byte[] imgBytes) throws IOException{
        User user = userRepo.getById(id);
        String fName = user.getUsername() + ".jpg";
        FileUtil.saveFile(imgBytes,directory,fName);
        user.setImagePath(directory+fName);
        userRepo.save(user);

    }


}
