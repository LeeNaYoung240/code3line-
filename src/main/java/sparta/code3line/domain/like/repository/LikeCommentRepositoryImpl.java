package sparta.code3line.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sparta.code3line.domain.like.entity.QLikeComment;

@RequiredArgsConstructor
public class LikeCommentRepositoryImpl implements LikeCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public int countByUserId(Long userId) {
        QLikeComment likeComment = QLikeComment.likeComment;
        return (int) queryFactory.selectFrom(likeComment)
                .where(likeComment.user.id.eq(userId))
                .fetchCount();
    }
}