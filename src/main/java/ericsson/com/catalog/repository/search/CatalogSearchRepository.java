package ericsson.com.catalog.repository.search;

import ericsson.com.catalog.domain.Catalog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Catalog entity.
 */
public interface CatalogSearchRepository extends ElasticsearchRepository<Catalog, String> {
}
