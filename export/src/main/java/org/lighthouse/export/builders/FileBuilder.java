package org.lighthouse.export.builders;

import org.lighthouse.domain.entities.Declaration;

/**
 * @author nivanov
 * on 02.11.2017.
 */
public interface FileBuilder {
    FileBuilder addDeclaration(Declaration declaration);
    String build();
}
