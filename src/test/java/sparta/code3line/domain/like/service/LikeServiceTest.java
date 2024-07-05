package sparta.code3line.domain.like.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import sparta.code3line.config.TestConfig;
import sparta.code3line.domain.board.dto.BoardResponseDto;
import sparta.code3line.domain.board.entity.Board;
import sparta.code3line.domain.board.repository.BoardRepository;
import sparta.code3line.domain.comment.dto.CommentResponseDto;
import sparta.code3line.domain.comment.entity.Comment;
import sparta.code3line.domain.comment.repository.CommentRepository;
import sparta.code3line.domain.like.entity.LikeBoard;
import sparta.code3line.domain.like.entity.LikeComment;
import sparta.code3line.domain.like.repository.LikeBoardRepository;
import sparta.code3line.domain.like.repository.LikeCommentRepository;
import sparta.code3line.domain.user.entity.User;
import sparta.code3line.domain.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({TestConfig.class, LikeServiceTest.LikeServiceTestConfig.class})
class LikeServiceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeBoardRepository likeBoardRepository;

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeService likeService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("khu4237@gmail.com")
                .nickname("nickname")
                .username("username")
                .password("passWord123!")
                .role(User.Role.NORMAL)
                .status(User.Status.ACTIVE)
                .build();
        userRepository.save(user);

        for (int i = 0; i < 10; i++) {
            Board board = Board.builder()
                    .title("Title " + i)
                    .contents("Content " + i)
                    .user(user)
                    .type(Board.BoardType.NORMAL)
                    .build();
            boardRepository.save(board);

            if (i < 5) {
                likeBoardRepository.save(LikeBoard.builder().user(user).board(board).build());
            }

            Comment comment = Comment.builder()
                    .contents("Comment " + i)
                    .user(user)
                    .board(board)
                    .build();
            commentRepository.save(comment);

            if (i < 5) {
                likeCommentRepository.save(LikeComment.builder().user(user).comment(comment).build());
            }
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testGetBoards() {
        List<BoardResponseDto> boards = likeService.getBoards(1, user, 5);
        assertThat(boards).hasSize(5);
        assertThat(boards.get(0).getTitle()).isEqualTo("Title 4");
        assertThat(boards.get(1).getTitle()).isEqualTo("Title 3");
        assertThat(boards.get(2).getTitle()).isEqualTo("Title 2");
        assertThat(boards.get(3).getTitle()).isEqualTo("Title 1");
        assertThat(boards.get(4).getTitle()).isEqualTo("Title 0");
    }

    @Test
    void testGetComments() {
        List<CommentResponseDto> comments = likeService.getComments(1, user, 5);
        assertThat(comments).hasSize(5);
        assertThat(comments.get(0).getContents()).isEqualTo("Comment 4");
        assertThat(comments.get(1).getContents()).isEqualTo("Comment 3");
        assertThat(comments.get(2).getContents()).isEqualTo("Comment 2");
        assertThat(comments.get(3).getContents()).isEqualTo("Comment 1");
        assertThat(comments.get(4).getContents()).isEqualTo("Comment 0");
    }

    @TestConfiguration
    static class LikeServiceTestConfig {

        @Bean
        public LikeService likeService(BoardRepository boardRepository,
                                       CommentRepository commentRepository,
                                       LikeBoardRepository likeBoardRepository,
                                       LikeCommentRepository likeCommentRepository) {
            return new LikeService(boardRepository, commentRepository, likeBoardRepository, likeCommentRepository);
        }
    }
}
