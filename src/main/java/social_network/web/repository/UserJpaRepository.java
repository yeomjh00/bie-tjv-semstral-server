package social_network.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_network.web.domain.Post;
import social_network.web.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    public Optional<User> findByUsername(String username);

    @Override
    public Optional<User> findByRealName(String realName);

//    @Override
//    @Modifying
//    @Query("UPDATE User u SET u.username = :#{#user.username}, u.realName = :#{#user.realName}, u.userStatus = :#{#user.userStatus}, u.introduction = :#{#user.introduction} WHERE u.id = :#{#user.id}")
//    public User updateUser(@Param("user") User user);

}
