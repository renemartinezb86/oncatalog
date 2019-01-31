package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.OptionalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the OptionalService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionalServiceRepository extends MongoRepository<OptionalService, String> {
    @Query("{}")
    Page<OptionalService> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<OptionalService> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<OptionalService> findOneWithEagerRelationships(String id);

}
