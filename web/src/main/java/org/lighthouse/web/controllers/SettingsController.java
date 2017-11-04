package org.lighthouse.web.controllers;

import org.lighthouse.domain.entities.Settings;
import org.lighthouse.domain.service.SettingsService;
import org.lighthouse.web.vo.SettingsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author nivanov
 * on 04.11.2017.
 */

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    public SettingsVO getSettings() {
        SettingsVO vo = new SettingsVO();
        vo.setCountryInterval(settingsService.getCountryInterval());
        vo.setCrawlerInterval(settingsService.getCrawlingInterval());
        vo.setIndexInterval(settingsService.getIndexingInterval());
        vo.setExportPath(settingsService.getExportPath());
        vo.setExportType(settingsService.getExportType().name());
        vo.setProxyHost(settingsService.getProxyHost());
        vo.setProxyMode(settingsService.isProxyEnabled());
        vo.setProxyPort(settingsService.getProxyPort());
        return vo;
    }

    @PostMapping
    public void updateSettings(@Valid @RequestBody SettingsVO vo) {
        settingsService.setCountryInterval(vo.getCountryInterval());
        settingsService.setCrawlingInterval(vo.getCrawlerInterval());
        settingsService.setIndexingInterval(vo.getIndexInterval());
        settingsService.setExportPath(vo.getExportPath());
        settingsService.setExportType(Settings.ExportType.valueOf(vo.getExportType()));
        settingsService.setProxyMode(vo.getProxyMode());
        settingsService.setProxySettings(vo.getProxyHost(), vo.getProxyPort());
    }

}
