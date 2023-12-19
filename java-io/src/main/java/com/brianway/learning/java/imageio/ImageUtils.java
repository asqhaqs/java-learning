package com.brianway.learning.java.imageio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-11-15
 */
public class ImageUtils {
    public static String getImageFormat(byte[] imageData) throws IOException {
        String[] writerFormatNames = ImageIO.getWriterFormatNames();
        System.out.println("write: " + Arrays.asList(writerFormatNames));
        String[] readerFormatNames = ImageIO.getReaderFormatNames();
        System.out.println("reader: " + Arrays.asList(readerFormatNames));
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        ImageInputStream iis = ImageIO.createImageInputStream(bis);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
        String format = "";
        while (imageReaders.hasNext()) {
            ImageReader reader = imageReaders.next();
            format = reader.getFormatName();
        }
        System.out.printf("format: %s%n", format);
        return "";
    }

    public static void main(String[] args) throws IOException {
        String pdfPath = "/Users/xudong/Downloads/v7vol2a.pdf";
        String imagePath = "/Users/xudong/Desktop/557c6e7786ee8dd5d5ccdbf2e1acc6d7.jpeg";
        byte[] imageByte = FileUtils.readFileToByteArray(new File(imagePath));
        byte[] pdfByte = FileUtils.readFileToByteArray(new File(pdfPath));
        ImageUtils.getImageFormat(imageByte);
        ImageUtils.getImageFormat(pdfByte);
    }
}
