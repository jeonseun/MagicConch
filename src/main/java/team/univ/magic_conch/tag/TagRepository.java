package team.univ.magic_conch.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
