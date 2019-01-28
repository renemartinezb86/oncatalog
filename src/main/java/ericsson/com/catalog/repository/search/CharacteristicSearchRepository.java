package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.Characteristic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Characteristic entity.
 */
public interface CharacteristicSearchRepository extends ElasticsearchRepository<Characteristic, String> {
}
