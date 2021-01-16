package team.univ.magic_conch.utils.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestDTO {

    private int page;
    private int size;

    public PageRequestDTO(int page, int size) {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(){
        return PageRequest.of(page -1, size);
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}
