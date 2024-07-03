package sparta.code3line.domain.like.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LikeCommentRepositoryCustom {

    int countByUserId(Long userId);

}
