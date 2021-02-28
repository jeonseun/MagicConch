package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.visibility.Visibility;

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
    public void create(String name, Tag tag, User user, Visibility visibility) {
        Bundle bundle = Bundle.builder()
                .name(name)
                .tag(tag)
                .user(user)
                .visibility(visibility)
                .build();
        bundleRepository.save(bundle);
    }

    @Override
    public Page<Bundle> getBundleByUsername(String username, Pageable pageable) {
        return bundleRepository.findAllByUserUsername(username, pageable);
    }


}
