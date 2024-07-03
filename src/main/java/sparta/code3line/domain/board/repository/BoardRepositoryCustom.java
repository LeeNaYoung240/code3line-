package sparta.code3line.domain.board.repository;

import org.springframework.stereotype.Repository;
import sparta.code3line.domain.board.entity.Board;
import sparta.code3line.domain.user.entity.User;

import java.util.List;

@Repository
public interface BoardRepositoryCustom {

    List<Board> getLikeBoardWithPageAndSortDesc(User user, long offset, int pageSize);

    List<Board> getFollowBoardWithPageAndSortDesc(List<User> followingUsers, long offset, int pageSize);

    List<Board> getFollowBoardWithPageAndSortByName(List<User> followingUsers, long offset, int pageSize);

}
