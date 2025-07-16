package org.mbc.board.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mbc.board.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
                    // i 변수에 1~99까지 100개의 정수를 반복해서 생성
                    Board board = Board.builder()
                            .title("제목..." + i)
                            .content("내용..." + i)
                            .writer("user" + (i % 10))
                            .build();
                    /* log.info((board)); */
            Board result = boardRepository.save(board);
            //                            .save 메서드는 jpa에서 상속한 메서드로 값을 저장하는 용도
            //                                         이미 값이 있으면 update를 진행

            log.info("게시물 번호 출력 : " + result.getBno() + "게시물의 제목" + result.getTitle());
                } // forEach문 종료

        ); // IntStream. 종료

    }

    @Test
    public void testSelect(){
        Long bno = 100L; // 게시물 번호가 100인 개체를 확인 해보자.

        Optional<Board> result = boardRepository.findById(bno);
        // 널값이 나올 경우를 대비한 객체
        //                                      findById(bno); select * from board where bno = bno;

        Board board = result.orElseThrow(); // 값이 있으면 넣음

        log.info(bno + "가 데이터베이스에 존재합니다.");
        log.info(board);

    } // testSelect 메서드 종료

    @Test
    public void testUpdate() {

        Long bno = 100L; // 100번 게시물을 가져와서 수정 후 테스트 종료

        Optional<Board> result = boardRepository.findById(bno); // bno를 찾아서 result에 넣는다.

        Board board = result.orElseThrow(); // 가져온 값이 있으면 board 타입의 객체에 넣는다.

        board.change("수정 테스트 제목", "수정테스트 내용"); // 제목과 내용만 수정할 수 있는 메서드

        boardRepository.save(board); // .save 메서드는 pk값이 없으면 insert, 있으면  update 함.

        // Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Hibernate:
        //    update
        //        board
        //    set
        //        content=?,
        //        moddate=?,
        //        title=?,
        //        writer=?
        //    where
        //        bno=?

    }

    @Test
    public void testDelete() {
        Long bno = 1L;

        boardRepository.deleteById(bno);
        //             .deleteById(bno) -> delete from board where bno = bnos

    }

    @Test
    public void testPaging(){
        // .findAll() 는 모든 리스트를 출력하는 메서드 select * from board;
        // 전체 리스트에 페이징과 정렬 기법추가

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        //                              시작번호, 10개 목록
        //                                           번호를 기준으로 내림차순 정렬

        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("전체 페이지 수 : " + result.getTotalElements());
        log.info("총 페이지 수 : " + result.getTotalPages());
        log.info("현재 페이지 번호 : " + result.getNumber());
        log.info("페이지 당 데이터 개수 : " + result.getSize());
        log.info("다음 페이지 여부 : " + result.hasNext());
        log.info("시작 페이지 여부 : " + result.isFirst());

        List<Board> boardList = result.getContent();

        boardList.forEach(board -> log.info(board));
        // forEach는 인덱스를 사용하지 않고 앞에서부터 객체를 리턴함
        //              board -> log.info(board)
        //                  람다식 1개의 명령어가 있을 때 활용

    }


} // 클래스종료