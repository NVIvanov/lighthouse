package org.lighthouse.indexer.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Document(indexName = "lighthouse", type = "declarations")
@Data
@NoArgsConstructor
public class DeclarationDocument {

    @Id
    private String id;

    @CompletionField
    private String official;
    private String function;
    private String country;
    private LocalDateTime lastUpdate;
    private String link;
    private String type;
    private Integer year;

}
