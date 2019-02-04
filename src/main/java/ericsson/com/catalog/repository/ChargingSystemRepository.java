package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.ChargingSystem;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the ChargingSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargingSystemRepository extends MongoRepository<ChargingSystem, String> {

}
