package org.lighthouse.export.builders.impl;

import org.lighthouse.domain.entities.Declaration;
import org.lighthouse.export.builders.FileBuilder;
import org.lighthouse.export.utils.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nivanov
 * on 02.11.2017.
 */

public class CSVFileBuilder implements FileBuilder {
    private List<Declaration> declarations = new ArrayList<>();

    @Override
    public FileBuilder addDeclaration(Declaration declaration) {
        declarations.add(declaration);
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            CSVUtils.writeLine(stringBuilder, Arrays.asList("Имя", "Должность", "Ссылка на декларацию"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        declarations.forEach(declaration -> {
            try {
                CSVUtils.writeLine(stringBuilder, Arrays.asList(declaration.getOfficial().getName(), declaration.getOfficial().getFunction(), declaration.getUrl()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return stringBuilder.toString();
    }
}
