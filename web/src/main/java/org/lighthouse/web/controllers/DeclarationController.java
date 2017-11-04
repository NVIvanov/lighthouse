package org.lighthouse.web.controllers;

import org.lighthouse.domain.entities.Official;
import org.lighthouse.domain.repositories.OfficialRepository;
import org.lighthouse.indexer.services.SolrIndexerService;
import org.lighthouse.web.vo.OfficialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    public DeclarationController(OfficialRepository officialRepository,
                                 Converter<Official, OfficialVO> converter,
                                 SolrIndexerService solrIndexerService) {
        this.officialRepository = officialRepository;
        this.converter = converter;
        this.solrIndexerService = solrIndexerService;
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

    @GetMapping("suggest")
    public Collection<String> suggest(@RequestParam String name, @RequestParam String countryName) {
        return solrIndexerService.findDeclaration(name, countryName);
    }
}
