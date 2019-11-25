package ua.com.idltd.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import ua.com.idltd.user.entity.Roles;
import ua.com.idltd.user.entity.Users;

import java.util.HashSet;
import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {

}
