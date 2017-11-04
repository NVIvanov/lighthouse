package org.lighthouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author nivanov
 * on 22.10.2017.
 */

@SpringBootApplication
@EnableJpaRepositories("org.lighthouse.domain.repositories")
public class DomainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }
}
