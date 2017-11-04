package org.lighthouse.export.strategies.impl;

import org.lighthouse.domain.service.SettingsService;
import org.lighthouse.export.strategies.ExportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author nivanov
 * on 02.11.2017.
 */

@Component
public class LocalExportStrategy implements ExportStrategy {

    private final SettingsService settingsService;

    @Autowired
    public LocalExportStrategy(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public void exportFile(String content, Integer year, String country) {
        Path file;
        try {
            Path countryPath = Paths.get(settingsService.getExportPath(), country);
            if (!Files.exists(countryPath)) {
                Files.createDirectory(countryPath);
            }
            file = Files.createFile(Paths.get(settingsService.getExportPath(), country, "declarations-" + country + "-" + year + ".csv"));
            Files.write(file, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
