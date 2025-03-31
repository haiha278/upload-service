package blog.collection.upload_service.controller;

import blog.collection.upload_service.dto.ResponseDTO;
import blog.collection.upload_service.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/blog-collection/media")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload-avatar")
    public ResponseEntity<ResponseDTO<String>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = uploadService.uploadMedia(file);
            return new ResponseEntity<>(ResponseDTO.<String>builder()
                    .responseCode(HttpStatus.OK.value())
                    .responseMessage("Upload Successfully")
                    .responseData(fileUrl)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDTO.<String>builder()
                    .responseCode(HttpStatus.BAD_REQUEST.value())
                    .responseMessage("Upload Failed")
                    .responseData(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/post/upload")
    public ResponseEntity<ResponseDTO<Object>> uploadMultipleFilesForPost(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> fileUrls = uploadService.uploadMultipleFiles(files);
            return new ResponseEntity<>(ResponseDTO.<Object>builder()
                    .responseCode(HttpStatus.OK.value())
                    .responseMessage("Uploaded Successfully")
                    .responseData(fileUrls)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDTO.<Object>builder()
                    .responseCode(HttpStatus.BAD_REQUEST.value())
                    .responseMessage("Upload Failed: " + e.getMessage())
                    .responseData(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

}
