package com.example.imagecrusher;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@RestController
public class ImageCrusherController {

    @PostMapping("/editImage")
    public @ResponseBody String editImage(@RequestBody String inputImage) throws Exception {
        // decode Base64
        byte[] decodedInputImage = Base64.getDecoder().decode(inputImage);

        //crush input image
        byte[] outputImage = decodedInputImage;

        //crush and rotate input image 3x
        for (int i = 0; i < 12; i++) {
            ImageCrusher.crush(outputImage);
            File outputFile = new File("output.jpg");
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputImage);
            ByteArrayInputStream bis = new ByteArrayInputStream(outputImage);
            BufferedImage bImg = ImageIO.read(bis);
            outputImage = ImageRotator.rotate();
        }
        //invert, merge, whatever

        //store output file locally (just in case)

        System.out.println("image created");

        //return output image
        return Base64.getEncoder().encodeToString(outputImage);
    }
}
