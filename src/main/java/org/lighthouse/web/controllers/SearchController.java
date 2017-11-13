package org.lighthouse.web.controllers;

import org.lighthouse.domain.entities.Official;
import org.lighthouse.domain.repositories.CountryRepository;
import org.lighthouse.domain.repositories.OfficialRepository;
import org.lighthouse.domain.service.SettingsService;
import org.lighthouse.web.vo.OfficialVO;
import org.lighthouse.web.vo.SettingsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author nivanov
 * on 03.11.2017.
 */

@Controller
@RequestMapping("/search/")
public class SearchController {
    private final CountryRepository countryRepository;
    private final OfficialRepository officialRepository;
    private final Converter<Official, OfficialVO> converter;
    private final SettingsService settingsService;

    @Autowired
    public SearchController(CountryRepository countryRepository, OfficialRepository officialRepository, Converter<Official, OfficialVO> converter, SettingsService settingsService) {
        this.countryRepository = countryRepository;
        this.officialRepository = officialRepository;
        this.converter = converter;
        this.settingsService = settingsService;
    }

    @GetMapping
    public String searchPage(Model model) {
        SettingsVO vo = new SettingsVO();
        vo.setCountryInterval(settingsService.getCountryInterval());
        vo.setCrawlerInterval(settingsService.getCrawlingInterval());
        vo.setIndexInterval(settingsService.getIndexingInterval());
        vo.setExportPath(settingsService.getExportPath());
        vo.setExportType(settingsService.getExportType().name());
        vo.setProxyHost(settingsService.getProxyHost());
        vo.setProxyMode(settingsService.isProxyEnabled());
        vo.setProxyPort(settingsService.getProxyPort());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("settings", vo);
        return "search";
    }

    @GetMapping("official")
    public String findOfficial(@RequestParam String name, @RequestParam String countryName, Model model) {
        Optional<Official> officialOptional = officialRepository.findOfficialByCountryNameAndName(countryName, name);
        if (!officialOptional.isPresent()) {
            return "result::result404";
        }
        Official official = officialOptional.get();
        OfficialVO officialVO = converter.convert(official);
        model.addAttribute("official", officialVO);
        return "result::resultsList";
    }

}
