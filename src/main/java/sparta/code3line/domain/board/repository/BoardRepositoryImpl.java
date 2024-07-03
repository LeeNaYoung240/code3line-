package sparta.code3line.domain.board.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.code3line.domain.board.entity.Board;
import sparta.code3line.domain.user.entity.User;

import java.util.List;

import static sparta.code3line.domain.board.entity.QBoard.board;
import static sparta.code3line.domain.follow.entity.QFollow.follow;
import static sparta.code3line.domain.like.entity.QLikeBoard.likeBoard;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> getLikeBoardWithPageAndSortDesc(User user, long offset, int pageSize)
    {
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, board.createdAt);

        return jpaQueryFactory.selectFrom(board)
                .leftJoin(board.likeBoards, likeBoard)
                .where(likeBoard.user.id.eq(user.getId()))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public List<Board> getFollowBoardWithPageAndSortDesc(List<User> followingUsers, long offset, int pageSize) {
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, board.createdAt);

        return jpaQueryFactory.selectFrom(board)
                .leftJoin(follow).on(board.user.eq(follow.following))
                .where(follow.following.in(followingUsers))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public List<Board> getFollowBoardWithPageAndSortByName(List<User> followingUsers, long offset, int pageSize) {
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.ASC, board.user.username);

        return jpaQueryFactory.selectFrom(board)
                .leftJoin(follow).on(board.user.eq(follow.following))
                .where(follow.following.in(followingUsers))
                .offset(offset)
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();
    }

}
