package com.school.communication_service.service;

import com.school.communication_service.dto.DocumentResponse;
import com.school.communication_service.entity.Document;
import com.school.communication_service.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    // The user lives in auth-service, so only the user id is stored here.
    // Checking that the user id exists will be added in Step 6 (OpenFeign).

    private final String UPLOAD_DIR = "uploads/";

    private final List<String> ALLOWED_TYPES = List.of(
            "application/pdf", "image/png", "image/jpeg", "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    private final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    public DocumentResponseWrapper upload(MultipartFile file, Long uploadedByUserId) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size exceeds the 5MB limit");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new RuntimeException("File type not allowed: " + file.getContentType());
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String storedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), filePath);

            Document document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setStoredFileName(storedFileName);
            document.setFileType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setUploadedById(uploadedByUserId);

            Document saved = documentRepository.save(document);
            return new DocumentResponseWrapper(saved);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
    }

    public List<DocumentResponse> getAll() {
        return documentRepository.findAll()
                .stream().map(DocumentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public Document getById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + id));
    }

    public byte[] downloadFile(Long id) {
        Document document = getById(id);
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(document.getStoredFileName());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        Document document = getById(id);
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(document.getStoredFileName());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // continue even if file delete fails, still remove DB record
        }
        documentRepository.deleteById(id);
    }

    // Small internal helper class just to pass the saved entity back to the controller
    public static class DocumentResponseWrapper {
        public Document entity;
        public DocumentResponseWrapper(Document entity) {
            this.entity = entity;
        }
    }
}