package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BundleServiceImpl implements BundleSerivce{

    private final BundleRepository bundleRepository;

    /**
     * 번들 생성
     * @param bundle
     */
    @Override
    public void save(Bundle bundle) {
        bundleRepository.save(bundle);
    }

    /**
     * 해당 ID로 검색
     * @param id
     * @return 해당 ID Bundle Entity
     */
    @Override
    public Optional<Bundle> findById(Long id) {
        return bundleRepository.findById(id);
    }

}
