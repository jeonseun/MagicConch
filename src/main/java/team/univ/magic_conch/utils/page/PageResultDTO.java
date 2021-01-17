package team.univ.magic_conch.utils.page;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
        curPage = pageable.getPageNumber() + 1;
        totalCnt = (int) result.getTotalElements();
        endPage = (int) (Math.ceil(curPage / 10.0) * 10);
        startPage = endPage - 9;
        endPage = Math.min(endPage, result.getTotalPages());
        previous = curPage > 10;
        next = result.getTotalPages() / 10 != pageable.getPageNumber() / 10;
    }

}
