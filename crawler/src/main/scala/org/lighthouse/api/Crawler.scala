package org.lighthouse.api

import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import org.lighthouse.crawler.services.DeclarationService
import org.lighthouse.domain.repositories.CountryRepository
import org.lighthouse.domain.service.SettingsService
import org.lighthouse.export.services.ExportService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * @author nivanov 
  *         on 23.10.2017.
  */
trait Crawler {
  val list: mutable.SortedSet[String] = mutable.SortedSet[String](initPage)
  val visitedLinks: ListBuffer[String] = new ListBuffer[String]
  val logger: Logger = Crawler.logger
  val isStarted: AtomicBoolean = new AtomicBoolean(false)

  @Autowired
  var declarationService: DeclarationService = _

  @Autowired
  var countryRepository: CountryRepository = _

  @Autowired
  var settingsService: SettingsService = _

  @Autowired
  var exportService: ExportService = _

  def start(): Unit = {
    isStarted.set(true)
    try {
      while (list.nonEmpty) {
        val root = list.head
        val page = getPage(root)
        visit(page)
        Thread.sleep(settingsService.getCrawlingInterval.longValue())
        if (isTerminal(root)) {
          val declaration = parseDeclaration(page, root)
          declarationService.saveDeclaration(declaration, page)
        }
        visitedLinks += root
        val links = page >> elementList("div") >?> attr("href")("a")
        val newLinks = links
          .filter(l => l.isDefined)
          .map(l => page.location.replace("index.html", "") + l.get)
          .filter(link => !visitedLinks.contains(link))
          .filter(isShouldVisit)
        list ++= newLinks
        list.remove(root)
        logger.info(list.size + " ссылок осталось обработать")
      }
      list.clear()
      list += initPage
    } finally {
      isStarted.set(false)
    }
    exportService.exportDataForCountry(countryName())
  }

  def updated(): LocalDateTime = {
    Option(countryRepository.findByName(countryName()).orElse(null)).map(c => c.getLastUpdate).getOrElse(LocalDateTime.MIN)
  }

  def isShouldVisit(url: String): Boolean
  def visit(page: Document): Unit
  def isTerminal(url: String): Boolean
  def initPage: String
  def getPage(str: String): Document
  def parseDeclaration(page: Document, url: String): Declaration
  def countryName(): String
}

object Crawler {
  def logger: Logger = LoggerFactory.getLogger(classOf[Crawler])
}
