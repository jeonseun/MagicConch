package team.univ.magic_conch.bundle;

import java.util.Optional;

public interface BundleSerivce {

    public Optional<Bundle> findById(Long id);
    public void save(Bundle bundle);
}
