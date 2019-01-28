package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.BasicPO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BasicPO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BasicPORepository extends MongoRepository<BasicPO, String> {

}
