package org.lighthouse.domain.service.impl;

import org.lighthouse.domain.entities.Settings;
import org.lighthouse.domain.repositories.SettingsRepository;
import org.lighthouse.domain.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author nivanov
 * on 02.11.2017.
 */

@Service
public class SettingsServiceImpl implements SettingsService {
    private static final String DEFAULT_EXPORT_PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static").toString();
    private static final String DEFAULT_INDEXING_CRON = "0 * * * * *";
    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsServiceImpl(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void setExportPath(String path) {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.EXPORT_PATH);
        Settings settings = settingsOptional.orElse(new Settings(Settings.Type.EXPORT_PATH));
        settings.setValue(path);
        settingsRepository.save(settings);
    }

    @Override
    public void setExportType(Settings.ExportType exportType) {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.EXPORT_TYPE);
        Settings settings = settingsOptional.orElse(new Settings(Settings.Type.EXPORT_TYPE));
        settings.setValue(exportType.name());
        settingsRepository.save(settings);
    }

    @Override
    public void setCrawlingInterval(Integer milliseconds) {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.CRAWLING_INTERVAL);
        Settings settings = settingsOptional.orElse(new Settings(Settings.Type.CRAWLING_INTERVAL));
        settings.setValue(milliseconds.toString());
        settingsRepository.save(settings);
    }

    @Override
    public void setIndexingInterval(String cron) {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.INDEX_INTERVAL);
        Settings settings = settingsOptional.orElse(new Settings(Settings.Type.INDEX_INTERVAL));
        settings.setValue(cron);
        settingsRepository.save(settings);
    }

    @Override
    public void setCountryInterval(Integer days) {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.COUNTRY_INTERVAL);
        Settings settings = settingsOptional.orElse(new Settings(Settings.Type.COUNTRY_INTERVAL));
        settings.setValue(days.toString());
        settingsRepository.save(settings);
    }

    @Override
    public void setProxyMode(Boolean enabled) {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.PROXY_ENABLED);
        Settings settings = settingsOptional.orElse(new Settings(Settings.Type.PROXY_ENABLED));
        settings.setValue(enabled.toString());
        settingsRepository.save(settings);
    }

    @Override
    public void setProxySettings(String host, Integer port) {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.PROXY_HOST);
        Settings settingsHost = settingsOptional.orElse(new Settings(Settings.Type.PROXY_HOST));
        settingsOptional = settingsRepository.findByType(Settings.Type.PROXY_PORT);
        Settings settingsPort = settingsOptional.orElse(new Settings(Settings.Type.PROXY_PORT));
        settingsHost.setValue(host);
        settingsPort.setValue(port.toString());
        settingsRepository.save(settingsHost);
        settingsRepository.save(settingsPort);
    }

    @Override
    public String getExportPath() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.EXPORT_PATH);
        return settingsOptional.map(Settings::getValue).orElse(DEFAULT_EXPORT_PATH);
    }

    @Override
    public Settings.ExportType getExportType() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.EXPORT_TYPE);
        return settingsOptional.map(s -> Settings.ExportType.valueOf(s.getValue())).orElse(Settings.ExportType.LOCAL);
    }

    @Override
    public Integer getCrawlingInterval() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.CRAWLING_INTERVAL);
        return settingsOptional.map(s -> Integer.valueOf(s.getValue())).orElse(0);
    }

    @Override
    public String getIndexingInterval() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.INDEX_INTERVAL);
        return settingsOptional.map(Settings::getValue).orElse(DEFAULT_INDEXING_CRON);
    }

    @Override
    public Integer getCountryInterval() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.COUNTRY_INTERVAL);
        return settingsOptional.map(s -> Integer.valueOf(s.getValue())).orElse(1);
    }

    @Override
    public Boolean isProxyEnabled() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.PROXY_ENABLED);
        return settingsOptional.map(s -> Boolean.valueOf(s.getValue())).orElse(false);
    }

    @Override
    public String getProxyHost() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.PROXY_HOST);
        return settingsOptional.map(Settings::getValue).orElse("localhost");
    }

    @Override
    public Integer getProxyPort() {
        Optional<Settings> settingsOptional = settingsRepository.findByType(Settings.Type.PROXY_PORT);
        return settingsOptional.map(s -> Integer.valueOf(s.getValue())).orElse(3128);
    }
}
