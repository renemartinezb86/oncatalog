package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.BSCS;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BSCS entity.
 */
public interface BSCSSearchRepository extends ElasticsearchRepository<BSCS, String> {
}
