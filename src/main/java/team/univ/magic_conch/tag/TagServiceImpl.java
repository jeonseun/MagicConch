package team.univ.magic_conch.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
