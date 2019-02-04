package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.PoService;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the PoService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoServiceRepository extends MongoRepository<PoService, String> {

}
