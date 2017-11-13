package org.lighthouse.web.controllers;

import org.lighthouse.domain.entities.Official;
import org.lighthouse.domain.repositories.OfficialRepository;
import org.lighthouse.domain.service.SettingsService;
import org.lighthouse.export.services.ExportService;
import org.lighthouse.indexer.services.SolrIndexerService;
import org.lighthouse.web.vo.OfficialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

/**
 * @author nivanov
 * on 03.11.2017.
 */

@RestController
@RequestMapping("/api/")
public class DeclarationController {
    private final OfficialRepository officialRepository;
    private final Converter<Official, OfficialVO> converter;
    private final SolrIndexerService solrIndexerService;
    private final ExportService exportService;
    private final SettingsService settingsService;

    @Autowired
    public DeclarationController(OfficialRepository officialRepository,
                                 Converter<Official, OfficialVO> converter,
                                 SolrIndexerService solrIndexerService, ExportService exportService, SettingsService settingsService) {
        this.officialRepository = officialRepository;
        this.converter = converter;
        this.solrIndexerService = solrIndexerService;
        this.exportService = exportService;
        this.settingsService = settingsService;
    }

    @GetMapping("official")
    public ResponseEntity<OfficialVO> findOfficial(@RequestParam String name, @RequestParam String countryName) {
        Optional<Official> officialOptional = officialRepository.findOfficialByCountryNameAndName(countryName, name);
        if (!officialOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Official official = officialOptional.get();
        OfficialVO officialVO = converter.convert(official);
        return new ResponseEntity<>(officialVO, HttpStatus.OK);
    }

    @PostMapping("export")
    public void exportCountry(@RequestParam String countryName) {
        exportService.exportDataForCountry(countryName);
    }

    @GetMapping("file")
    public FileSystemResource files(@RequestParam String countryName, @RequestParam int year) {
        return new FileSystemResource(Paths.get(settingsService.getExportPath(), countryName, "declarations-" + countryName + "-" + year + ".csv").toFile());
    }

    @GetMapping("suggest")
    public Collection<String> suggest(@RequestParam String name, @RequestParam String countryName) {
        return solrIndexerService.findDeclaration(name, countryName);
    }
}
