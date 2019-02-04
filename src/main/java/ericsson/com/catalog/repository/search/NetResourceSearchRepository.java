package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.NetResource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NetResource entity.
 */
public interface NetResourceSearchRepository extends ElasticsearchRepository<NetResource, String> {
}
