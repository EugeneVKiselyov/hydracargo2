package ua.com.idltd.hydracargo.business.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.business.entity.Business;

import java.util.List;

@Repository
public interface BusinessRepository extends CrudRepository<Business, Long> {

    @Query(nativeQuery = true, value = "select * from Business where bs_id=?1")
    List<Business> queryByBS_ID(Long bs_id);

}
