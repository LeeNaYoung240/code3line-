package sparta.code3line.domain.board.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import sparta.code3line.config.TestConfig;
import sparta.code3line.domain.board.entity.Board;
import sparta.code3line.domain.like.entity.LikeBoard;
import sparta.code3line.domain.like.repository.LikeBoardRepository;
import sparta.code3line.domain.user.entity.User;
import sparta.code3line.domain.user.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class BoardRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeBoardRepository likeBoardRepository;

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
                LikeBoard likeBoard = LikeBoard.builder()
                        .user(user)
                        .board(board)
                        .build();
                likeBoardRepository.save(likeBoard);
            }
        }
        entityManager.flush();
        entityManager.clear();
    }

     @Test
    void testGetLikeBoardWithPageAndSortDesc() {
        List<Board> boards = boardRepository.getLikeBoardWithPageAndSortDesc(user, 0, 5);
        assertThat(boards).hasSize(5);
        assertThat(boards.get(0).getTitle()).isEqualTo("Title 4");
        assertThat(boards.get(1).getTitle()).isEqualTo("Title 3");
        assertThat(boards.get(2).getTitle()).isEqualTo("Title 2");
        assertThat(boards.get(3).getTitle()).isEqualTo("Title 1");
        assertThat(boards.get(4).getTitle()).isEqualTo("Title 0");
    }
}