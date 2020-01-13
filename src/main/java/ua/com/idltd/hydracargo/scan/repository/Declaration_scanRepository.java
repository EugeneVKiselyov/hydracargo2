package ua.com.idltd.hydracargo.scan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.scan.entity.Declaration_scan;

@Repository
public interface Declaration_scanRepository extends CrudRepository<Declaration_scan, Long> {

}
