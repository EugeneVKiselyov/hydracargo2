package ua.com.idltd.hydracargo.contragent.ratepackagematerial.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.contragent.ratepackagematerial.entity.VContragentRatePackageMaterial;

import java.util.List;

@Repository
public interface VContragentRatePackageMaterialRepository extends CrudRepository<VContragentRatePackageMaterial, Long> {
    @Query(nativeQuery = true, value = "select fcrm_id, cnt_id, frt_id, ftpm_id, ftpm_price, ftpm_price_brand, ftpm_name from VFIN_CONTRAGENTRATEPACKAGEMATERIAL c where c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username ) and c.cnt_id=:cnt_id")
    List<VContragentRatePackageMaterial> getRateByContragent(@Param("username") String usename, @Param("cnt_id") Long cnt_id);
}
