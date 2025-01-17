package sparta.code3line.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.code3line.common.exception.CustomException;
import sparta.code3line.domain.board.dto.BoardResponseDto;
import sparta.code3line.domain.board.entity.Board;
import sparta.code3line.domain.board.repository.BoardRepository;
import sparta.code3line.domain.follow.entity.Follow;
import sparta.code3line.domain.follow.repository.FollowRepository;
import sparta.code3line.domain.user.entity.User;
import sparta.code3line.domain.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static sparta.code3line.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 팔로우 기능
    public void followUser(Long followingUserId, User follower) {

        if (followingUserId == null) {
            throw new CustomException(NOT_FOLLOWED_ID);
        }
        if (followingUserId.equals(follower.getId())) {
            throw new CustomException(NOT_FOLLOW);
        }

        User followingUser = findUser(followingUserId);

        if (isAlreadyFollowing(followingUserId, follower.getId())) {
            throw new CustomException(ALREADY_FOLLOW);
        }

        Follow follow = new Follow(followingUser, follower);
        followRepository.save(follow);

    }

    // 언팔로우 기능
    public void unfollowUser(Long followingUserId, User follower) {

        if (followingUserId == null) {
            throw new CustomException(NOT_FOLLOWED_ID);
        }

        Follow follow = followRepository.findByFollowingIdAndFollowerId(followingUserId, follower.getId())
                .orElseThrow(() -> new CustomException(NOT_FOLLOWED));

        followRepository.delete(follow);

    }

    // 사용자 조회
    private User findUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USERNAME_NOT_FOUND));

    }


    // 이미 팔로우 중인지 확인
    private boolean isAlreadyFollowing(Long followingUserId, Long followerId) {

        return followRepository.findByFollowingIdAndFollowerId(followingUserId, followerId).isPresent();

    }

    // 팔로우하는 게시글 조회
    public List<BoardResponseDto> getFollowersBoards(int page, User user, int size) {
        long offset = (page - 1) * size;

        List<User> followingUsers = followRepository.findAllByFollowerId(user.getId())
                .stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());

        List<Board> boards = boardRepository.getFollowBoardWithPageAndSortDesc(followingUsers, offset, size);

        return boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // 팔로우하는 게시글 조회 - 작성자명 기준 오름차순 정렬
    public List<BoardResponseDto> getFollowBoardWithPageAndSortByName(int page, User user, int size) {
        long offset = (page - 1) * size;

        List<User> followingUsers = followRepository.findAllByFollowerId(user.getId())
                .stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());

        List<Board> boards = boardRepository.getFollowBoardWithPageAndSortByName(followingUsers, offset, size);

        return boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }
}
