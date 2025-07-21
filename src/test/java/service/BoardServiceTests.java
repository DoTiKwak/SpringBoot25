package service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mbc.board.dto.BoardDTO;
import org.mbc.board.dto.PageRequestDTO;
import org.mbc.board.dto.PageResponseDTO;
import org.mbc.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        log.info("등록용 테스트 서비스 실행중.....");
        log.info(boardService.getClass().getName()); // 객체 생성용 테스트

        BoardDTO boardDTO = BoardDTO.builder()
                .title("서비스에서 만든 제목")
                .content("서비스에서 만든 내용")
                .writer("서비스님")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("테스트 결과 bno : " + bno);

    }

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("서비스에서 수정된 제목")
                .content("서비스에서 수정된 내용")
                .build();
        boardService.modify(boardDTO); // 프론트에서 객체가 넘어가 수정이 되었는지 테스트

    }

    @Test
    public void testList(){
        // 프론트에서 넘어오는 데이터를 이용해서 페이징과 검색과 정렬 처리용
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);

    }

}
