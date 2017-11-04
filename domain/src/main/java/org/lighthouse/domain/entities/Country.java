package org.lighthouse.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NoArgsConstructor
@RequiredArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String siteUrl;

    @NonNull
    private LocalDateTime lastUpdate;
}
