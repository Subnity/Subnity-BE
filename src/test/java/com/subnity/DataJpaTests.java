package com.subnity;

import com.subnity.domain.member.Member;
import com.subnity.domain.member.enums.Role;
import com.subnity.domain.member.repository.JpaMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class DataJpaTests {

  @Autowired
  private JpaMemberRepository jpaMemberRepository;

  @BeforeEach
  @DisplayName("더미 데이터 생성")
  void dummyCreate() {
    // 회원 생성
    jpaMemberRepository.save(
      Member.builder()
        .memberId("KyungMin")
        .name("김경민")
        .role(Role.USER)
        .profileUrl("")
        .isNotification(true)
        .mail("k.kyungmin2892@gmail.com")
        .build()
    );

    Member member = this.jpaMemberRepository.findById("KyungMin").orElse(null);

    Assertions.assertNotNull(member);
    Assertions.assertEquals("KyungMin", member.getMemberId());
  }

  @Test
  @DisplayName("더미 데이터 저장 확인")
  void test() {
    Member member = this.jpaMemberRepository.findById("KyungMin").orElse(null);

    Assertions.assertNotNull(member);
    Assertions.assertEquals("KyungMin", member.getMemberId());
  }
}
