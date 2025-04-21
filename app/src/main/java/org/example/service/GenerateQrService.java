package org.example.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class GenerateQrService {
    public String generateQrAsByte(String qrData,int width,int height) throws
            WriterException, IOException{
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bitMatrix=qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE,width,height);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        MatrixToImageConfig config=new MatrixToImageConfig(0xFF000002 , 0xFFFFC041);
        MatrixToImageWriter.writeToStream(bitMatrix,"PNG",outputStream,config);

        byte[] qrByteArray=outputStream.toByteArray();

        String qrCode= Base64.getEncoder().encodeToString(qrByteArray);

        return qrCode;

    }
}
