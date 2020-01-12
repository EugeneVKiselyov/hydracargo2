package ua.com.idltd.hydracargo.contragent.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.contragent.entity.Contragent;

import java.util.List;

@Repository
public interface ContragentRepository extends CrudRepository<Contragent, Long> {
    @Query(nativeQuery = true, value = "select * from CONTRAGENT c where c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username )")
    List<Contragent> getAllByUser(@Param("username") String usename);
}
