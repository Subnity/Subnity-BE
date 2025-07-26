package com.subnity.domain.s3_file;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "s3_file")
public class S3File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "s3_file_id", nullable = false)
  private Long s3Id;

  @Column(name = "original_name", nullable = false)
  private String originalName;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(name = "file_size", nullable = false)
  private long fileSize;

  @Column(name = "s3_url", nullable = false)
  private String s3Url;
}
