package org.lighthouse.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "country_id"}))
@NoArgsConstructor
@RequiredArgsConstructor
public class Official {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    private String function;

    @NonNull
    @ManyToOne
    private Country country;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private List<Declaration> declaration;
}
