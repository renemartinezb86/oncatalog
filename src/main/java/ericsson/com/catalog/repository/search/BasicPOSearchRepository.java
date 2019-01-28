package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.BasicPO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BasicPO entity.
 */
public interface BasicPOSearchRepository extends ElasticsearchRepository<BasicPO, String> {
}
