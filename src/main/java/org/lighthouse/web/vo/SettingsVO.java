package org.lighthouse.web.vo;

/**
 * @author nivanov
 * on 03.11.2017.
 */
public class SettingsVO {
    private String exportPath;
    private String exportType;
    private String indexInterval;
    private Integer crawlerInterval;
    private Integer countryInterval;
    private Boolean proxyMode;
    private String proxyHost;
    private Integer proxyPort;

    public String getExportPath() {
        return exportPath;
    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getIndexInterval() {
        return indexInterval;
    }

    public void setIndexInterval(String indexInterval) {
        this.indexInterval = indexInterval;
    }

    public Integer getCrawlerInterval() {
        return crawlerInterval;
    }

    public void setCrawlerInterval(Integer crawlerInterval) {
        this.crawlerInterval = crawlerInterval;
    }

    public Integer getCountryInterval() {
        return countryInterval;
    }

    public void setCountryInterval(Integer countryInterval) {
        this.countryInterval = countryInterval;
    }

    public Boolean getProxyMode() {
        return proxyMode;
    }

    public void setProxyMode(Boolean proxyMode) {
        this.proxyMode = proxyMode;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }
}
