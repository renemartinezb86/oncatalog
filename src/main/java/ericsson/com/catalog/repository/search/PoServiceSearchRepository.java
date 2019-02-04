package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.PoService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PoService entity.
 */
public interface PoServiceSearchRepository extends ElasticsearchRepository<PoService, String> {
}
