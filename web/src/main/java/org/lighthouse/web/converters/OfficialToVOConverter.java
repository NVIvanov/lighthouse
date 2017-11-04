package org.lighthouse.web.converters;

import org.lighthouse.domain.entities.Declaration;
import org.lighthouse.domain.entities.Official;
import org.lighthouse.domain.repositories.DeclarationRepository;
import org.lighthouse.web.vo.DeclarationVO;
import org.lighthouse.web.vo.OfficialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nivanov
 * on 03.11.2017.
 */

@Component
public class OfficialToVOConverter implements Converter<Official, OfficialVO> {
    private final DeclarationRepository declarationRepository;
    private final Map<String, String> declarationTypes = new HashMap<String, String>(){{
        put(Declaration.Type.BOTH.name(), "Без классификации");
        put(Declaration.Type.PROPERTY.name(), "Декларация имущества и расходов");
        put(Declaration.Type.FIELD.name(), "Декларация недвижимого имущества");
    }};

    @Autowired
    public OfficialToVOConverter(DeclarationRepository declarationRepository) {
        this.declarationRepository = declarationRepository;
    }

    @Override
    public OfficialVO convert(Official source) {
        OfficialVO officialVO = new OfficialVO();
        officialVO.setCountry(source.getCountry().getName());
        officialVO.setFunction(source.getFunction());
        officialVO.setName(source.getName());
        Map<Integer, List<DeclarationVO>> declarationVOMap = declarationRepository.findDeclarationByOfficialNameAndOfficialCountryName(source.getName(), source.getCountry().getName())
                .stream()
                .map(d -> {
                    DeclarationVO declarationVO = new DeclarationVO();
                    declarationVO.setLink(d.getUrl());
                    declarationVO.setType(declarationTypes.get(d.getType().name()));
                    declarationVO.setYear(d.getYear());
                    return declarationVO;
                })
                .collect(Collectors.groupingBy(DeclarationVO::getYear));
        officialVO.setDeclarations(declarationVOMap);
        return officialVO;
    }
}
