package org.mbc.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder // 세터를 사용하지 않고 빌더 패턴을 사용 아래 2개 어노테이션 필수
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class PageRequestDTO {
    // 페이징 처리에 요청 용
    // 페이징에 관련된 정보, 검생 종류, 키워드 처리용

    @Builder.Default // 빌더 패턴 시작시 초기값이 들어감.
    private int page = 1;

    @Builder.Default
    private int size = 10; // 게시물 수

    private String type; // t,c,w,tc,tw,twc.... (다중 검색용)

    private String keyword;

    private String link; // 프론트에 페이징번호 출력 시 처리되는 문자열
    // List?page=3&type=&keyword=kkw

    public String getLink(){

        if(link == null){
            StringBuilder builder = new StringBuilder();

            builder.append("page" + this.page); // page =1
            builder.append("&size" + this.size); // page=10&size=10

            if(type != null && type.length() >0){
                // 타입이 있을 때
                builder.append("&type=" + type);

            } // 타입이 있을 때 if문 종료

          if(keyword != null) {
              try{
                  builder.append("&keyword=" + URLEncoder.encode(keyword,"UTF-8"));
              }catch (UnsupportedEncodingException e){
                  log.info(e.getStackTrace());
                  log.info("UTF-8 처리 중 오류 발생");
              } // try문 종료
          } // keyword if문 종료
            link = builder.toString(); // 최종 결과들이 문자열로 변호나되어 Link에 저장

        } // if문 종료
        return link; // page=1&size=10&type=???&keyword=????
    } // 메서드 종료

    public String[] getTypes() {
        // 프론트에서 문자열이 여러개가 넘어오면 배열로 변환
        if(type==null || type.isEmpty()){
            // 넘어온 값이 널이거나 비어있으면
            return null;
        }
        return type.split(" ");
        // 문자열로 넘어온 값을 분할하여 배열에 꽂는다.
    }

    public Pageable getPageable(String...props){
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());

    }

}
