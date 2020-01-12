package ua.com.idltd.hydracargo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.user.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {

    @Query(nativeQuery = true, value = "select * from USERS where user_username=?")
    User findByUser_username(String user_username);

}
