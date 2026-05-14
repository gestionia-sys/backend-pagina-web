package com.medicalDuarte.panelAdministrativo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/webp", "application/pdf"
    );

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file, String folderName) throws IOException {
        if (file.getContentType() == null || !ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Tipo de archivo no permitido por seguridad.");
        }

        try {
            // Ajustamos la ruta a la clínica
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", "clinica_duarte/" + folderName
            ));
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new IOException("Error al subir la imagen a Cloudinary: " + e.getMessage());
        }
    }

    public void deleteFileByUrl(String secureUrl) {
        try {
            int uploadIndex = secureUrl.indexOf("/upload/");
            if (uploadIndex != -1) {
                String afterUpload = secureUrl.substring(uploadIndex + 8);
                afterUpload = afterUpload.replaceFirst("^v\\d+/", "");
                int dotIndex = afterUpload.lastIndexOf(".");
                if (dotIndex != -1) {
                    String publicId = afterUpload.substring(0, dotIndex);
                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                    System.out.println("🗑️ Archivo limpiado de Cloudinary: " + publicId);
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo limpiar el archivo en Cloudinary: " + secureUrl);
        }
    }
}
