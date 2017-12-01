package org.lighthouse.domain.entities;

import lombok.*;

import javax.persistence.*;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "official")
public class Declaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @NonNull
    @ManyToOne
    private Official official;

    @NonNull
    private Integer year;

    @NonNull
    private Type type;

    @NonNull
    @Column(columnDefinition = "text")
    private String body;

    public enum Type {
        PROPERTY, FIELD, BOTH
    }


}
