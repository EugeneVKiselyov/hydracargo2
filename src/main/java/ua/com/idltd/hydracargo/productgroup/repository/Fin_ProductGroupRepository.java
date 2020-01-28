package ua.com.idltd.hydracargo.productgroup.repository;

import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.productgroup.entity.Fin_ProductGroup;
import ua.com.idltd.hydracargo.repository.CustomRepository;

@Repository
public interface Fin_ProductGroupRepository extends CustomRepository<Fin_ProductGroup, Long> {

}
