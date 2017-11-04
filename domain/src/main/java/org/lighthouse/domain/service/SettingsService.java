package org.lighthouse.domain.service;

import org.lighthouse.domain.entities.Settings;

/**
 * @author nivanov
 * on 02.11.2017.
 */
public interface SettingsService {

    void setExportPath(String path);
    void setExportType(Settings.ExportType exportType);
    void setCrawlingInterval(Integer milliseconds);
    void setIndexingInterval(String cron);
    void setCountryInterval(Integer days);
    void setProxyMode(Boolean enabled);
    void setProxySettings(String host, Integer port);

    String getExportPath();
    Settings.ExportType getExportType();
    Integer getCrawlingInterval();
    String getIndexingInterval();
    Integer getCountryInterval();
    Boolean isProxyEnabled();
    String getProxyHost();
    Integer getProxyPort();
}
