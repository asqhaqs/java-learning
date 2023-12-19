package com.brianway.learning.java.imageio;

import cn.hutool.core.io.FileTypeUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-11-23
 */
public class FileTypeUtilCase {
    public static void main(String[] args) throws IOException {
        String pdfPath = "/Users/xudong/Downloads/v7vol2a.pdf";
        String imagePath = "/Users/xudong/Desktop/557c6e7786ee8dd5d5ccdbf2e1acc6d7.jpeg";
        byte[] imageByte = FileUtils.readFileToByteArray(new File(imagePath));
        byte[] pdfByte = FileUtils.readFileToByteArray(new File(pdfPath));
        InputStream imageInputStream = new ByteArrayInputStream(imageByte);
        InputStream pdfInputStream = new ByteArrayInputStream(pdfByte);
        String imageType = FileTypeUtil.getType(imageInputStream);
        String pdfType = FileTypeUtil.getType(pdfInputStream);
        System.out.println("imageType: " + imageType + " pdfType: " + pdfType);
    }
}
