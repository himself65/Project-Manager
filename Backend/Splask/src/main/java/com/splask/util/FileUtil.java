package com.splask.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    /**
     * util function to store image file on server filesystem
     *
     * @param imageBytes    image in bytes uploaded in base64 format
     * @param fPath         where to store the image file
     * @param fName         file name
     * @throws IOException
     */
    public static void saveFile(byte[] imageBytes, String fPath, String fName) throws IOException {

        // parse base64 and convert it to an image
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // create directory if not exist
        Files.createDirectories(Paths.get(fPath));

        // create the image file
        try {
            File file = new File(fPath+fName);

            if (file.createNewFile()){
                ImageIO.write(img, "jpg", file);
            }else{

                //overwrites the file
                ImageIO.write(img,"jpg",file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load image file from filesystem
     *
     * @param filePath      absolute path to the file
     * @return              image as a byte array
     * @throws IOException
     */
    public static byte[] loadFile(String filePath) throws IOException{
        File file = new File(filePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(file), "jpg", stream);

        return stream.toByteArray();
    }

}
