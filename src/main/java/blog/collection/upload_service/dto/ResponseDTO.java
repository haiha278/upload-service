package blog.collection.upload_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private int responseCode;
    private String responseMessage;
    private T responseData;
}
