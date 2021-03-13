package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.bundle.AccessLevel;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;

    @Override
    public Optional<Bundle> findById(Long id) {
        return bundleRepository.findWithQuestionsAndTagById(id);
    }

    @Override
    public Bundle createBundle(String name, Tag tag, User user, AccessLevel accessLevel) {
        Bundle bundle = Bundle.builder()
                .name(name)
                .tag(tag)
                .user(user)
                .accessLevel(accessLevel)
                .build();
        bundleRepository.save(bundle);
        return bundle;
    }

    @Override
    public Page<Bundle> getBundleByUsername(String username, Pageable pageable) {
        return bundleRepository.findAllByUserUsername(username, pageable);
    }

    @Override
    public List<Bundle> searchBundle(String bundleName) {
        return bundleRepository.findAllByNameContaining(bundleName);
    }


}
