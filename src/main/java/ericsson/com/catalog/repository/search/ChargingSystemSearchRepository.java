package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.ChargingSystem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ChargingSystem entity.
 */
public interface ChargingSystemSearchRepository extends ElasticsearchRepository<ChargingSystem, String> {
}
