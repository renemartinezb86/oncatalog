package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.OptionalService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OptionalService entity.
 */
public interface OptionalServiceSearchRepository extends ElasticsearchRepository<OptionalService, String> {
}
