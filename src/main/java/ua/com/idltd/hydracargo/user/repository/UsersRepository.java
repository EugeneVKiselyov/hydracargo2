package ua.com.idltd.hydracargo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.user.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    @Query(nativeQuery = true, value = "select * from USERS where user_username=?")
    Users findByUser_username(String user_username);

}
