package team.univ.magic_conch.bundle;

import team.univ.magic_conch.bundle.dto.BundleDTO;

import java.util.List;
import java.util.Optional;

public interface BundleService {

    public Optional<Bundle> findById(Long id);
    public void save(Bundle bundle);
    BundleDTO.BundleDetails getBundleDetails(Long id);
}
