package sparta.code3line.domain.comment.repository;

import org.springframework.stereotype.Repository;
import sparta.code3line.domain.comment.entity.Comment;
import sparta.code3line.domain.user.entity.User;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {

    List<Comment> getLikeCommentWithPageAndSortDesc(User user, long offset, int pageSize);
}
