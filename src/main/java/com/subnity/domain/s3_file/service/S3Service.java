package com.subnity.domain.s3_file.service;

import com.subnity.common.api_response.exception.GeneralException;
import com.subnity.common.api_response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  private final S3Client s3Client;

  // 파일 업로드
  public String uploadFile(MultipartFile file) {
    String originalFileName = file.getOriginalFilename();
    String fileName = UUID.randomUUID() + "-" + originalFileName;

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
      .bucket(bucket)
      .key(fileName)
      .acl(ObjectCannedACL.PUBLIC_READ)
      .contentType(file.getContentType())
      .build();

    try {
      s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    } catch (Exception e) {
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "파일 업로드에 실패했습니다.");
    }

    return String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s", bucket, fileName);
  }

  // 파일 조회

  // 파일 삭제
}
