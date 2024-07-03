package sparta.code3line.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.code3line.common.CommonResponse;
import sparta.code3line.domain.board.dto.BoardResponseDto;
import sparta.code3line.domain.follow.dto.FollowRequestDto;
import sparta.code3line.domain.follow.dto.FollowResponseDto;
import sparta.code3line.domain.follow.service.FollowService;
import sparta.code3line.domain.user.entity.User;
import sparta.code3line.security.UserPrincipal;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<CommonResponse<FollowResponseDto>> createFollow(
            @RequestBody FollowRequestDto followRequestDto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User currentUser = userPrincipal.getUser();
        followService.followUser(followRequestDto.getFollowingUserId(), currentUser);
        FollowResponseDto followResponseDto = new FollowResponseDto(followRequestDto.getFollowingUserId(), currentUser.getId(), LocalDateTime.now(), LocalDateTime.now());
        CommonResponse<FollowResponseDto> response = new CommonResponse<>(
                "팔로우 성공 🎉",
                HttpStatus.OK.value(),
                followResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<FollowResponseDto>> deleteFollow(
            @RequestBody FollowRequestDto followRequestDto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User currentUser = userPrincipal.getUser();
        followService.unfollowUser(followRequestDto.getFollowingUserId(), currentUser);
        FollowResponseDto followResponseDto = new FollowResponseDto(followRequestDto.getFollowingUserId(), currentUser.getId(), LocalDateTime.now(), LocalDateTime.now());
        CommonResponse<FollowResponseDto> response = new CommonResponse<>(
                "언팔로우 성공 🎉",
                HttpStatus.OK.value(),
                followResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // 팔로우하는 user의 게시글과 조회
    @GetMapping("/boards")
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getFollowersBoards(
            @RequestParam(defaultValue = "1") int page,
            @AuthenticationPrincipal UserPrincipal principal) {

        CommonResponse<List<BoardResponseDto>> response = new CommonResponse<>(
                "팔로우한 게시글 " + page + "번 페이지 조회 완료 🎉",
                HttpStatus.OK.value(),
                followService.getFollowersBoards(page, principal.getUser(), 5)
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
