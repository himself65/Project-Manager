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

    private static String directory = "C:";

    @Autowired
    UserDB userRepo;

    public byte[] getUserImageById(Integer id) throws IOException{
        Optional<User> optionalUser = userRepo.findById(id);
        User user = optionalUser.get();
        String file = user.getImagePath();

        return FileUtil.loadFile(file);
    }


    public void uploadNewUserImage(Integer id,byte[] imgBytes) throws IOException{
        User user = userRepo.getById(id);
        String fName = user.getUsername() + ".jpg";
        FileUtil.saveFile(imgBytes,directory,fName);
        user.setImagePath(directory+fName);
        userRepo.save(user);

    }


}
