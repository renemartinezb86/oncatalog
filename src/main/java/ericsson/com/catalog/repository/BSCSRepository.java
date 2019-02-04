package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.BSCS;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BSCS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BSCSRepository extends MongoRepository<BSCS, String> {

}
