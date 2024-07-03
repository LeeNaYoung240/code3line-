package sparta.code3line.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sparta.code3line.domain.like.entity.QLikeBoard;

@RequiredArgsConstructor
public class LikeBoardRepositoryImpl implements LikeBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public int countByUserId(Long userId) {
        QLikeBoard likeBoard = QLikeBoard.likeBoard;
        return (int) queryFactory.selectFrom(likeBoard)
                .where(likeBoard.user.id.eq(userId))
                .fetchCount();
    }
}