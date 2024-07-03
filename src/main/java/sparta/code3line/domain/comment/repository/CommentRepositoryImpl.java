package sparta.code3line.domain.comment.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.code3line.domain.comment.entity.Comment;
import sparta.code3line.domain.user.entity.User;

import java.util.List;

import static sparta.code3line.domain.comment.entity.QComment.comment;
import static sparta.code3line.domain.like.entity.QLikeComment.likeComment;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> getLikeCommentWithPageAndSortDesc(User user, long offset, int pageSize)
    {
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, comment.createdAt);

        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.likeComments, likeComment)
                .where(likeComment.user.id.eq(user.getId()))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();
    }
}
