//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.imagecrusher;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageRotator {
    private BufferedImage image;
    private String filename;

    public ImageRotator(BufferedImage image) throws Exception {
        this.image = image;
    }

    public ImageRotator() {
    }

    public static byte[] rotate() throws IOException {
        BufferedImage image = ImageIO.read(new File("output.jpg"));

        final double rads = Math.toRadians(90);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image,rotatedImage);
        System.out.println("rotated");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(rotatedImage, "JPG", baos);
        return baos.toByteArray();
    }

    /*public void rotate() throws Exception {
        final double rads = Math.toRadians(90);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image,rotatedImage);
        System.out.println("rotated");
        save(rotatedImage);
    }*/

    private void save(BufferedImage rotatedImage) throws IOException {
        ImageIO.write(rotatedImage, "JPG", new File("C:\\Users\\jknjk\\Documents\\image_crusher\\rotated files\\rotated_" + filename));
    }

    public void setImage(BufferedImage crushedImage) {
        this.image = crushedImage;
    }

    public void setFilename(String name) {
        this.filename = name;
    }
}
