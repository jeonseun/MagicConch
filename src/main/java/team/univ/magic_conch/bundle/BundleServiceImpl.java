package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.bundle.dto.BundleDTO;

import java.util.List;
import java.util.Optional;
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
        return bundleRepository.findWithQuestionsAndTagById(id);
    }


    @Override
    public BundleDTO.BundleDetails getBundleDetails(Long id) {

        Optional<Bundle> findBundle = bundleRepository.findWithQuestionsAndTagById(id);
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
