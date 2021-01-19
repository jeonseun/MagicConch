package team.univ.magic_conch.bundle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BundleRepository extends JpaRepository<Bundle, Long> {

    List<Bundle> findAllByUserUsername(String username);
}
