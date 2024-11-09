package com.fdmgroup.backend_eventhub.modules.service;

import com.fdmgroup.backend_eventhub.eventsession.model.Content;
import com.fdmgroup.backend_eventhub.eventsession.repository.IContentRepository;
import com.fdmgroup.backend_eventhub.modules.model.VideoModule;
import com.fdmgroup.backend_eventhub.modules.repository.IVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    IContentRepository contentRepository;

    @Autowired
    IVideoRepository videoRepository;

    private final String VIDEO_BASE_URL = "src/main/resources/videos/";

    // save video in the local server and database
    public VideoModule createVideo(MultipartFile videoFile, String videoTitle, long durationSecond, long contentId) {
        // save video in the file directory
        try {
            Path uploadPath = Path.of(VIDEO_BASE_URL);
            String videoUrl = contentId + "-" + videoTitle;
            Path filePath = uploadPath.resolve(videoUrl);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(videoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // save video in the database
            Optional<Content> content = contentRepository.findById(contentId);

            //TODO: encode video
            if (content.isPresent()) {
                VideoModule videoModule = new VideoModule();
                videoModule.setContent(content.get());
                videoModule.setVideoTitle(videoTitle);
                videoModule.setVideoUrl(videoUrl);
                videoModule.setDurationSecond(durationSecond);

                return videoRepository.save(videoModule);
            } else {
                throw new RuntimeException("Video not found.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Video not uploaded.");
        }

    }

    // remove video in the local server and database
    public VideoModule deleteVideo(long contentId, long moduleId) throws IOException {
        VideoModule videoModule = videoRepository.getReferenceById(moduleId);
        String videoUrl = videoModule.getVideoUrl();
        String path = "src/main/resources/" + videoUrl;

        // remove video from the file directory
        Path deletePath = Path.of(path);
        Path filePath = deletePath.resolve(videoUrl);
        Files.delete(filePath);

        videoRepository.deleteById(moduleId);

        return videoModule;
    }

    // get video by id
    public Optional<VideoModule> findVideoById(long id) {
        return videoRepository.findById(id);
    }
}
