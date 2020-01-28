package ua.com.idltd.hydracargo.ratetype.repository;

import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.ratetype.entity.Fin_RateType;
import ua.com.idltd.hydracargo.repository.CustomRepository;

@Repository
public interface Fin_RateTypeRepository extends CustomRepository<Fin_RateType, Long> {

}
