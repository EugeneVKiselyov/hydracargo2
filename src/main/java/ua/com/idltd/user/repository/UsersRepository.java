package ua.com.idltd.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.user.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
//    Users findByUsername(String user_username);
}
