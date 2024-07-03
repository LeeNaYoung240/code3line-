package sparta.code3line.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.code3line.domain.board.repository.BoardRepositoryCustom;
import sparta.code3line.domain.comment.entity.Comment;
import sparta.code3line.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> , CommentRepositoryCustom {

    Optional<List<Comment>> findAllByBoardId(Long boardId);

    Optional<Comment> findByIdAndBoardId(Long commentId, Long boardId);
}
