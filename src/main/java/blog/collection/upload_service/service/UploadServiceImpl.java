package blog.collection.upload_service.service;

import blog.collection.upload_service.config.CloudinaryConfig;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private CloudinaryConfig cloudinaryConfig;

    @Override
    public String uploadMedia(MultipartFile multipartFile) throws Exception {
        try {
            Map uploadResult = cloudinaryConfig.cloudinary().uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("resource_type", "auto",
                    "folder", "media_uploads"));
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new Exception("Failed to upload");
        }
    }
}
