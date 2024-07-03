package sparta.code3line.domain.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import sparta.code3line.common.CommonResponse;
import sparta.code3line.domain.board.dto.BoardResponseDto;
import sparta.code3line.domain.comment.dto.CommentResponseDto;
import sparta.code3line.domain.like.dto.LikeResponseDto;
import sparta.code3line.domain.like.service.LikeService;
import sparta.code3line.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/boards/{id}/likes")
    public ResponseEntity<CommonResponse<LikeResponseDto>> createLikeBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        LikeResponseDto responseDto = likeService.createLikeBoard(id, principal.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                "게시글 좋아요 성공 🎉",
                HttpStatus.OK.value(),
                responseDto));

    }

    @DeleteMapping("/boards/{id}/likes")
    public ResponseEntity<CommonResponse<LikeResponseDto>> deleteLikeBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        LikeResponseDto responseDto = likeService.deleteLikeBoard(id, principal.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                "게시글 좋아요 취소 성공 🎉",
                HttpStatus.OK.value(),
                responseDto));

    }

    @PostMapping("/comments/{id}/likes")
    public ResponseEntity<CommonResponse<LikeResponseDto>> createLikeComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        LikeResponseDto responseDto = likeService.createLikeComment(id, principal.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                "댓글 좋아요 성공 🎉",
                HttpStatus.OK.value(),
                responseDto));

    }

    @DeleteMapping("/comments/{id}/likes")
    public ResponseEntity<CommonResponse<LikeResponseDto>> deleteLikeComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        LikeResponseDto responseDto = likeService.deleteLikeComment(id, principal.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                "댓글 좋아요 취소 성공 🎉",
                HttpStatus.OK.value(),
                responseDto));

    }

    @GetMapping("/likes/boards")
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getLikeBoards(
            @RequestParam(defaultValue = "1") int page,
            @AuthenticationPrincipal UserPrincipal principal) {

        CommonResponse<List<BoardResponseDto>> response = new CommonResponse<>(
                "좋아요 한 게시글 " + page + "번 페이지 조회 완료 🎉",
                HttpStatus.OK.value(),
                likeService.getBoards(page, principal.getUser(), 5)
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/likes/comments")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getLikeComments(
            @RequestParam(defaultValue = "1") int page,
            @AuthenticationPrincipal UserPrincipal principal) {

        CommonResponse<List<CommentResponseDto>> response = new CommonResponse<>(
                "좋아요 한 댓글 " + page + "번 페이지 조회 완료 🎉",
                HttpStatus.OK.value(),
                likeService.getComments(page, principal.getUser(), 5)
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}