package com.subnity.common.config.jpa;

import com.subnity.common.utils.CryptoUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  @Override
  public String convertToDatabaseColumn(String text) {
    return (!StringUtils.hasText(text)) ? null : CryptoUtils.encrypt(text);
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    return (!StringUtils.hasText(dbData)) ? null : CryptoUtils.decrypt(dbData);
  }
}
