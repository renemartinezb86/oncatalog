package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.OptionalService;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the OptionalService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionalServiceRepository extends MongoRepository<OptionalService, String> {

}
