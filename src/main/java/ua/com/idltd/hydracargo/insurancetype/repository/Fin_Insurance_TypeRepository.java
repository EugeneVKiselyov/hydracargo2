package ua.com.idltd.hydracargo.insurancetype.repository;

import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.insurancetype.entity.Fin_Insurance_Type;
import ua.com.idltd.hydracargo.repository.CustomRepository;

@Repository
public interface Fin_Insurance_TypeRepository extends CustomRepository<Fin_Insurance_Type, Long> {

}
