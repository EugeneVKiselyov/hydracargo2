package ua.com.idltd.hydracargo.service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.service.entity.Service;

import java.util.List;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
    @Query(nativeQuery = true, value = "select * from Service where serv_id=?1 order by serv_id")
    List<Service> queryByServ_id(String serv_id);
}
