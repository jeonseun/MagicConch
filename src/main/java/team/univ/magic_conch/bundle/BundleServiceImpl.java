package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BundleServiceImpl implements BundleSerivce{

    private final BundleRepository bundleRepository;

    /**
     * 해당 ID로 검색
     * @param id
     * @return 해당 ID Bundle Entity
     */
    @Override
    public Bundle findById(Long id) {
        return bundleRepository.findById(id).get();
    }
}
