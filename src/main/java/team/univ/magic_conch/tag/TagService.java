package team.univ.magic_conch.tag;

import java.util.List;

public interface TagService {

    public Tag findByName(String name);
    public List<Tag> findAll();

}
