package blog.collection.upload_service.service;

import blog.collection.upload_service.config.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadMedia(MultipartFile multipartFile) throws Exception {
        try {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("resource_type", "auto", "folder", "avatar_uploads"));
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new Exception("Failed to upload");
        }
    }

    @Override
    public List<String> uploadMultipleFiles(List<MultipartFile> files) {
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(files.size(), 10));
        List<Future<String>> futures = new ArrayList<>();
        List<String> urls = Collections.synchronizedList(new ArrayList<>());

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                futures.add(executor.submit(() -> {
                    Map<String, Object> result = cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap("resource_type", "auto", "folder", "uploads")
                    );
                    return (String) result.get("secure_url");
                }));
            }
        }

        for (Future<String> future : futures) {
            try {
                urls.add(future.get());
            } catch (Exception e) {
                urls.add("Error: " + e.getMessage());
            }
        }

        executor.shutdown();
        return urls;
    }
}
