package me.hysong.libhyextended.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.Getter;
import lombok.Setter;
import me.hysong.libhycore.CoreBase64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class QRCode {

    private String data;
    private int width;
    private int height;

    public QRCode(String data, int width, int height) {
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public byte[] toBytes() throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        }
    }

    public String toRawBase64() throws IOException, WriterException {
        return Base64.getEncoder().encodeToString(toBytes());
    }

    public String toBase64() throws IOException, WriterException {
        return "data:image/png;base64," + toRawBase64();
    }

    public void save(String to) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(to);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static String toBase64(String data, int width, int height) {
        try {
            return new QRCode(data, width, height).toBase64();
        } catch (Exception e) {
            return "";
        }
    }

    public static String toRawBase64(String data, int width, int height) {
        try {
            return new QRCode(data, width, height).toRawBase64();
        } catch (Exception e) {
            return "";
        }
    }
}
