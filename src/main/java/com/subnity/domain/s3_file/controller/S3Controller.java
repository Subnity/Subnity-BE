package com.subnity.domain.s3_file.controller;

import com.subnity.common.api_response.ApiResponse;
import com.subnity.domain.s3_file.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/s3")
@RequiredArgsConstructor
@Tag(name = "S3", description = "S3 관련 API")
public class S3Controller {
  private final S3Service s3Service;

  @PostMapping(value = "/put", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "S3 파일 업로드", description = "S3 파일 업로드 엔드포인트")
  public ApiResponse<String> uploadFile(@RequestPart MultipartFile file) {
    return ApiResponse.onSuccess(s3Service.uploadFile(file));
  }

  @DeleteMapping(value = "/delete")
  @Operation(summary = "S3 파일 삭제", description = "S3 파일 삭제 엔드포인트")
  public ApiResponse<Void> deleteFile(String fileUrl) {
    s3Service.deleteFile(fileUrl);
    return ApiResponse.onSuccess();
  }
}
