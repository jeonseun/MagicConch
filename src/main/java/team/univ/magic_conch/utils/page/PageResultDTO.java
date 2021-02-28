package team.univ.magic_conch.utils.page;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;
    int totalCnt;
    int startPage;
    int endPage;
    int curPage;
    boolean previous;
    boolean next;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        /* 페이징 계산 */
        Pageable pageable = result.getPageable();
        /* 현재 페이지 */
        curPage = pageable.getPageNumber() + 1;
        /* 전체 게시글 수 */
        totalCnt = (int) result.getTotalElements();
        endPage = (int) (Math.ceil(curPage / 10.0) * 10);
        /* 쪽 번호 시작 페이지 */
        startPage = endPage - 9;
        /* 쪽 번호 끝 페이지 */
        endPage = Math.min(endPage, result.getTotalPages());
        endPage = (endPage == 0) ? 1 : endPage;
        /* 이전 버튼 활성화 여부 */
        previous = curPage > 10;
        /* 다음 버튼 활성화 여부 */
        next = result.getTotalPages() / 10 != pageable.getPageNumber() / 10;
    }

}
