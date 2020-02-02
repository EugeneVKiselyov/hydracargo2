package ua.com.idltd.hydracargo.contragent.rateproductgroup.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.contragent.rateproductgroup.entity.VContragentRateProductGroup;

import java.util.List;

@Repository
public interface VContragentRateProductGroupRepository extends CrudRepository<VContragentRateProductGroup, Long> {
    @Query(nativeQuery = true, value = "select fcr_id,cnt_id,frt_id,fpg_id,fcr_price,fcr_price_brand,fpg_name from VFIN_CONTRAGENTRATEPRODUCTGROUP c where c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username ) and c.cnt_id=:cnt_id")
    List<VContragentRateProductGroup> getRateByContragent(@Param("username") String usename, @Param("cnt_id") Long cnt_id);
}
