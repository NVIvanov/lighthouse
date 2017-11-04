package org.lighthouse.crawler.services.impl

import java.time.LocalDateTime
import java.util
import java.util.function.Supplier

import org.lighthouse.api.Declaration
import org.lighthouse.crawler.services.DeclarationService
import org.lighthouse.domain.entities
import org.lighthouse.domain.entities.Declaration.Type
import org.lighthouse.domain.entities.Official
import org.lighthouse.domain.repositories.{CountryRepository, DeclarationRepository, OfficialRepository}
import org.lighthouse.indexer.services.SolrIndexerService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * @author nivanov 
  *         on 23.10.2017.
  */

@Service
class DeclarationServiceImpl extends DeclarationService{
  val logger: Logger = DeclarationServiceImpl.logger

  @Autowired
  var jpaRepository: DeclarationRepository = _

  @Autowired
  var officialRepository: OfficialRepository = _

  @Autowired
  var countryRepository: CountryRepository = _

  @Autowired
  var solrIndexService: SolrIndexerService = _

  override def saveDeclaration(declaration: Declaration): Unit = {
    val declarationOpt = jpaRepository.
      findDeclarationByOfficialNameAndOfficialCountryNameAndYearAndType(declaration.name, declaration.country, declaration.year,
        Type.valueOf(declaration.`type`))
    if (declarationOpt.isPresent && declarationOpt.get().getYear.equals(declaration.year)) {
      declarationOpt.get.setUrl(declaration.link)
      jpaRepository.save(declarationOpt.get)
      solrIndexService.index(declarationOpt.get)
    } else {
      val countryOpt = countryRepository.findByName(declaration.country)
      val country = countryOpt.orElseThrow(new Supplier[Throwable] {
        override def get(): Throwable = new IllegalStateException()
      })
      country.setLastUpdate(LocalDateTime.now())
      countryRepository.save(country)

      val official = officialRepository.findOfficialByName(declaration.name).orElse(new Official())
      official.setFunction(declaration.function)
      official.setCountry(country)
      official.setName(declaration.name)
      officialRepository.save(official)
      val declarationModel = new org.lighthouse.domain.entities.Declaration(official, declaration.year, Type.valueOf(declaration.`type`))
      declarationModel.setUrl(declaration.link)
      jpaRepository.save(declarationModel)
      var declarations = official.getDeclaration
      if (declarations == null) {
        declarations = new util.ArrayList[entities.Declaration]()
      }
      declarations.add(declarationModel)
      official.setDeclaration(declarations)
      officialRepository.save(official)
      solrIndexService.index(declarationModel)
      logger.info(s"Проиндексирован ${official.getName} (${official.getFunction}). Ссылка на декларацию: ${declaration.link}")
    }
  }
}

object DeclarationServiceImpl {
  def logger: Logger = LoggerFactory.getLogger(classOf[DeclarationService])
}
