package org.lighthouse.crawler.impl

import java.net.URL

import net.ruippeixotog.scalascraper.browser.{HtmlUnitBrowser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Document
import org.lighthouse.api.{Crawler, Declaration}
import org.lighthouse.domain.entities.Declaration.Type._
import org.slf4j.{Logger, LoggerFactory}

/**
  * @author nivanov 
  *         on 23.10.2017.
  */

class BulgaryCrawler(browser: HtmlUnitBrowser, jsoupBrowser: JsoupBrowser) extends Crawler{

  override def isShouldVisit(url: String): Boolean = {
    (url.endsWith(".html") || url.endsWith(".xml")) && !url.contains("abbrev") //FIXME переписать через регулярки
  }

  override def visit(page: Document): Unit = {
    if (page.location != null && !page.location.isEmpty) {
        logger.info("Заход на страницу: " + page.location)
    }
  }

  override def isTerminal(url: String): Boolean = {
    url.endsWith(".xml")
  }

  override def initPage: String = "http://register.bulnao.government.bg"

  override def parseDeclaration(page: Document, url: String): Declaration = {
    new Declaration {
      override def country: String = "Болгария"

      override def link: String = url

      override def name: String = (page >> text("Name")).toString

      override def function: String = (page >> text("Position")).toString

      override def `type`: String = if ((page >> text("Entrynumber")).toString.startsWith("Г")) PROPERTY.name() else FIELD.name()

      override def year: Integer = Integer.valueOf((page >> extractor("Year", text, asInt)).toString)
    }
  }

  override def getPage(string: String): Document = {
    if (string.endsWith("xml")) {
      jsoupBrowser.parseInputStream(new URL(string).openStream())
    } else {
      browser.get(string)
    }
  }

  override def countryName(): String = "Болгария"
}

object BulgaryCrawler {
  def logger: Logger = LoggerFactory.getLogger(classOf[BulgaryCrawler])
}