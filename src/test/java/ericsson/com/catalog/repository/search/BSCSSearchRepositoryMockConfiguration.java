package ericsson.com.catalog.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BSCSSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BSCSSearchRepositoryMockConfiguration {

    @MockBean
    private BSCSSearchRepository mockBSCSSearchRepository;

}
