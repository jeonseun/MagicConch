package team.univ.magic_conch.utils.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class PageRequestDTO {

    private int page = 1;
    private int size = 10;

    public PageRequestDTO(int page){
        this.page = page;
    }

    public PageRequestDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Pageable getPageable(){
        return PageRequest.of(page -1, size);
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}
