package com.management.progettodigestioneacquisti.converterimage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Base64ImageConverter {
    public static BufferedImage convert(String base64Image) throws IOException {
        String[] parts = base64Image.split(",");
        String imageString = parts[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bis);
        bis.close();
        return image;
    }
}
