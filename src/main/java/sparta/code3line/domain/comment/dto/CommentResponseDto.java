package sparta.code3line.domain.comment.dto;

import lombok.Builder;
import lombok.Data;
import sparta.code3line.domain.comment.entity.Comment;

@Data
public class CommentResponseDto {

    private Long userId;
    private Long boardId;
    private String contents;
    private int likeCnt;

    public CommentResponseDto(Comment comment) {
        this.userId = comment.getUser().getId();
        this.boardId = comment.getBoard().getId();
        this.contents = comment.getContents();
        this.likeCnt = comment.getLikeCnt();
    }

    @Builder
    public CommentResponseDto(Long userId, Long boardId, String contents, int likeCnt) {
        this.userId = userId;
        this.boardId = boardId;
        this.contents = contents;
        this.likeCnt = likeCnt;
    }
}
