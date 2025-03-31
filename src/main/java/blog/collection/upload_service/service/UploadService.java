package blog.collection.upload_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String uploadMedia(MultipartFile multipartFile) throws Exception;
}
