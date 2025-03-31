package blog.collection.upload_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UploadService {
    String uploadMedia(MultipartFile multipartFile) throws Exception;

    List<String> uploadMultipleFiles(List<MultipartFile> files);
}
