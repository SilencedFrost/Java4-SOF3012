package com.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/upload")
@MultipartConfig
public class ImageFileHelper extends HttpServlet {

    public static void saveImage(Part filePart, String filename) throws IOException {
        String uploadPath = "src/main/webapp/images/" + filename;
        Path path = Paths.get(uploadPath);

        // Create directories if they don't exist
        Files.createDirectories(path.getParent());

        // Copy file from input stream
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, path, StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("Image saved to: " + path.toAbsolutePath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("file");
        String filename = getFilename(filePart);

        if (filename != null && !filename.isEmpty()) {
            saveImage(filePart, filename);
            response.getWriter().println("File uploaded successfully");
        } else {
            response.getWriter().println("No file selected");
        }
    }

    private String getFilename(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}