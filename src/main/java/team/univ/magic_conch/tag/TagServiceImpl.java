package team.univ.magic_conch.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    /**
     * 태그 이름으로 검색
     * @param name
     * @return 태그 이름으로 검색한 Entity
     */
    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * 태그 전부 검색
     * @return 이름을 기준으로 오름차순 된 모든 태그 내용
     */
    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll(Sort.by("name").ascending());
    }

}
