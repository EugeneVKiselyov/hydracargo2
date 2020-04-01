package ua.com.idltd.hydracargo.zammler.z1c.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.zammler.z1c.entity.Load_Zammler;


@Repository
public interface Load_ZammlerRepository extends CrudRepository<Load_Zammler, Long> {

}
