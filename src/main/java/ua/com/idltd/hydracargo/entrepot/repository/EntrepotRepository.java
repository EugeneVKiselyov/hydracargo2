package ua.com.idltd.hydracargo.entrepot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.entrepot.entity.Entrepot;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntrepotRepository extends CrudRepository<Entrepot, Long> {

    @Query(nativeQuery = true, value = "select * from Entrepot where ep_id=?1")
    Optional<Entrepot> queryByEP_ID(Long ep_id);

}
