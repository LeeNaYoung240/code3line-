package sparta.code3line.domain.like.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LikeBoardRepositoryCustom {
    int countByUserId(Long userId);
}
