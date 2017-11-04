package org.lighthouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.common.settings.Settings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "org.lighthouse.indexer.domain.repositories")
public class IndexerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndexerApplication.class, args);
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(ObjectMapper objectMapper) {
        return new ElasticsearchTemplate(nodeBuilder().settings(Settings.settingsBuilder().put("path.home", "target/elastic-embedded").build()).local(true).node().client(), new EntityMapper() {
            @Override
            public String mapToString(Object object) throws IOException {
                return objectMapper.writeValueAsString(object);
            }

            @Override
            public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
                return objectMapper.readValue(source, clazz);
            }
        });
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.findAndRegisterModules();
    }
}
