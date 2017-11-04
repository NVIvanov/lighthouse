package org.lighthouse.export.services.impl;

import org.lighthouse.domain.entities.Declaration;
import org.lighthouse.domain.repositories.DeclarationRepository;
import org.lighthouse.domain.service.SettingsService;
import org.lighthouse.export.builders.FileBuilder;
import org.lighthouse.export.builders.impl.CSVFileBuilder;
import org.lighthouse.export.factory.ExportStrategyFactory;
import org.lighthouse.export.services.ExportService;
import org.lighthouse.export.strategies.ExportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nivanov
 * on 02.11.2017.
 */

@Service
public class DefaultExportService implements ExportService {
    private final DeclarationRepository declarationRepository;
    private final ExportStrategyFactory exportStrategyFactory;
    private final SettingsService settingsService;

    @Autowired
    public DefaultExportService(DeclarationRepository declarationRepository, ExportStrategyFactory exportStrategyFactory, SettingsService settingsService) {
        this.declarationRepository = declarationRepository;
        this.exportStrategyFactory = exportStrategyFactory;
        this.settingsService = settingsService;
    }

    @Override
    public void exportDataForCountry(String countryName) {
        Collection<Declaration> declarations = declarationRepository.findDeclarationByOfficialCountryName(countryName);
        Map<Integer, List<Declaration>> declarationPartitionByYear = declarations.stream()
                .collect(Collectors.groupingBy(Declaration::getYear));
        declarationPartitionByYear.forEach((year, declarationList) -> {
            FileBuilder fileBuilder = new CSVFileBuilder();
            for(Declaration declaration: declarationList) {
                fileBuilder = fileBuilder.addDeclaration(declaration);
            }
            ExportStrategy strategy = exportStrategyFactory.strategyByType(settingsService.getExportType());
            strategy.exportFile(fileBuilder.build(), year, countryName);
        });
    }
}
