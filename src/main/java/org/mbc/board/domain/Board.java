package org.mbc.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder // 빌더 패턴 setter 대신 활용
@AllArgsConstructor // 모든 필드값으로 생성자 생성
@NoArgsConstructor // 기본 생성자
@ToString // 객체 주소가 아닌 값을 출력
public class Board extends BaseEntity { // extends BaseEntity (날짜 관련 된 jpa 연결)

    @Id // pk로 선언용 ( notnull, unique, indexing )
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 번호 생성용
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    //Hibernate:
    //    alter table if exists board
    //       add column moddate datetime(6)
    //Hibernate:
    //    alter table if exists board
    //       add column regdate datetime(6)
    //Hibernate:
    //    alter table if exists board
    //       modify column content varchar(2000) not null
    //Hibernate:
    //    alter table if exists board
    //       modify column title varchar(500) not null
    //Hibernate:
    //    alter table if exists board
    //       modify column writer varchar(50) not null

    public void change(String title, String content) {
        // 제목과 내용만 수정하는 메서드 (세터 대체용)
        this.title = title;
        this.content = content;

    }

}
