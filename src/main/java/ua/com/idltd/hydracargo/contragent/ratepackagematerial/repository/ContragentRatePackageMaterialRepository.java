package ua.com.idltd.hydracargo.contragent.ratepackagematerial.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.contragent.ratepackagematerial.entity.ContragentRatePackageMaterial;

import java.util.List;

@Repository
public interface ContragentRatePackageMaterialRepository extends CrudRepository<ContragentRatePackageMaterial, Long> {
    @Query(nativeQuery = true, value = "select fcrm_id, cnt_id, frt_id, ftpm_id, ftpm_price, ftpm_price_brand from FIN_CONTRAGENTRATEPACKAGEMATERIAL c where c.cnt_id = PKG_RIGHT.GETCNT_ID( c.cnt_id, :username ) and c.cnt_id=:cnt_id")
    List<ContragentRatePackageMaterial> getRateByContragent(@Param("username") String usename, @Param("cnt_id") Long cnt_id);
}
