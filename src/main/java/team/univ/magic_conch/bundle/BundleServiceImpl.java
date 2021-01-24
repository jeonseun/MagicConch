package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.bundle.dto.BundleDTO;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;

    /**
     * 번들 생성
     *
     * @param bundle
     */
    @Override
    public void save(Bundle bundle) {
        bundleRepository.save(bundle);
    }

    /**
     * 해당 ID로 검색
     *
     * @param id
     * @return 해당 ID Bundle Entity
     */
    @Override
    public Optional<Bundle> findById(Long id) {
        return bundleRepository.findById(id);
    }


    /**
     * 회원 ID로 해당 회원이 생성한 번들목록 조회하여 반환
     *
     * @param username
     * @return 뷰로 넘겨주기 위한 번들정보 리스트
     */
    @Override
    public List<BundleDTO.MyBundle> getMyBundles(String username) {
        List<Bundle> bundles = bundleRepository.findAllByUserUsername(username);

        return bundles.stream()
                .map(Bundle::toMyBundleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BundleDTO.BundleDetails getBundleDetails(Long id) {
        Optional<Bundle> findBundle = bundleRepository.findById(id);
        if(findBundle.isPresent()) {
            Bundle bundle = findBundle.get();
            return BundleDTO.BundleDetails.builder()
                    .bundleName(bundle.getName())
                    .tagColor(bundle.getTag().getColor())
                    .tagName(bundle.getTag().getName())
                    .createDate(bundle.getCreateDate())
                    .questions(bundle.getQuestions())
                    .build();
        }
        return null;
    }

}
