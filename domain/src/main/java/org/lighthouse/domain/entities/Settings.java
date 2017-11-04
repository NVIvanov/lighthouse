package org.lighthouse.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author nivanov
 * on 02.11.2017.
 */

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Type type;
    private String value;

    public enum Type {
        EXPORT_TYPE, EXPORT_PATH, INDEX_INTERVAL, CRAWLING_INTERVAL, COUNTRY_INTERVAL, PROXY_ENABLED,
        PROXY_HOST, PROXY_PORT
    }

    public enum ExportType {
        LOCAL, FTP
    }
}
