package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.BasicPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the BasicPO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BasicPORepository extends MongoRepository<BasicPO, String> {
    @Query("{}")
    Page<BasicPO> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<BasicPO> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<BasicPO> findOneWithEagerRelationships(String id);

}
