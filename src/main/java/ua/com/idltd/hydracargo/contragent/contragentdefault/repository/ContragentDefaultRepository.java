package ua.com.idltd.hydracargo.contragent.contragentdefault.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.contragent.contragentdefault.entity.ContragentDefault;

import java.util.List;

@Repository
public interface ContragentDefaultRepository extends CrudRepository<ContragentDefault, Long> {
    @Query(nativeQuery = true, value = "select * from CONTRAGENT_DEFAULT c where c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username ) and c.cnt_id=:cnt_id")
    List<ContragentDefault> getDefaultByContragent(@Param("username") String usename, @Param("cnt_id") Long cnt_id);

    @Query(nativeQuery = true, value =  "select * from CONTRAGENT_DEFAULT c,\n" +
                                        "              users u \n" +
                                        "where \n" +
                                        "u.USER_USERNAME=:username \n" +
                                        "and c.cnt_id=u.CNT_ID\n" +
                                        "and c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username )")
    ContragentDefault getDefaultByUsername(@Param("username") String usename);
}
