package com.subnity.domain.member.repository;

import com.subnity.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, String> {
}
