package com.ecommerce.userservice.config;

import java.util.zip.CRC32;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


@Component
public class QRCodeDataBuilder {

    public  String buildQRCodeData(String merchantID, String transactionAmount, String currency) {
        StringBuilder qrData = new StringBuilder();
    
        // Payload Format Indicator
        qrData.append("00").append("02").append("01");
    
        // Point of Initiation Method (Dynamic QR)
        qrData.append("01").append("02").append("12");
    
        // Merchant Account Information
        qrData.append("26")
              .append(String.format("%02d", merchantID.length()))
              .append(merchantID);
    
        // Transaction Amount
        qrData.append("54")
              .append(String.format("%02d", transactionAmount.length()))
              .append(transactionAmount);
    
        // Currency Code
        qrData.append("53")
              .append(String.format("%02d", currency.length()))
              .append(currency);
    
        return qrData.toString(); // Return data without CRC
    }

    public  String calculateCRC(String data) {
        CRC32 crc = new CRC32();
        crc.update(data.getBytes());
        return String.format("%04X", crc.getValue());
    }    

    public  byte[] generateQRCode(String qrData, int width, int height) throws Exception {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, width, height);

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
        }
    }

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ImageIO.write(image, "png", byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
}


}
