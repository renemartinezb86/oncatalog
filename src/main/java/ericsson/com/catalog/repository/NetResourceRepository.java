package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.NetResource;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the NetResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetResourceRepository extends MongoRepository<NetResource, String> {

}
