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

@RestController
@RequestMapping("/blog-collection/media")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO<String>> uploadFile(@RequestParam("file") MultipartFile file) {
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
}
