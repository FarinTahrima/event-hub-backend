package com.fdmgroup.backend_eventhub.modules.service;

import com.fdmgroup.backend_eventhub.eventsession.model.Content;
import com.fdmgroup.backend_eventhub.eventsession.repository.IContentRepository;
import com.fdmgroup.backend_eventhub.modules.model.ImageModule;
import com.fdmgroup.backend_eventhub.modules.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {

    private ImageRepository imageRepository;

    private final String IMAGE_BASE_URL = "src/main/resources/images/";

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageModule createImage(MultipartFile imageFile, ImageModule imageModule, Content content) {
        try {
            //save image in file directory
            Path uploadPath = Path.of(IMAGE_BASE_URL);
            String imageUrl = content.getId() + "-" + imageModule.getName();
            Path filePath = uploadPath.resolve(imageUrl);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            //save image in database
            ImageModule imageToSave = new ImageModule();
            imageToSave.setName(imageModule.getName());
            imageToSave.setFilePath(imageModule.getFilePath());
            imageToSave.setContent(content);
            return imageRepository.save(imageModule);

        } catch (Exception e) {
            throw new RuntimeException("Image not saved.");
        }

    }

    public void deleteImage(long moduleId) throws IOException {

        ImageModule imageModule = imageRepository.getReferenceById(moduleId);
        Path path = Path.of(IMAGE_BASE_URL);
        String imageUrl = imageModule.getContent().getId() + "-" + imageModule.getName();
        Path filePath = path.resolve(imageUrl);
        Files.delete(filePath);

        imageRepository.deleteById(moduleId);

    }

}
