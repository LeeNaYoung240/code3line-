package sparta.code3line.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import sparta.code3line.domain.board.entity.Board;

import java.time.LocalDateTime;

@Data
public class BoardResponseDto {

    private String nickname;
    private Long boardId;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCnt;

    public BoardResponseDto(Board board) {

        this.nickname = board.getUser().getNickname();
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.likeCnt = board.getLikeCnt();

    }

    @Builder
    public BoardResponseDto(String nickname, Long boardId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt,
                            int likeCnt)
    {
        this.nickname = nickname;
        this.boardId = boardId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeCnt = likeCnt;
    }

}
