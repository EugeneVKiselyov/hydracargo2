package ua.com.idltd.hydracargo.country.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.idltd.hydracargo.country.entity.Country;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    @Query(nativeQuery = true, value = "select * from Country where country_iso2=?1")
    List<Country> queryByIso2(String country_iso2);

}
