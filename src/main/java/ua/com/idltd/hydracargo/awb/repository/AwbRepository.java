package ua.com.idltd.hydracargo.awb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.awb.entity.Awb;

import java.util.Optional;

@Repository
public interface AwbRepository extends CrudRepository<Awb, Long> {


    @Query(nativeQuery = true, value = "select * from Awb where awb_num=?1")
    Optional<Awb> findByAWB_NUM(String awb_num);
}
