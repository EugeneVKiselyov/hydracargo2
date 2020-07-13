package ua.com.idltd.hydracargo.po_user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.po_user.entity.Po_User;


@Repository
public interface Po_UserRepository extends CrudRepository<Po_User, Long> {

}
