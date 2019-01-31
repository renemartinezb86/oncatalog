package ericsson.com.catalog.repository;

import ericsson.com.catalog.domain.Characteristic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Characteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacteristicRepository extends MongoRepository<Characteristic, String> {
    @Query("{}")
    Page<Characteristic> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Characteristic> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Characteristic> findOneWithEagerRelationships(String id);

}
