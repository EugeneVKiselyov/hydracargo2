package ua.com.idltd.hydracargo.contragent.rateproductgroup.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.contragent.rateproductgroup.entity.ContragentRateProductGroup;

import java.util.List;

@Repository
public interface ContragentRateProductGroupRepository extends CrudRepository<ContragentRateProductGroup, Long> {
    @Query(nativeQuery = true, value = "select fcr_id,cnt_id,frt_id,fpg_id,fcr_price,fcr_price_brand from FIN_CONTRAGENTRATEPRODUCTGROUP c where c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username ) and c.cnt_id=:cnt_id")
    List<ContragentRateProductGroup> getRateByContragent(@Param("username") String usename, @Param("cnt_id") Long cnt_id);
}
