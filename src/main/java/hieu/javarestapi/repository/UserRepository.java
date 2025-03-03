package hieu.javarestapi.repository;

import hieu.javarestapi.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select u from UserEntity u where u.status = 'ACTIVE' AND (u.firstName like :keyword or u.lastName like :keyword or u.phone like :keyword) order by u.id limit :limit offset :offset")
    List<UserEntity> searchByKeyword(String keyword, int limit, int offset);

    @Query(value = "select count(u) from UserEntity u where u.status = 'ACTIVE' AND (u.firstName like :keyword or u.lastName like :keyword or u.phone like :keyword)")
    int countByKeyword(String keyword);

    Optional<UserEntity> findByUsername(String username);
}
