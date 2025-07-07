package com.subnity.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

  STREAMING("스트리밍"),
  MUSIC("음악"),
  PRODUCTIVITY("생산성"),
  FITNESS("피트니스"),
  NEWS("뉴스"),
  GAME("게임"),
  CLOUD("클라우드"),
  EDUCATION("교육"),
  ETC("기타");

  private final String value;
}
