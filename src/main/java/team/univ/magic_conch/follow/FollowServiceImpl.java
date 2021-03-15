package team.univ.magic_conch.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.follow.dto.BestFollowerDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.dto.UserSimpleDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;

    @Override
    @Transactional(readOnly = false)
    public Follow addFollow(User userFrom, User userTo) {
        Follow follow = new Follow(userFrom, userTo);
        followRepository.save(follow);
        return follow;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFollow(User userFrom, User userTo) {
        Optional<Follow> allByUserFromAndUserTo = followRepository.findByUserFromAndUserTo(userFrom, userTo);
        allByUserFromAndUserTo.ifPresent(follow -> followRepository.deleteById(follow.getId()));
    }

    @Override
    public List<UserSimpleDTO> findAllByUserFrom(User userFrom) {
        return followRepository.findAllByUserFrom(userFrom).stream()
                .map(Follow::getUserTo)
                .map(User::entityToSimpleUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSimpleDTO> findAllByUserTo(User userTo) {
        return followRepository.findAllByUserTo(userTo).stream()
                .map(Follow::getUserFrom)
                .map(User::entityToSimpleUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isFollowed(User userFrom, User userTo) {
        return followRepository.findByUserFromAndUserTo(userFrom, userTo).isPresent();
    }

    @Override
    public List<BestFollowerDTO> findBestFollower() {
        return null;
    }
}
